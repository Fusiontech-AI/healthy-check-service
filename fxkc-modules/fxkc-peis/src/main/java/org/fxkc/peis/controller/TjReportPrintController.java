package org.fxkc.peis.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.constant.ReportTypeEnum;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.reportPrint.ReportPrintHandler;
import org.fxkc.peis.reportPrint.ReportPrintService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 报告打印
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/reportPrint")
public class TjReportPrintController extends BaseController {

    private final ReportPrintHandler reportPrintHandler;

    /**
     * 打印指引单
     */
    @PostMapping("/printGuideSheet")
    public void printGuideSheet(@RequestBody ReportPrintBO bo,HttpServletResponse response) throws Exception {
        ReportPrintService printService = reportPrintHandler.getHandler(ReportTypeEnum.ZYD.getCode());
        printService.print(bo,response);
    }

    /**
     * 打印个人报告
     */
    @PostMapping("/printPersonalReport")
    public void printPersonalReport(@RequestBody ReportPrintBO bo,HttpServletResponse response) throws Exception {
        ReportPrintService printService = reportPrintHandler.getHandler(ReportTypeEnum.PERSONAL.getCode());
        printService.print(bo,response);
    }

    /**
     * 打印条形码
     */
    @PostMapping("/printTxm")
    public void printTxm(@RequestBody ReportPrintBO bo,HttpServletResponse response) throws Exception {
        ReportPrintService printService = reportPrintHandler.getHandler(ReportTypeEnum.TXM.getCode());
        printService.print(bo,response);
    }
}
