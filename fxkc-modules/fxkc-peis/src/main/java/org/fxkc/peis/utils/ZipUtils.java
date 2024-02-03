package org.fxkc.peis.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.io.input.NullInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author pw
 */
@Slf4j
public class ZipUtils {

    /**
     * 该线程池会被自动关闭
     * @return ZipCreator
     */
    private static ParallelScatterZipCreator getZipCreator(){
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("compressFileList-pool-").build();
        ExecutorService executor = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), factory);
        return new ParallelScatterZipCreator(executor);
    }

    /**
     *  多线程压缩
     **/
    public static void zipThreads(List<File> list,OutputStream outputStream) throws Exception{
        ParallelScatterZipCreator zipCreator = getZipCreator();
        ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputStream);
        zipArchiveOutputStream.setEncoding("UTF-8");
        for(File f:list){
            compressFile(f, "", zipCreator);
        }
        zipCreator.writeTo(zipArchiveOutputStream);
        zipArchiveOutputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     *  多线程压缩
     **/
    public static void zipThreads(List<File> list, HttpServletResponse response) throws Exception{
        zipThreads(list,response.getOutputStream());
    }

    /**
     *  多线程压缩
     **/
    public static void zipThreads(String srcFilePath, HttpServletResponse response) throws Exception{
        ParallelScatterZipCreator zipCreator = getZipCreator();
        OutputStream outputStream = response.getOutputStream();
        ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputStream);
        zipArchiveOutputStream.setEncoding("UTF-8");
        File src = new File(srcFilePath);
        compressbyType(src, "", zipCreator);
        zipCreator.writeTo(zipArchiveOutputStream);
        zipArchiveOutputStream.close();
    }

    /**
     * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
     * @param src 源文件
     * @param zipCreator 工具
     */
    private static void compressbyType(File src,String baseDir, ParallelScatterZipCreator zipCreator) {
        if (!src.exists()) {
            return;
        }
        if (src.isFile()) {
            //src是文件，调用此方法
            compressFile(src, baseDir, zipCreator);

        } else if (src.isDirectory()) {
            //src是文件夹，调用此方法
            compressDir(src,baseDir, zipCreator);

        }

    }

    /**
     * 压缩文件
     */
    private static void compressFile(File f,String baseDir, ParallelScatterZipCreator zipCreator) {
        final InputStreamSupplier inputStreamSupplier = () -> {
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException e) {
                log.error("压缩文件失败:",e);
                return new NullInputStream(0);
            }
        };
        ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry( baseDir+f.getName());
        zipArchiveEntry.setMethod(ZipArchiveEntry.DEFLATED);
        zipArchiveEntry.setSize(f.length());
        zipArchiveEntry.setUnixMode(UnixStat.FILE_FLAG | 436);
        zipCreator.addArchiveEntry(zipArchiveEntry, inputStreamSupplier);
    }


    /**
     * 压缩文件夹
     */
    private static void compressDir(File dir,String baseDir, ParallelScatterZipCreator zipCreator) {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if(null==files || files.length == 0){
            compressbyType(dir,  baseDir + dir.getName() +"/",zipCreator);
            return;
        }
        for (File file : files) {
            compressbyType(file,  baseDir + dir.getName() + "/",zipCreator);
        }
    }
}
