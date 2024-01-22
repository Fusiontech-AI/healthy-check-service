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
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.domain.bo.TjRegisterBo;
import org.fxkc.peis.service.ITjRegisterService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 体检人员登记信息
 * 前端访问路由地址为:/peis/register
 *
 * @author JunBaiChen
 * @date 2024-01-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class TjRegisterController extends BaseController {

    private final ITjRegisterService tjRegisterService;

    /**
     * 查询体检人员登记信息列表
     */
    @SaCheckPermission("peis:register:list")
    @GetMapping("/list")
    public TableDataInfo<TjRegisterVo> list(TjRegisterBo bo, PageQuery pageQuery) {
        return tjRegisterService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检人员登记信息列表
     */
    @SaCheckPermission("peis:register:export")
    @Log(title = "体检人员登记信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjRegisterBo bo, HttpServletResponse response) {
        List<TjRegisterVo> list = tjRegisterService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检人员登记信息", TjRegisterVo.class, response);
    }

    /**
     * 获取体检人员登记信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:register:query")
    @GetMapping("/{id}")
    public R<TjRegisterVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjRegisterService.queryById(id));
    }

    /**
     * 新增体检人员登记信息
     */
    @SaCheckPermission("peis:register:add")
    @Log(title = "体检人员登记信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjRegisterBo bo) {
        return toAjax(tjRegisterService.insertByBo(bo));
    }

    /**
     * 修改体检人员登记信息
     */
    @SaCheckPermission("peis:register:edit")
    @Log(title = "体检人员登记信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjRegisterBo bo) {
        return toAjax(tjRegisterService.updateByBo(bo));
    }

    /**
     * 删除体检人员登记信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:register:remove")
    @Log(title = "体检人员登记信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjRegisterService.deleteWithValidByIds(List.of(ids), true));
    }
}
