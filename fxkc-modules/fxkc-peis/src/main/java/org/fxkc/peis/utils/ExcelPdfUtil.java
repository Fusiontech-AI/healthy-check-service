package org.fxkc.peis.utils;

import com.alibaba.nacos.client.utils.ParamUtil;
import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.excel.utils.ExcelUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;


/**
 * @author Administrator
 */
@Slf4j
public class ExcelPdfUtil {

    private static boolean license;

    static {
        try {
            InputStream is = ParamUtil.class.getClassLoader().getResourceAsStream("license-18.8.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            license = true;
        } catch (Exception e) {
            license = false;
            log.error("许可校验失败:",e);
        }
    }


    /**
     * 将excel转为pdf
     * @param in 需要转换的word
     * @param os 保存pdf文件
     */
    public static void excel2pdf(InputStream in, OutputStream os) {
        if (!license) {
            return;
        }
        try {
            Workbook wb = new Workbook(in); // Address是将要被转化的excel文档
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            pdfSaveOptions.setAllColumnsInOnePagePerSheet(true);
            int sheetCount = wb.getWorksheets().getCount();

            for (int i = 0; i < sheetCount; i++) {
                Worksheet worksheet = wb.getWorksheets().get(i);
                //单元格自动高度
                worksheet.autoFitRows();
                //自动拉伸比例
                worksheet.getHorizontalPageBreaks().clear();
                worksheet.getVerticalPageBreaks().clear();
            }
            wb.save(os, pdfSaveOptions);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("excel转为pdf失败:",e);
        }
    }

    public static void createCommon(boolean isPrint, HttpServletResponse response, String fileName, List<?> list, Set<String> set, Class<?> c) throws Exception {
        if(!isPrint) {
            ExcelUtil.download(response,fileName,c,list,set);
            return;
        }
        //先下载到本地
        File file = new File(WordToPdfUtils.getTemplatePath(), fileName +".xlsx");
        try {
            ExcelUtil.download(response,fileName,c,list,set);
            //转成PDF
            WordToPdfUtils.setResponse(response, fileName+".pdf");
            ExcelPdfUtil.excel2pdf(Files.newInputStream(file.toPath()),response.getOutputStream());
        }finally {
            WordToPdfUtils.deleteFolder(file.getParentFile());
        }
    }
}
