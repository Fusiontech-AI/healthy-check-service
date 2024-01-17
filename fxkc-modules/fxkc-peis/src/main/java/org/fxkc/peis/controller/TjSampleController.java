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
import org.fxkc.peis.domain.bo.TjSampleBatchUpdateBo;
import org.fxkc.peis.domain.bo.TjSampleBo;
import org.fxkc.peis.domain.bo.TjSamplePageBo;
import org.fxkc.peis.domain.bo.TjSampleUpdateBo;
import org.fxkc.peis.domain.vo.TjSampleInfoListVo;
import org.fxkc.peis.domain.vo.TjSampleVo;
import org.fxkc.peis.service.ITjSampleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检样本
 * 前端访问路由地址为:/peis/sample
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/sample")
public class TjSampleController extends BaseController {

    private final ITjSampleService tjSampleService;

    /**
     * 查询体检样本列表
     */
    @SaCheckPermission("peis:sample:list")
    @GetMapping("/list")
    public TableDataInfo<TjSampleVo> list(TjSamplePageBo bo, PageQuery pageQuery) {
        return tjSampleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检样本列表
     */
    @SaCheckPermission("peis:sample:export")
    @Log(title = "体检样本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjSampleBo bo, HttpServletResponse response) {
        List<TjSampleVo> list = tjSampleService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检样本", TjSampleVo.class, response);
    }

    /**
     * 获取体检样本详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:sample:query")
    @GetMapping("/{id}")
    public R<TjSampleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjSampleService.queryById(id));
    }

    /**
     * 新增体检样本
     */
    @SaCheckPermission("peis:sample:add")
    @Log(title = "体检样本", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjSampleBo bo) {
        return toAjax(tjSampleService.insertByBo(bo));
    }

    /**
     * 修改体检样本
     */
    @SaCheckPermission("peis:sample:edit")
    @Log(title = "体检样本", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjSampleBo bo) {
        return toAjax(tjSampleService.updateByBo(bo));
    }

    /**
     * 删除体检样本
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:sample:remove")
    @Log(title = "体检样本", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjSampleService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 根据样本id查询样本下的项目列表信息
     */
    @GetMapping("/getCombinProjectBySampleId/{id}")
    public R<List<TjSampleInfoListVo>> getCombinProjectBySampleId(@NotNull(message = "样本主键不能为空")
                                                                      @PathVariable Long id) {
        return R.ok(tjSampleService.getCombinProjectBySampleId(id));
    }


    /**
     * 根据样本id新增和修改样本下的项目列表信息
     */
    @PostMapping("/updateCombinProjectBySampleId")
    public R<Void> updateCombinProjectBySampleId(@RequestBody @Valid TjSampleUpdateBo tjSampleUpdateBo) {
        return toAjax(tjSampleService.updateCombinProjectBySampleId(tjSampleUpdateBo));
    }


    /**
     * 批量禁用体检样本
     *
     * @param ids 主键串
     */
    @GetMapping("batchDisable/{ids}")
    public R<Void> batchDisable(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjSampleService.batchDisable(List.of(ids)));
    }


    /**
     * 批量修改体检样本类别
     *
     */
    @PostMapping("batchUpdateCategory")
    public R<Void> batchUpdateCategory(@RequestBody @Valid TjSampleBatchUpdateBo bo) {
        return toAjax(tjSampleService.batchUpdateCategory(bo));
    }
}
