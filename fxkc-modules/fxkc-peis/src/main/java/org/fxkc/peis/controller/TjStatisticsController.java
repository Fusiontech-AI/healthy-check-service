package org.fxkc.peis.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.TjGroupVerifyBo;
import org.fxkc.peis.domain.bo.TjStatisticsCommonBo;
import org.fxkc.peis.domain.vo.TjDailyPhysicalExportVo;
import org.fxkc.peis.domain.vo.VerifyMessageVo;
import org.fxkc.peis.service.ITjStatisticsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 报表统计
 * 前端访问路由地址为:/peis/statistics
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class TjStatisticsController extends BaseController {

    private final ITjStatisticsService iTjStatisticsService;

    /**
     * 每日体检者概览
     */
    @PostMapping("/dailyPhysicalOverview")
    public R<TjDailyPhysicalExportVo> dailyPhysicalOverview(@RequestBody TjStatisticsCommonBo bo) {
        return R.ok(iTjStatisticsService.dailyPhysicalOverview(bo));
    }

    /**
     * 每日体检者概览导出
     */
    @PostMapping("/dailyPhysicalOverviewExport")
    public void dailyPhysicalOverviewExport(@RequestBody TjStatisticsCommonBo bo, HttpServletResponse response) {
        iTjStatisticsService.dailyPhysicalOverviewExport(bo, response);
    }
}
