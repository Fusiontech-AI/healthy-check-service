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
import org.fxkc.peis.domain.vo.TjPackageInfoVo;
import org.fxkc.peis.domain.bo.TjPackageInfoBo;
import org.fxkc.peis.service.ITjPackageInfoService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 体检组合项目详细信息
 * 前端访问路由地址为:/peis/packageInfo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/packageInfo")
public class TjPackageInfoController extends BaseController {

    private final ITjPackageInfoService tjPackageInfoService;

    /**
     * 查询体检组合项目详细信息列表
     */
    @SaCheckPermission("peis:packageInfo:list")
    @GetMapping("/list")
    public TableDataInfo<TjPackageInfoVo> list(TjPackageInfoBo bo, PageQuery pageQuery) {
        return tjPackageInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检组合项目详细信息列表
     */
    @SaCheckPermission("peis:packageInfo:export")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjPackageInfoBo bo, HttpServletResponse response) {
        List<TjPackageInfoVo> list = tjPackageInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检组合项目详细信息", TjPackageInfoVo.class, response);
    }

    /**
     * 获取体检组合项目详细信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:packageInfo:query")
    @GetMapping("/{id}")
    public R<TjPackageInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjPackageInfoService.queryById(id));
    }

    /**
     * 新增体检组合项目详细信息
     */
    @SaCheckPermission("peis:packageInfo:add")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjPackageInfoBo bo) {
        return toAjax(tjPackageInfoService.insertByBo(bo));
    }

    /**
     * 修改体检组合项目详细信息
     */
    @SaCheckPermission("peis:packageInfo:edit")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjPackageInfoBo bo) {
        return toAjax(tjPackageInfoService.updateByBo(bo));
    }

    /**
     * 删除体检组合项目详细信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:packageInfo:remove")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjPackageInfoService.deleteWithValidByIds(List.of(ids), true));
    }
}
