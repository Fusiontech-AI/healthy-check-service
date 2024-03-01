package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.TjZdjywh;
import org.fxkc.peis.domain.bo.TjZdjywhBo;
import org.fxkc.peis.domain.bo.TjZdjywhQueryBo;
import org.fxkc.peis.domain.vo.TjZdjywhVo;
import org.fxkc.peis.service.ITjZdjywhService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诊断建议主
 * 前端访问路由地址为:/peis/zdjywh
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/zdjywh")
public class TjZdjywhController extends BaseController {

    private final ITjZdjywhService tjZdjywhService;

    /**
     * 查询诊断建议主列表
     */
    @GetMapping("/list")
    public TableDataInfo<TjZdjywh> list(TjZdjywhQueryBo bo, PageQuery pageQuery) {
        return tjZdjywhService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取诊断建议主详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:zdjywh:query")
    @GetMapping("/{id}")
    public R<TjZdjywhVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjZdjywhService.queryById(id));
    }

    /**
     * 新增诊断建议主表
     */
    @SaCheckPermission("peis:zdjywh:add")
    @Log(title = "诊断建议主", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjZdjywhBo bo) {
        return toAjax(tjZdjywhService.insertByBo(bo));
    }

    /**
     * 修改诊断建议主表
     */
    @SaCheckPermission("peis:zdjywh:edit")
    @Log(title = "诊断建议主", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjZdjywhBo bo) {
        return toAjax(tjZdjywhService.updateByBo(bo));
    }

    /**
     * 删除诊断建议主表
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:zdjywh:remove")
    @Log(title = "诊断建议主表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjZdjywhService.deleteWithValidByIds(List.of(ids), true));
    }
}
