package org.fxkc.peis.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.peis.domain.bo.HazardFactorsRequireSaveBo;
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
     * 危害因素必检项目新增或修改
     */
    @PostMapping(value = "/saveOrUpdate")
    @Log(title = "危害因素必检项目主", businessType = BusinessType.INSERTORUPDATE)
    @RepeatSubmit()
    public R<?> saveOrUpdate(@RequestBody @Valid HazardFactorsRequireSaveBo bo)  {
        hazardFactorsRequireService.saveOrUpdate(bo);
        return R.ok();
    }

    /**
     * 获取危害因素必检项目主详细信息
     *
     * @param id 主键
     */
    @GetMapping(value = "/hazardFactorsDetail")
    public R<HazardFactorsRequireVo> hazardFactorsDetail(@NotBlank(message = "主键id不能为空") String id)  {
        return R.ok(hazardFactorsRequireService.hazardFactorsDetail(id));
    }

    /**
     * 危害因素必检项目删除
     *
     * @param id 主键
     */
    @GetMapping(value = "/deleteById")
    @Log(title = "删除危害因素必检项目", businessType = BusinessType.DELETE)
    public R<Void> deleteById(@NotBlank(message = "主键id不能为空") Long id)  {
        return toAjax(hazardFactorsRequireService.deleteById(id));
    }

    /**
     * 删除危害因素必检项目主
     *
     * @param list 主键集合
     */
    @Log(title = "批量删除危害因素必检项目", businessType = BusinessType.DELETE)
    @PostMapping(value = "/batchDeleteByIds")
    public R<Void> batchDeleteByIds(@RequestBody @NotEmpty(message = "请选择要删除项") List<Long> list) {
        return toAjax(hazardFactorsRequireService.batchDeleteByIds(list));
    }

    /**
     * 危害因素必检项目启用禁用
     *
     * @param id 主键
     * @param enableStatus 启用状态(0:启用1:不启用)
     */
    @GetMapping(value = "/isEnableById")
    @Log(title = "危害因素必检项目启用禁用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    public R<Void> isEnableById(@NotBlank(message = "主键id不能为空") Long id,
                             @NotBlank(message = "启用状态不能为空") String enableStatus)  {
        return toAjax( hazardFactorsRequireService.updateById(new HazardFactorsRequire()
            .setId(id)
            .setEnableStatus(enableStatus)));
    }
}
