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
import org.fxkc.peis.domain.bo.TjRegProjectPositiveBo;
import org.fxkc.peis.domain.bo.TjRegProjectListBo;
import org.fxkc.peis.domain.vo.TjRegProjectPositiveVo;
import org.fxkc.peis.service.ITjRegProjectPositiveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检登记基础项目阳性记录
 * 前端访问路由地址为:/peis/regProjectPositive
 *
 * @author JunBaiChen
 * @date 2024-02-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/regProjectPositive")
public class TjRegProjectPositiveController extends BaseController {

    private final ITjRegProjectPositiveService tjRegProjectPositiveService;

    /**
     * 查询体检登记基础项目阳性记录列表
     */
    @SaCheckPermission("peis:regProjectPositive:list")
    @PostMapping("/positiveList")
    public R<List<TjRegProjectPositiveVo>> positiveList(@RequestBody TjRegProjectListBo bo) {
        return R.ok(tjRegProjectPositiveService.positiveList(bo));
    }


    /**
     * 新增体检登记基础项目阳性记录
     */
    @SaCheckPermission("peis:regProjectPositive:add")
    @Log(title = "体检登记基础项目阳性记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjRegProjectPositiveBo bo) {
        return toAjax(tjRegProjectPositiveService.insertByBo(bo));
    }

    /**
     * 修改体检登记基础项目阳性记录
     */
    @SaCheckPermission("peis:regProjectPositive:edit")
    @Log(title = "体检登记基础项目阳性记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjRegProjectPositiveBo bo) {
        return toAjax(tjRegProjectPositiveService.updateByBo(bo));
    }

    /**
     * 删除体检登记基础项目阳性记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:regProjectPositive:remove")
    @Log(title = "体检登记基础项目阳性记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjRegProjectPositiveService.deleteWithValidByIds(List.of(ids), true));
    }
}
