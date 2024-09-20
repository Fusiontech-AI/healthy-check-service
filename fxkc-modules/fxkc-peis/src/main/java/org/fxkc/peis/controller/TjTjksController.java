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
import org.fxkc.peis.domain.bo.TjTjksBo;
import org.fxkc.peis.domain.vo.TjTjksBasicNameVo;
import org.fxkc.peis.domain.vo.TjTjksVo;
import org.fxkc.peis.service.ITjTjksService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检科室
 * 前端访问路由地址为:/peis/tjks
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tjks")
public class TjTjksController extends BaseController {

    private final ITjTjksService tjTjksService;

    /**
     * 查询体检科室列表
     */
    @SaCheckPermission("peis:tjks:list")
    @GetMapping("/list")
    public TableDataInfo<TjTjksVo> list(TjTjksBo bo, PageQuery pageQuery) {
        return tjTjksService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检科室列表
     */
    @SaCheckPermission("peis:tjks:export")
    @Log(title = "体检科室", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTjksBo bo, HttpServletResponse response) {
        List<TjTjksVo> list = tjTjksService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检科室", TjTjksVo.class, response);
    }

    /**
     * 获取体检科室详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:tjks:query")
    @GetMapping("/{id}")
    public R<TjTjksVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjTjksService.queryById(id));
    }

    /**
     * 新增体检科室
     */
    @SaCheckPermission("peis:tjks:add")
    @Log(title = "体检科室", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjTjksBo bo) {
        return toAjax(tjTjksService.insertByBo(bo));
    }

    /**
     * 修改体检科室
     */
    @SaCheckPermission("peis:tjks:edit")
    @Log(title = "体检科室", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjTjksBo bo) {
        return toAjax(tjTjksService.updateByBo(bo));
    }

    /**
     * 删除体检科室
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:tjks:remove")
    @Log(title = "体检科室", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTjksService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 查询体检科室编码
     */
    @GetMapping("/getKsCode")
    public R<String> getKsCode() {
        return R.ok("操作成功!",tjTjksService.getKsCode());
    }


    /**
     * 根据基础项目查询相应科室列表信息
     */
    @GetMapping("queryTjKsListByBasicName")
    public R<List<TjTjksBasicNameVo>> queryTjKsListByBasicName(@RequestParam(value = "basicProjectName",required = false) String basicProjectName) {
        return R.ok(tjTjksService.queryTjKsListByBasicName(basicProjectName));
    }
}
