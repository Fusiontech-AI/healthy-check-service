package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import org.fxkc.peis.domain.bo.TjCombinationProjectAddBo;
import org.fxkc.peis.domain.bo.TjCombinationProjectBo;
import org.fxkc.peis.domain.bo.TjCompulsoryInspectionProjectBo;
import org.fxkc.peis.domain.bo.TjOtherCompulsoryInspectionBo;
import org.fxkc.peis.domain.vo.TjCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjCombinationProjectVo;
import org.fxkc.peis.service.ITjCombinationProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检组合项目
 * 前端访问路由地址为:/peis/combinationProject
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/combinationProject")
public class TjCombinationProjectController extends BaseController {

    private final ITjCombinationProjectService tjCombinationProjectService;

    /**
     * 查询体检组合项目列表
     */
    @SaCheckPermission("peis:combinationProject:list")
    @GetMapping("/list")
    public TableDataInfo<TjCombinationProjectListVo> list(TjCombinationProjectBo bo, PageQuery pageQuery) {
        return tjCombinationProjectService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检组合项目列表
     */
    @SaCheckPermission("peis:combinationProject:export")
    @Log(title = "体检组合项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjCombinationProjectBo bo, HttpServletResponse response) {
        List<TjCombinationProjectVo> list = tjCombinationProjectService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检组合项目", TjCombinationProjectVo.class, response);
    }

    /**
     * 获取体检组合项目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:combinationProject:query")
    @GetMapping("/{id}")
    public R<TjCombinationProjectVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjCombinationProjectService.queryById(id));
    }

    /**
     * 新增体检组合项目
     */
    @SaCheckPermission("peis:combinationProject:add")
    @Log(title = "体检组合项目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjCombinationProjectAddBo bo) {
        return toAjax(tjCombinationProjectService.insertByBo(bo));
    }

    /**
     * 修改体检组合项目
     */
    @SaCheckPermission("peis:combinationProject:edit")
    @Log(title = "体检组合项目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjCombinationProjectAddBo bo) {
        return toAjax(tjCombinationProjectService.updateByBo(bo));
    }

    /**
     * 删除体检组合项目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:combinationProject:remove")
    @Log(title = "体检组合项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjCombinationProjectService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 查询必检组合项目
     */
    @PostMapping("/queryCompulsoryInspectionProject")
    public R<List<TjCombinationProjectListVo>> queryCompulsoryInspectionProject(@RequestBody @Valid TjCompulsoryInspectionProjectBo bo) {
        return R.ok(tjCombinationProjectService.queryCompulsoryInspectionProject(bo));
    }

    /**
     * 查询其他必检组合项目
     */
    @PostMapping("/queryOtherCompulsoryInspection")
    public R<List<TjCombinationProjectListVo>> queryOtherCompulsoryInspection(@RequestBody @Valid TjOtherCompulsoryInspectionBo bo) {
        return R.ok(tjCombinationProjectService.queryOtherCompulsoryInspection(bo));
    }
}
