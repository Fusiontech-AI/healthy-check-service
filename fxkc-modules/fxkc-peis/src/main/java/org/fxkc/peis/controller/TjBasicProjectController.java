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
import org.fxkc.peis.domain.bo.TjBasicProjectBo;
import org.fxkc.peis.domain.vo.TjBasicProjectVo;
import org.fxkc.peis.service.ITjBasicProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检基础项目
 * 前端访问路由地址为:/peis/basicProject
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basicProject")
public class TjBasicProjectController extends BaseController {

    private final ITjBasicProjectService tjBasicProjectService;

    /**
     * 查询体检基础项目列表
     */
    @SaCheckPermission("peis:basicProject:list")
    @GetMapping("/list")
    public TableDataInfo<TjBasicProjectVo> list(TjBasicProjectBo bo, PageQuery pageQuery) {
        return tjBasicProjectService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检基础项目列表
     */
    @SaCheckPermission("peis:basicProject:export")
    @Log(title = "体检基础项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjBasicProjectBo bo, HttpServletResponse response) {
        List<TjBasicProjectVo> list = tjBasicProjectService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检基础项目", TjBasicProjectVo.class, response);
    }

    /**
     * 获取体检基础项目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:basicProject:query")
    @GetMapping("/{id}")
    public R<TjBasicProjectVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjBasicProjectService.queryById(id));
    }

    /**
     * 新增体检基础项目
     */
    @SaCheckPermission("peis:basicProject:add")
    @Log(title = "体检基础项目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjBasicProjectBo bo) {
        return toAjax(tjBasicProjectService.insertByBo(bo));
    }

    /**
     * 修改体检基础项目
     */
    @SaCheckPermission("peis:basicProject:edit")
    @Log(title = "体检基础项目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjBasicProjectBo bo) {
        return toAjax(tjBasicProjectService.updateByBo(bo));
    }

    /**
     * 删除体检基础项目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:basicProject:remove")
    @Log(title = "体检基础项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjBasicProjectService.deleteWithValidByIds(List.of(ids), true));
    }

}
