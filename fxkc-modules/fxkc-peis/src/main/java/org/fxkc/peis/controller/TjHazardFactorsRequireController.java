package org.fxkc.peis.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.TjHazardFactorsRequire;
import org.fxkc.peis.domain.bo.TjHazardFactorsCodeBo;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireBo;
import org.fxkc.peis.domain.bo.TjHazardFactorsRequireSaveBo;
import org.fxkc.peis.domain.vo.TjHazardFactorsRequireVo;
import org.fxkc.peis.service.ITjHazardFactorsRequireService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class TjHazardFactorsRequireController extends BaseController {

    private final ITjHazardFactorsRequireService tjHazardFactorsRequireService;

    /**
     * 查询危害因素必检项目主列表
     */
    @PostMapping(value = "/hazardFactorsQuery")
    public R<TjHazardFactorsRequireVo> hazardFactorsQuery(@RequestBody @Valid TjHazardFactorsRequireBo bo, PageQuery pageQuery)  {
        return R.ok(tjHazardFactorsRequireService.hazardFactorsQuery(bo, pageQuery));
    }

    /**
     * 危害因素必检项目新增或修改
     */
    @PostMapping(value = "/saveOrUpdate")
    @Log(title = "危害因素必检项目主", businessType = BusinessType.INSERTORUPDATE)
    @RepeatSubmit()
    public R<?> saveOrUpdate(@RequestBody @Valid TjHazardFactorsRequireSaveBo bo)  {
        tjHazardFactorsRequireService.saveOrUpdate(bo);
        return R.ok();
    }

    /**
     * 获取危害因素必检项目主详细信息
     *
     * @param id 主键
     */
    @GetMapping(value = "/hazardFactorsDetail")
    public R<TjHazardFactorsRequireVo> hazardFactorsDetail(@NotBlank(message = "主键id不能为空") String id)  {
        return R.ok(tjHazardFactorsRequireService.hazardFactorsDetail(id));
    }

    /**
     * 危害因素必检项目删除
     *
     * @param id 主键
     */
    @GetMapping(value = "/deleteById")
    @Log(title = "删除危害因素必检项目", businessType = BusinessType.DELETE)
    public R<Void> deleteById(@NotBlank(message = "主键id不能为空") Long id)  {
        return toAjax(tjHazardFactorsRequireService.deleteById(id));
    }

    /**
     * 删除危害因素必检项目主
     *
     * @param list 主键集合
     */
    @Log(title = "批量删除危害因素必检项目", businessType = BusinessType.DELETE)
    @PostMapping(value = "/batchDeleteByIds")
    public R<Void> batchDeleteByIds(@RequestBody @NotEmpty(message = "请选择要删除项") List<Long> list) {
        return toAjax(tjHazardFactorsRequireService.batchDeleteByIds(list));
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
        return toAjax(tjHazardFactorsRequireService.updateById(new TjHazardFactorsRequire()
            .setId(id)
            .setEnableStatus(enableStatus)));
    }

    /**
     * 根据危害因素编码、在岗状态查询必检项目
     */
    @PostMapping(value = "/queryItemByFactorsCodeAndDutyStauts")
    public R<List<TjHazardFactorsRequireVo.ItemBody>> queryItemByFactorsCodeAndDutyStatus(@RequestBody @Valid TjHazardFactorsCodeBo bo)  {
        return R.ok(tjHazardFactorsRequireService.queryItemByFactorsCodeAndDutyStatus(bo));
    }
}
