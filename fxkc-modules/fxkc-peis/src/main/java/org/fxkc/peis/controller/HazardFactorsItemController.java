package org.fxkc.peis.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.peis.domain.vo.HazardFactorsItemVo;
import org.fxkc.peis.domain.bo.HazardFactorsItemBo;
import org.fxkc.peis.service.IHazardFactorsItemService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 危害因素必检项目关联
 * 前端访问路由地址为:/peis/factorsItem
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/factorsItem")
public class HazardFactorsItemController extends BaseController {

    private final IHazardFactorsItemService hazardFactorsItemService;

    /**
     * 查询危害因素必检项目关联列表
     */
    @SaCheckPermission("peis:factorsItem:list")
    @GetMapping("/list")
    public TableDataInfo<HazardFactorsItemVo> list(HazardFactorsItemBo bo, PageQuery pageQuery) {
        return hazardFactorsItemService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出危害因素必检项目关联列表
     */
    @SaCheckPermission("peis:factorsItem:export")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HazardFactorsItemBo bo, HttpServletResponse response) {
        List<HazardFactorsItemVo> list = hazardFactorsItemService.queryList(bo);
        ExcelUtil.exportExcel(list, "危害因素必检项目关联", HazardFactorsItemVo.class, response);
    }

    /**
     * 获取危害因素必检项目关联详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:factorsItem:query")
    @GetMapping("/{id}")
    public R<HazardFactorsItemVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(hazardFactorsItemService.queryById(id));
    }

    /**
     * 新增危害因素必检项目关联
     */
    @SaCheckPermission("peis:factorsItem:add")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HazardFactorsItemBo bo) {
        return toAjax(hazardFactorsItemService.insertByBo(bo));
    }

    /**
     * 修改危害因素必检项目关联
     */
    @SaCheckPermission("peis:factorsItem:edit")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HazardFactorsItemBo bo) {
        return toAjax(hazardFactorsItemService.updateByBo(bo));
    }

    /**
     * 删除危害因素必检项目关联
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:factorsItem:remove")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(hazardFactorsItemService.deleteWithValidByIds(List.of(ids), true));
    }
}
