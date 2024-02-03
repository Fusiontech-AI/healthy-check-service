package org.fxkc.peis.reportPrint;

import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.peis.domain.bo.template.ReportPrintBO;

public interface ReportPrintService {
   void print(ReportPrintBO bo, HttpServletResponse response) throws Exception;
}
