package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.TjRegProjectSummaryBo;
import org.fxkc.peis.domain.bo.TjRegProjectListBo;
import org.fxkc.peis.domain.vo.TjRegProjectSummaryVo;
import org.fxkc.peis.service.ITjRegProjectSummaryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检登记基础项目小结
 * 前端访问路由地址为:/peis/regProjectSummary
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/regProjectSummary")
public class TjRegProjectSummaryController extends BaseController {

    private final ITjRegProjectSummaryService tjRegProjectSummaryService;

    /**
     * 查询体检登记基础项目小结列表
     */
    @PostMapping("/summaryList")
    public R<List<TjRegProjectSummaryVo>> summaryList(@RequestBody TjRegProjectListBo bo) {
        return R.ok(tjRegProjectSummaryService.summaryList(bo));
    }


    /**
     * 新增体检登记基础项目小结
     */
    @Log(title = "体检登记基础项目小结新增", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjRegProjectSummaryBo bo) {
        return toAjax(tjRegProjectSummaryService.insertByBo(bo));
    }

    /**
     * 修改体检登记基础项目小结
     */
    @SaCheckPermission("peis:regProjectSummary:edit")
    @Log(title = "体检登记基础项目小结", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjRegProjectSummaryBo bo) {
        return toAjax(tjRegProjectSummaryService.updateByBo(bo));
    }

    /**
     * 删除体检登记基础项目小结
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:regProjectSummary:remove")
    @Log(title = "体检登记基础项目小结", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjRegProjectSummaryService.deleteWithValidByIds(List.of(ids), true));
    }
}
