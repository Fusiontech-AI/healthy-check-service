package org.fxkc.peis.utils;

import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.SaveFormat;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Word转PDF工具类
 * @author pw
 *
 */
@Slf4j
@Component
public class WordToPdfUtils {
    /**
     *  word模板存放地址
     */
    private static String templatePath ="ftl";

    @Value("${template.path:ftl}")
    public void setTemplatePath(String templatePath) {
        String os = System.getProperty("os.name");
        //Linux操作系统
        if (os != null && os.toLowerCase().startsWith("linux")) {
            WordToPdfUtils.templatePath = templatePath;
        }
    }

    public static String getTemplatePath(){
        String s = templatePath + "/short2/" + System.currentTimeMillis();
        //判断文件夹是否存在，不存在，则新建一个文件夹
        File file = new File(s);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return s;
    }

    private static final Configuration CONFIGURATION;
    static {
        CONFIGURATION = new Configuration(Configuration.VERSION_2_3_30);
        CONFIGURATION.setDefaultEncoding("utf-8");
        CONFIGURATION.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_30));
    }
    /**
     * 用freemaker模板生成word文件
     * @param dataMap 要填充的数据
     * @param templateName 模板名称
     * @param fileName 要输出的文件名称
     * @param isPdf 是否打印pdf
     */
    public static void createCommon(Object dataMap, String templateName, String fileName, HttpServletResponse response, boolean isPdf) throws IOException {
        //生成word/或者pdf
        if(isPdf){
            createPdf(dataMap,templateName,fileName,response);
        }else {
            createWord(dataMap,templateName,fileName,response);
        }
    }
    /**
     * 用freemaker模板生成word文件
     * @param dataMap 要填充的数据
     * @param templateName 模板名称
     * @param fileName 要输出的文件名称
     * @param isPdf 是否打印pdf
     */
    public static void createCommon(Object dataMap,String templateName, String fileName, FileOutputStream fileOutputStream,boolean isPdf) {
        //生成word/或者pdf
        if(isPdf){
            createPdf(dataMap,templateName,fileName,fileOutputStream);
        }else {
            createWord(dataMap,templateName,fileOutputStream);
        }
    }

    /**
     * 用freemaker模板生成word文档
     * @param dataMap 要填充的数据
     * @param templateName 模板名称
     * @param fileName 要输出的文件名称
     */
    public static void createWord(Object dataMap,String templateName, String fileName,HttpServletResponse response) throws IOException {
        setResponse(response,fileName+".doc");
        getWord(dataMap,templateName,response.getOutputStream());
    }

    /**
     * 用freemaker模板生成word文档
     * @param dataMap 要填充的数据
     * @param templateName 模板名称
     */
    public static void createWord(Object dataMap,String templateName, FileOutputStream fileOutputStream) {
      getWord(dataMap,templateName,fileOutputStream);
    }

    /**
     * 用freemaker模板生成pdf文档
     * @param dataMap 要填充的数据
     * @param templateName 模板名称
     * @param fileName 要输出的文件名称
     */
    public static void createPdf(Object dataMap,String templateName, String fileName,HttpServletResponse response) throws IOException {
        setResponse(response,fileName+".pdf");
        getPdf(dataMap,templateName,fileName,response.getOutputStream());
    }

    /**
     * 用freemaker模板生成pdf文档
     * @param dataMap 要填充的数据
     * @param templateName 模板名称
     * @param fileName 要输出的文件名称
     */
    public static void createPdf(Object dataMap,String templateName, String fileName,OutputStream outputStream) {
        getPdf(dataMap,templateName,fileName,outputStream);
    }

    private static void getWord(Object dataMap,String templateName,OutputStream os){
        try (Writer out = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8))){
            CONFIGURATION.setDirectoryForTemplateLoading(new File(templatePath));
            Template template  = CONFIGURATION.getTemplate(templateName,"UTF-8");
            //创建一个Word文档输出流
            template.process(dataMap, out);
        } catch (Exception e) {
            log.error("生成WORD失败:",e);
        }
    }

    private static void getPdf(Object dataMap,String templateName, String fileName,OutputStream os){
        ///临时路径
        String wordPath =templatePath+"/short/"+System.currentTimeMillis();
        File f = new File(wordPath+"/"+fileName+".doc");
        //判断文件夹是否存在，不存在，则新建一个文件夹
        if (!f.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            f.getParentFile().mkdirs();
        }
        try (Writer out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(f.toPath()), StandardCharsets.UTF_8))){
            CONFIGURATION.setDirectoryForTemplateLoading(new File(templatePath));
            Template template  = CONFIGURATION.getTemplate(templateName,"UTF-8");
            //创建一个Word文档输出流
            template.process(dataMap, out);
            //word文档转PDF
            FileInputStream fio = new FileInputStream(f);
            wordOrPdf(fio, os);
        } catch (Exception e) {
            log.error("WORD转换PDF失败:",e);
        }finally {
            deleteFolder(f.getParentFile());
        }
    }

    public static void deleteFolder(File folder) {
        if (!folder.exists()) {
            return;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归直到目录下没有文件
                    deleteFolder(file);
                } else {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
            }
        }
        //noinspection ResultOfMethodCallIgnored
        folder.delete();
    }

    /**
     * 合并多个word文件
     */
    public void twoDocToOneDoc(List<String> files, String newFile) throws Exception {
        Document doc3 = new Document();
        for(String s:files){
            doc3.appendDocument( new Document(s), ImportFormatMode.USE_DESTINATION_STYLES );
        }
        doc3.save(newFile);
    }

    /**
     * 使用aspose.word把word文档转为pdf文档
     */
    public static void wordOrPdf(InputStream in, OutputStream os) throws Exception {
        try {
            Document doc = new Document(in);
            doc.save(os, SaveFormat.PDF);
        } finally {
            os.flush();
            os.close();
            in.close();
        }
    }

    /**
     * pdf预览、下载
     */
    public static void setResponse(HttpServletResponse response,String fileName) {
        //content-disposition属性名 （attachment表示以附件的方式下载;inline表示在页面内打开）
        response.setHeader("Content-Disposition", "attachment; filename*=utf-8'zh_cn'"+ URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }


    /**
     * 获取网络图片
     */
    public static String getImageFromNetByUrl(String strUrl) {
        InputStream inStream =null;
        try {
            URL url = new URI(strUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();
            return readInputStream(inStream);
        } catch (Exception e) {
            log.error("获取网络图片失败:",e);
        }finally {
            if(null!=inStream){
                try {
                    inStream.close();
                }catch (Exception e1){
                    log.error("关闭流失败:",e1);
                }
            }
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     * @param inputStream
     *            输入流
     */
    public static String readInputStream(InputStream inputStream){
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()){
            byte[] buffer = new byte[10240];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.flush();
            outStream.close();
            return Base64.encodeBase64String(outStream.toByteArray());
        }catch (Exception e){
            log.error("输入流转base64失败:",e);
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     * @param bufferedImage
     *            输入流
     */
    public static String getBufferedImageToBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png", stream);
        return Base64.encodeBase64String(stream.toByteArray());
    }

    /**
     * 图片转码工具类
     * @param f 图片地址
     */
    public static String getImgFileToBase64(File f){
        try(InputStream inputStream = Files.newInputStream(f.toPath())) {
           return readInputStream(inputStream);
        } catch (Exception e) {
            log.error("file转base64失败:",e);
        }
        return null;
    }

    /**
     * base64转文件
     * @param outputFilePath 图片地址
     */
    public static File convertBase64ToPDF(String base64String, String outputFilePath) throws IOException {
        String wordPath =getTemplatePath();
        // 解码Base64字符串
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64String);
        File file = new File(wordPath+"/"+outputFilePath);
        // 创建文件输出流
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // 将解码后的字节写入文件
            fos.write(decodedBytes);
        }
        return file;
    }

    /**
     * 流转base64
     */
    public static String getByteToBase64(byte[] buffer) {
        return Base64.encodeBase64String(buffer);
    }
}
