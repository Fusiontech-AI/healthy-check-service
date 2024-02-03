package org.fxkc.peis.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

/**
 * pw PDF合并
 */
public class PdfMergeUtil {
    /**
     * 合并pdf
     * @param files 需要合并的pdf路径
     * @param os 合并成新的文件的路径
     */
    public static void mergePdfFiles(List<File> files,OutputStream os) throws Exception{
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);;
        PdfCopy copy = null;
        try {
            copy = new PdfCopy(document,os);
            document.open();
            for (File file : files) {
                compressFile(file,document,copy);
            }
        } finally {
            if (copy != null) {
                copy.close();
            }
            document.close();
        }
    }

    /**
     * 合并pdf
     * @param folderPath 文件夹路径
     * @param os 合并成新的文件的路径
     */
    public static void mergePdfFiles(String folderPath,OutputStream os) throws Exception {
        File src = new File(folderPath);
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
        PdfCopy copy = null;
        try {
            copy = new PdfCopy(document,os);
            document.open();
            compressByType(src, document,copy);
        } finally {
            if (copy != null) {
                copy.close();
            }
            document.close();
        }
    }

    /**
     * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
     * @param src 源文件
     */
    private static void compressByType(File src,Document document,PdfCopy copy) throws Exception {
        if (!src.exists()) {
            return;
        }
        if (src.isFile()) {
            //src是文件，调用此方法
            compressFile(src, document,copy);
        } else if (src.isDirectory()) {
            //src是文件夹，调用此方法
            compressDir(src,document,copy);
        }
    }
    /**
     * 压缩文件
     */
    private static void compressFile(File file,Document document,PdfCopy copy) throws Exception{
        PdfReader reader = new PdfReader(Files.newInputStream(file.toPath()));
        int n = reader.getNumberOfPages();
        for (int j = 1; j <= n; j++) {
            document.newPage();
            PdfImportedPage page = copy.getImportedPage(reader, j);
            copy.addPage(page);
        }
        reader.close();
    }

    /**
     * 压缩文件夹
     */
    private static void compressDir(File dir,Document document,PdfCopy copy) throws Exception {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if(null==files || files.length == 0){
            compressByType(dir,document,copy);
            return;
        }
        for (File file : files) {
            compressByType(file,document,copy);
        }
    }
}
