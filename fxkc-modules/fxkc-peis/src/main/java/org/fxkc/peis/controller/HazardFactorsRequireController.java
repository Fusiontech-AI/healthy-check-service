package org.fxkc.peis.controller;

import java.util.List;

import jakarta.validation.Valid;
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
import org.fxkc.peis.domain.vo.HazardFactorsRequireVo;
import org.fxkc.peis.domain.bo.HazardFactorsRequireBo;
import org.fxkc.peis.service.IHazardFactorsRequireService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 危害因素必检项目主
 * 前端访问路由地址为:/peis/factorsRequire
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/factorsRequire")
public class HazardFactorsRequireController extends BaseController {

    private final IHazardFactorsRequireService hazardFactorsRequireService;

    /**
     * 查询危害因素必检项目主列表
     */
    @PostMapping(value = "/hazardFactorsQuery")
    public R<HazardFactorsRequireVo> hazardFactorsQuery(@RequestBody @Valid HazardFactorsRequireBo bo, PageQuery pageQuery)  {
        return R.ok(hazardFactorsRequireService.hazardFactorsQuery(bo, pageQuery));
    }

    /**
     * 导出危害因素必检项目主列表
     */
    @SaCheckPermission("peis:factorsRequire:export")
    @Log(title = "危害因素必检项目主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HazardFactorsRequireBo bo, HttpServletResponse response) {
        List<HazardFactorsRequireVo> list = hazardFactorsRequireService.queryList(bo);
        ExcelUtil.exportExcel(list, "危害因素必检项目主", HazardFactorsRequireVo.class, response);
    }

    /**
     * 获取危害因素必检项目主详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:factorsRequire:query")
    @GetMapping("/{id}")
    public R<HazardFactorsRequireVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(hazardFactorsRequireService.queryById(id));
    }

    /**
     * 新增危害因素必检项目主
     */
    @SaCheckPermission("peis:factorsRequire:add")
    @Log(title = "危害因素必检项目主", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody HazardFactorsRequireBo bo) {
        return toAjax(hazardFactorsRequireService.insertByBo(bo));
    }

    /**
     * 修改危害因素必检项目主
     */
    @SaCheckPermission("peis:factorsRequire:edit")
    @Log(title = "危害因素必检项目主", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody HazardFactorsRequireBo bo) {
        return toAjax(hazardFactorsRequireService.updateByBo(bo));
    }

    /**
     * 删除危害因素必检项目主
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:factorsRequire:remove")
    @Log(title = "危害因素必检项目主", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(hazardFactorsRequireService.deleteWithValidByIds(List.of(ids), true));
    }
}
