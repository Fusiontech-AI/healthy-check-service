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
import org.fxkc.peis.domain.bo.TjCombinationProjectInfoBo;
import org.fxkc.peis.domain.vo.TjCombinationProjectInfoVo;
import org.fxkc.peis.service.ITjCombinationProjectInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检组合项目详细信息
 * 前端访问路由地址为:/peis/combinationProjectInfo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/combinationProjectInfo")
public class TjCombinationProjectInfoController extends BaseController {

    private final ITjCombinationProjectInfoService tjCombinationProjectInfoService;

    /**
     * 查询体检组合项目详细信息列表
     */
    @SaCheckPermission("peis:combinationProjectInfo:list")
    @GetMapping("/list")
    public TableDataInfo<TjCombinationProjectInfoVo> list(TjCombinationProjectInfoBo bo, PageQuery pageQuery) {
        return tjCombinationProjectInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检组合项目详细信息列表
     */
    @SaCheckPermission("peis:combinationProjectInfo:export")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjCombinationProjectInfoBo bo, HttpServletResponse response) {
        List<TjCombinationProjectInfoVo> list = tjCombinationProjectInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检组合项目详细信息", TjCombinationProjectInfoVo.class, response);
    }

    /**
     * 获取体检组合项目详细信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:combinationProjectInfo:query")
    @GetMapping("/{id}")
    public R<TjCombinationProjectInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjCombinationProjectInfoService.queryById(id));
    }

    /**
     * 新增体检组合项目详细信息
     */
    @SaCheckPermission("peis:combinationProjectInfo:add")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjCombinationProjectInfoBo bo) {
        return toAjax(tjCombinationProjectInfoService.insertByBo(bo));
    }

    /**
     * 修改体检组合项目详细信息
     */
    @SaCheckPermission("peis:combinationProjectInfo:edit")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjCombinationProjectInfoBo bo) {
        return toAjax(tjCombinationProjectInfoService.updateByBo(bo));
    }

    /**
     * 删除体检组合项目详细信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:combinationProjectInfo:remove")
    @Log(title = "体检组合项目详细信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjCombinationProjectInfoService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 查询组合项目下基础项目信息
     */
    @PostMapping("/queryBasicListByCombinIds")
    public R<List<TjCombinationProjectInfoVo>> queryBasicListByCombinIds(@RequestBody List<Long> combinIds) {
        return R.ok(tjCombinationProjectInfoService.queryBasicListByCombinIds(combinIds));
    }
}
