package org.fxkc.peis.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;
import org.fxkc.peis.service.ITjReportPrintService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * 报告打印
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/reportPrint")
public class TjReportPrintController extends BaseController {

    private final ITjReportPrintService reportPrintService;

    /**
     * 打印指引单
     */
    @PostMapping("/printGuideSheet")
    public void printGuideSheet(@RequestBody ReportPrintBO bo,HttpServletResponse response) throws Exception {
        reportPrintService.printGuideSheet(bo,response);
    }

}
