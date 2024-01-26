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
import org.fxkc.peis.domain.bo.TjRegisterAddBo;
import org.fxkc.peis.domain.bo.TjRegisterBo;
import org.fxkc.peis.domain.bo.TjRegisterPageBo;
import org.fxkc.peis.domain.bo.TjRegisterSingleBo;
import org.fxkc.peis.domain.vo.TjRegisterPageVo;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.service.ITjRegisterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/page")
    public TableDataInfo<TjRegisterPageVo> page(TjRegisterPageBo bo, PageQuery pageQuery) {
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
    public R<Long> add(@Validated(AddGroup.class) @RequestBody TjRegisterAddBo bo) {
        return R.ok(tjRegisterService.insertByBo(bo));
    }

    /**
     * 修改体检人员登记信息
     */
    @Log(title = "体检人员登记信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit(interval = 2000)
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

    /**
     * 取消体检人员登记
     *
     * @param ids 主键串
     */
    @Log(title = "取消体检人员登记", businessType = BusinessType.UPDATE)
    @PutMapping("/cancelRegister/{ids}")
    @RepeatSubmit()
    public R<Void> cancelRegistration(@NotEmpty(message = "主键不能为空")
                                      @PathVariable Long[] ids){
        return toAjax(tjRegisterService.cancelRegistration(List.of(ids)));
    }

    /**
     * 体检人员再次登记
     *
     * @param ids 主键串
     */
    @Log(title = "体检人员再次登记", businessType = BusinessType.UPDATE)
    @PutMapping("/reRegistration/{ids}")
    @RepeatSubmit()
    public R<Void> reRegistration(@NotEmpty(message = "主键不能为空")
                                      @PathVariable Long[] ids){
        return toAjax(tjRegisterService.reRegistration(List.of(ids)));
    }

    /**
     * 根据条件查询单个体检人员信息
     */
    @GetMapping("/getSingleInfo")
    public R<TjRegisterVo> getSingleInfo(TjRegisterSingleBo bo) {
        return R.ok(tjRegisterService.getSingleInfo(bo));
    }
}
