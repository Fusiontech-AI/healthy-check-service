package org.fxkc.peis.reportPrint;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pw
 */
@Component
public class ReportPrintHandler {

    private final Map<String, ReportPrintService> printHolder = new HashMap<>(4);

    public ReportPrintService getHandler(String key) {
        return printHolder.get(key);
    }

    public void putHandler(String key, ReportPrintService builder) {
        printHolder.put(key, builder);
    }
}
