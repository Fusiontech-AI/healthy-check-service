package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
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
import org.fxkc.peis.domain.bo.TjHazardFactorsItemBo;
import org.fxkc.peis.domain.vo.TjHazardFactorsItemVo;
import org.fxkc.peis.service.ITjHazardFactorsItemService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class TjHazardFactorsItemController extends BaseController {

    private final ITjHazardFactorsItemService tjHazardFactorsItemService;

    /**
     * 查询危害因素必检项目关联列表
     */
    @SaCheckPermission("peis:factorsItem:list")
    @GetMapping("/list")
    public TableDataInfo<TjHazardFactorsItemVo> list(TjHazardFactorsItemBo bo, PageQuery pageQuery) {
        return tjHazardFactorsItemService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出危害因素必检项目关联列表
     */
    @SaCheckPermission("peis:factorsItem:export")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjHazardFactorsItemBo bo, HttpServletResponse response) {
        List<TjHazardFactorsItemVo> list = tjHazardFactorsItemService.queryList(bo);
        ExcelUtil.exportExcel(list, "危害因素必检项目关联", TjHazardFactorsItemVo.class, response);
    }

    /**
     * 获取危害因素必检项目关联详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:factorsItem:query")
    @GetMapping("/{id}")
    public R<TjHazardFactorsItemVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjHazardFactorsItemService.queryById(id));
    }

    /**
     * 新增危害因素必检项目关联
     */
    @SaCheckPermission("peis:factorsItem:add")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjHazardFactorsItemBo bo) {
        return toAjax(tjHazardFactorsItemService.insertByBo(bo));
    }

    /**
     * 修改危害因素必检项目关联
     */
    @SaCheckPermission("peis:factorsItem:edit")
    @Log(title = "危害因素必检项目关联", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjHazardFactorsItemBo bo) {
        return toAjax(tjHazardFactorsItemService.updateByBo(bo));
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
        return toAjax(tjHazardFactorsItemService.deleteWithValidByIds(List.of(ids), true));
    }
}
