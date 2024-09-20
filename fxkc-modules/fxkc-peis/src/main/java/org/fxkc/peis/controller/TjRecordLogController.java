package org.fxkc.peis.controller;

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
import org.fxkc.peis.domain.bo.TjRecordLogBo;
import org.fxkc.peis.domain.vo.TjRecordLogVo;
import org.fxkc.peis.service.ITjRecordLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检操作记录日志
 * 前端访问路由地址为:/peis/recordLog
 *
 * @author JunBaiChen
 * @date 2024-03-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/recordLog")
public class TjRecordLogController extends BaseController {

    private final ITjRecordLogService tjRecordLogService;

    /**
     * 查询体检操作记录日志列表
     */
    @GetMapping("/list")
    public TableDataInfo<TjRecordLogVo> list(TjRecordLogBo bo, PageQuery pageQuery) {
        return tjRecordLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检操作记录日志列表
     */
    @Log(title = "体检操作记录日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjRecordLogBo bo, HttpServletResponse response) {
        List<TjRecordLogVo> list = tjRecordLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检操作记录日志", TjRecordLogVo.class, response);
    }

    /**
     * 获取体检操作记录日志详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<TjRecordLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjRecordLogService.queryById(id));
    }

    /**
     * 新增体检操作记录日志
     */
    @Log(title = "体检操作记录日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjRecordLogBo bo) {
        return toAjax(tjRecordLogService.insertByBo(bo));
    }

    /**
     * 修改体检操作记录日志
     */
    @Log(title = "体检操作记录日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjRecordLogBo bo) {
        return toAjax(tjRecordLogService.updateByBo(bo));
    }

    /**
     * 删除体检操作记录日志
     *
     * @param ids 主键串
     */
    @Log(title = "体检操作记录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjRecordLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
