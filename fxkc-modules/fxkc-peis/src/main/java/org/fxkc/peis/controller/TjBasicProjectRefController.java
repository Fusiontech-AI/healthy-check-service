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
import org.fxkc.peis.domain.bo.TjBasicProjectRefBo;
import org.fxkc.peis.domain.vo.TjBasicProjectRefVo;
import org.fxkc.peis.service.ITjBasicProjectRefService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检基础项目参考信息
 * 前端访问路由地址为:/peis/basicProjectRef
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basicProjectRef")
public class TjBasicProjectRefController extends BaseController {

    private final ITjBasicProjectRefService tjBasicProjectRefService;

    /**
     * 查询体检基础项目参考信息列表
     */
    @SaCheckPermission("peis:basicProjectRef:list")
    @GetMapping("/list")
    public TableDataInfo<TjBasicProjectRefVo> list(TjBasicProjectRefBo bo, PageQuery pageQuery) {
        return tjBasicProjectRefService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检基础项目参考信息列表
     */
    @SaCheckPermission("peis:basicProjectRef:export")
    @Log(title = "体检基础项目参考信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjBasicProjectRefBo bo, HttpServletResponse response) {
        List<TjBasicProjectRefVo> list = tjBasicProjectRefService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检基础项目参考信息", TjBasicProjectRefVo.class, response);
    }

    /**
     * 获取体检基础项目参考信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:basicProjectRef:query")
    @GetMapping("/{id}")
    public R<TjBasicProjectRefVo> getInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long id) {
        return R.ok(tjBasicProjectRefService.queryById(id));
    }

    /**
     * 新增体检基础项目参考信息
     */
    @SaCheckPermission("peis:basicProjectRef:add")
    @Log(title = "体检基础项目参考信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjBasicProjectRefBo bo) {
        return toAjax(tjBasicProjectRefService.insertByBo(bo));
    }

    /**
     * 修改体检基础项目参考信息
     */
    @SaCheckPermission("peis:basicProjectRef:edit")
    @Log(title = "体检基础项目参考信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjBasicProjectRefBo bo) {
        return toAjax(tjBasicProjectRefService.updateByBo(bo));
    }

    /**
     * 删除体检基础项目参考信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:basicProjectRef:remove")
    @Log(title = "体检基础项目参考信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjBasicProjectRefService.deleteWithValidByIds(List.of(ids), true));
    }
}
