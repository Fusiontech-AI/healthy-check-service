package org.fxkc.peis.service;

import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;

public interface ITjReportPrintService {
   void printGuideSheet(ReportPrintBO bo, HttpServletResponse response) throws Exception;
}
