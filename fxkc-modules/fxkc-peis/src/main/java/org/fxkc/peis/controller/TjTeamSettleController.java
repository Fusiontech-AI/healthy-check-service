package org.fxkc.peis.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.core.validate.QueryGroup;
import org.fxkc.peis.domain.bo.TjTeamTaskDiscountSealBo;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.fxkc.common.idempotent.annotation.RepeatSubmit;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.peis.domain.vo.TjTeamSettleVo;
import org.fxkc.peis.domain.bo.TjTeamSettleBo;
import org.fxkc.peis.service.ITjTeamSettleService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 体检单位结账
 * 前端访问路由地址为:/peis/teamSettle
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamSettle")
public class TjTeamSettleController extends BaseController {

    private final ITjTeamSettleService tjTeamSettleService;

    /**
     * 查询体检单位结账列表
     */
    @SaCheckPermission("peis:teamSettle:list")
    @GetMapping("/list")
    public TableDataInfo<TjTeamSettleVo> list(@Validated(QueryGroup.class) TjTeamSettleBo bo, PageQuery pageQuery) {
        return tjTeamSettleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出体检单位结账列表
     */
    @SaCheckPermission("peis:teamSettle:export")
    @Log(title = "体检单位结账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated(QueryGroup.class) TjTeamSettleBo bo, HttpServletResponse response) {
        List<TjTeamSettleVo> list = tjTeamSettleService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检单位结账", TjTeamSettleVo.class, response);
    }

    /**
     * 获取体检单位结账详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:teamSettle:query")
    @GetMapping("/{id}")
    public R<TjTeamSettleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjTeamSettleService.queryById(id));
    }

    /**
     * 新增体检单位结账
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.insertByBo(bo));
    }

    /**
     * 体检单位结账开票
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账开票", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/teamInvoice")
    public R<Void> teamInvoice(@Validated(EditGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.teamInvoice(bo));
    }

    /**
     * 体检单位结账发票作废
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账发票作废", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamInvalidInvoice")
    public R<Void> teamInvalidInvoice(@Validated(EditGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.teamInvalidInvoice(bo));
    }

    /**
     * 体检单位结账作废
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账作废", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamInvalidSettle")
    public R<Void> teamInvalidSettle(@Validated(EditGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.teamInvalidSettle(bo));
    }

    /**
     * 体检单位结账任务折扣
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账任务折扣", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamTaskDiscount")
    public R<Void> teamTaskDiscount(@Validated(EditGroup.class) @RequestBody TjTeamTaskDiscountSealBo bo) {
        return toAjax(tjTeamSettleService.teamTaskDiscount(bo));
    }

    /**
     * 体检单位结账封账
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账封账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamSettleSeal")
    public R<Void> teamSettleSeal(@Validated(EditGroup.class) @RequestBody TjTeamTaskDiscountSealBo bo) {
        return toAjax(tjTeamSettleService.teamSettleSeal(bo));
    }

    /**
     * 体检单位结账解除封账
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账解除封账", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamSettleUnseal")
    public R<Void> teamSettleUnseal(@Validated(EditGroup.class) @RequestBody TjTeamTaskDiscountSealBo bo) {
        return toAjax(tjTeamSettleService.teamSettleUnseal(bo));
    }

    /**
     * 体检单位结账审核通过
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账通过审核", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamSettleCheckPass")
    public R<Void> teamSettleCheckPass(@Validated(EditGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.teamSettleCheckPass(bo));
    }

    /**
     * 体检单位结账审核驳回
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账审核驳回", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamSettleCheckReject")
    public R<Void> teamSettleCheckReject(@Validated(EditGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.teamSettleCheckReject(bo));
    }

    /**
     * 删除体检单位结账
     */
    @SaCheckPermission("peis:teamSettle:remove")
    @Log(title = "体检单位结账", businessType = BusinessType.DELETE)
    @DeleteMapping
    public R<Void> remove(@Validated(EditGroup.class) @RequestBody TjTeamSettleBo bo) {
        return toAjax(tjTeamSettleService.deleteWithValidByIds(bo, true));
    }
}
