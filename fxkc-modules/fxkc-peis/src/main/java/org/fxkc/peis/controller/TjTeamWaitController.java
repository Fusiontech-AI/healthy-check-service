package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.TjTeamWaitSwitchGroupBo;
import org.fxkc.peis.domain.vo.*;
import org.fxkc.peis.service.ITjTeamWaitService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检单位待检人员
 * 前端访问路由地址为:/peis/teamWait
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamWait")
public class TjTeamWaitController extends BaseController {

    private final ITjTeamWaitService tjTeamWaitService;

    /**
     * 查询体检单位待检人员详情任务分组树
     */
    @SaCheckPermission("peis:teamWait:list")
    @GetMapping("/teamSettleWaitTaskGroupTree/{teamId}")
    public R<List<TjTeamWaitTaskGroupVo>> teamSettleWaitTaskGroupTree(@NotNull(message = "单位ID不能为空")
                                                                          @PathVariable Long teamId) {
        return R.ok(tjTeamWaitService.teamSettleWaitTaskGroupTree(teamId));
    }

    /**
     * 体检单位待检人员批量换组
     */
    @SaCheckPermission("peis:teamWait:update")
    @Log(title = "体检单位待检人员批量换组", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/batchSwitchGroup")
    public R<Void> batchSwitchGroup(@Validated @RequestBody TjTeamWaitSwitchGroupBo bo) {
        return toAjax(tjTeamWaitService.batchSwitchGroup(bo));
    }

}
