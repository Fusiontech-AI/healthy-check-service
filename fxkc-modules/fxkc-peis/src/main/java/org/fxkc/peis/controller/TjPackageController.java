package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.TjPackageAddBo;
import org.fxkc.peis.domain.bo.TjPackageBillBo;
import org.fxkc.peis.domain.bo.TjPackageBo;
import org.fxkc.peis.domain.vo.TjPackageVo;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检套餐
 * 前端访问路由地址为:/peis/package
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/package")
public class TjPackageController extends BaseController {

    private final ITjPackageService tjPackageService;

    /**
     * 查询体检套餐列表
     */
    @SaCheckPermission("peis:package:list")
    @GetMapping("/list")
    public TableDataInfo<TjPackageVo> list(TjPackageBo bo, PageQuery pageQuery) {
        return tjPackageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检套餐列表
     */
    @SaCheckPermission("peis:package:export")
    @Log(title = "体检套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjPackageBo bo, HttpServletResponse response) {
        List<TjPackageVo> list = tjPackageService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检套餐", TjPackageVo.class, response);
    }

    /**
     * 获取体检套餐详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:package:query")
    @GetMapping("/{id}")
    public R<TjPackageVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjPackageService.queryById(id));
    }

    /**
     * 新增体检套餐
     */
    @SaCheckPermission("peis:package:add")
    @Log(title = "体检套餐", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjPackageAddBo bo) {
        return toAjax(tjPackageService.insertByBo(bo));
    }

    /**
     * 修改体检套餐
     */
    @SaCheckPermission("peis:package:edit")
    @Log(title = "体检套餐", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjPackageAddBo bo) {
        return toAjax(tjPackageService.updateByBo(bo));
    }

    /**
     * 删除体检套餐
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:package:remove")
    @Log(title = "体检套餐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjPackageService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 体检套餐动态算费(可复用)
     */
    @PostMapping("dynamicBilling")
    public R<TjPackageBillBo> dynamicBilling(@Valid @RequestBody TjPackageBillBo bo) {
        return R.ok(tjPackageService.dynamicBilling(bo));
    }
}
