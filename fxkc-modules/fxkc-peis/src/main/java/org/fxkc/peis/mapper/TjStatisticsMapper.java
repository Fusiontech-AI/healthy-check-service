package org.fxkc.peis.mapper;

import org.fxkc.peis.domain.bo.TjStatisticsCommonBo;
import org.fxkc.peis.domain.vo.TjDailyPhysicalExportVo;

import java.util.List;

/**
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
public interface TjStatisticsMapper {

    List<TjDailyPhysicalExportVo> dailyPhysicalOverview(TjStatisticsCommonBo bo);
}
