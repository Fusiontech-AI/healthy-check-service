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
 * 电子导检单
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
     * 新增电子导检单
     */
    @Log(title = "新增电子导检单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) TjGuideSheetLogBo bo) {
        return toAjax(tjGuideSheetLogService.insertByBo(bo));
    }


    /**
     * 删除电子导检单
     *
     * @param ids 主键串
     */
    @Log(title = "删除电子导检单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjGuideSheetLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
