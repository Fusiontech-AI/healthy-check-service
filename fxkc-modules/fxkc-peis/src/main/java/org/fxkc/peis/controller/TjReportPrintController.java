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


}
