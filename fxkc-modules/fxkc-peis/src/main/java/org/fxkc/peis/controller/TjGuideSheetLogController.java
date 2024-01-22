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
import org.fxkc.peis.domain.vo.TjGuideSheetLogVo;
import org.fxkc.peis.domain.bo.TjGuideSheetLogBo;
import org.fxkc.peis.service.ITjGuideSheetLogService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 导检单回收记录
 * 前端访问路由地址为:/peis/guideSheetLog
 *
 * @author zry
 * @date 2024-01-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/guideSheetLog")
public class TjGuideSheetLogController extends BaseController {

    private final ITjGuideSheetLogService tjGuideSheetLogService;

    /**
     * 查询导检单回收记录列表
     */
    @SaCheckPermission("peis:guideSheetLog:list")
    @GetMapping("/list")
    public TableDataInfo<TjGuideSheetLogVo> list(TjGuideSheetLogBo bo, PageQuery pageQuery) {
        return tjGuideSheetLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出导检单回收记录列表
     */
    @SaCheckPermission("peis:guideSheetLog:export")
    @Log(title = "导检单回收记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjGuideSheetLogBo bo, HttpServletResponse response) {
        List<TjGuideSheetLogVo> list = tjGuideSheetLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "导检单回收记录", TjGuideSheetLogVo.class, response);
    }

    /**
     * 获取导检单回收记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:guideSheetLog:query")
    @GetMapping("/{id}")
    public R<TjGuideSheetLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjGuideSheetLogService.queryById(id));
    }

    /**
     * 新增导检单回收记录
     */
    @SaCheckPermission("peis:guideSheetLog:add")
    @Log(title = "导检单回收记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) TjGuideSheetLogBo bo) {
        return toAjax(tjGuideSheetLogService.insertByBo(bo));
    }

    /**
     * 修改导检单回收记录
     */
    @SaCheckPermission("peis:guideSheetLog:edit")
    @Log(title = "导检单回收记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjGuideSheetLogBo bo) {
        return toAjax(tjGuideSheetLogService.updateByBo(bo));
    }

    /**
     * 删除导检单回收记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:guideSheetLog:remove")
    @Log(title = "导检单回收记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjGuideSheetLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
