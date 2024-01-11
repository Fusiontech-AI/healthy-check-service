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
import org.fxkc.peis.domain.vo.TjTeamDeptVo;
import org.fxkc.peis.domain.bo.TjTeamDeptBo;
import org.fxkc.peis.service.ITjTeamDeptService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 单位部门信息
 * 前端访问路由地址为:/peis/teamDept
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamDept")
public class TjTeamDeptController extends BaseController {

    private final ITjTeamDeptService tjTeamDeptService;

    /**
     * 查询单位部门信息列表
     */
    @SaCheckPermission("peis:teamDept:list")
    @GetMapping("/list")
    public TableDataInfo<TjTeamDeptVo> list(TjTeamDeptBo bo, PageQuery pageQuery) {
        return tjTeamDeptService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出单位部门信息列表
     */
    @SaCheckPermission("peis:teamDept:export")
    @Log(title = "单位部门信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTeamDeptBo bo, HttpServletResponse response) {
        List<TjTeamDeptVo> list = tjTeamDeptService.queryList(bo);
        ExcelUtil.exportExcel(list, "单位部门信息", TjTeamDeptVo.class, response);
    }

    /**
     * 获取单位部门信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:teamDept:query")
    @GetMapping("/{id}")
    public R<TjTeamDeptVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjTeamDeptService.queryById(id));
    }

    /**
     * 新增单位部门信息
     */
    @SaCheckPermission("peis:teamDept:add")
    @Log(title = "单位部门信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjTeamDeptBo bo) {
        return toAjax(tjTeamDeptService.insertByBo(bo));
    }

    /**
     * 修改单位部门信息
     */
    @SaCheckPermission("peis:teamDept:edit")
    @Log(title = "单位部门信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjTeamDeptBo bo) {
        return toAjax(tjTeamDeptService.updateByBo(bo));
    }

    /**
     * 删除单位部门信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamDept:remove")
    @Log(title = "单位部门信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamDeptService.deleteWithValidByIds(List.of(ids), true));
    }

    @GetMapping("/getDeptNoById/{id}")
    public R<String> getDeptNoById(@NotNull(message = "所选上级单位Id不能为空") @PathVariable Long id) {
        return R.ok(tjTeamDeptService.getDeptNoById(id));
    }
}
