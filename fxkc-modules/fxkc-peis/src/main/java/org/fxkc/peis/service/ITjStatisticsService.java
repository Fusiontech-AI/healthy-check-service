package org.fxkc.peis.service;

import jakarta.servlet.http.HttpServletResponse;
import org.fxkc.peis.domain.bo.TjStatisticsCommonBo;
import org.fxkc.peis.domain.vo.TjDailyPhysicalExportVo;

public interface ITjStatisticsService {

    TjDailyPhysicalExportVo dailyPhysicalOverview(TjStatisticsCommonBo bo);

    void dailyPhysicalOverviewExport(TjStatisticsCommonBo bo, HttpServletResponse response);
}
