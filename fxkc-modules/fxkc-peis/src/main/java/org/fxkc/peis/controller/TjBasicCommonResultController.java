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
import org.fxkc.peis.domain.bo.TjBasicCommonResultBo;
import org.fxkc.peis.domain.vo.TjBasicCommonResultVo;
import org.fxkc.peis.service.ITjBasicCommonResultService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检基础项目常见结果
 * 前端访问路由地址为:/peis/basicCommonResult
 *
 * @author JunBaiChen
 * @date 2024-03-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basicCommonResult")
public class TjBasicCommonResultController extends BaseController {

    private final ITjBasicCommonResultService tjBasicCommonResultService;

    /**
     * 查询体检基础项目常见结果列表
     */
    @SaCheckPermission("peis:basicCommonResult:list")
    @GetMapping("/list")
    public TableDataInfo<TjBasicCommonResultVo> list(TjBasicCommonResultBo bo, PageQuery pageQuery) {
        return tjBasicCommonResultService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检基础项目常见结果列表
     */
    @SaCheckPermission("peis:basicCommonResult:export")
    @Log(title = "体检基础项目常见结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjBasicCommonResultBo bo, HttpServletResponse response) {
        List<TjBasicCommonResultVo> list = tjBasicCommonResultService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检基础项目常见结果", TjBasicCommonResultVo.class, response);
    }

    /**
     * 获取体检基础项目常见结果详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:basicCommonResult:query")
    @GetMapping("/{id}")
    public R<TjBasicCommonResultVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjBasicCommonResultService.queryById(id));
    }

    /**
     * 新增体检基础项目常见结果
     */
    @SaCheckPermission("peis:basicCommonResult:add")
    @Log(title = "体检基础项目常见结果", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjBasicCommonResultBo bo) {
        return toAjax(tjBasicCommonResultService.insertByBo(bo));
    }

    /**
     * 修改体检基础项目常见结果
     */
    @SaCheckPermission("peis:basicCommonResult:edit")
    @Log(title = "体检基础项目常见结果", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjBasicCommonResultBo bo) {
        return toAjax(tjBasicCommonResultService.updateByBo(bo));
    }

    /**
     * 删除体检基础项目常见结果
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:basicCommonResult:remove")
    @Log(title = "体检基础项目常见结果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjBasicCommonResultService.deleteWithValidByIds(List.of(ids), true));
    }
}
