package org.fxkc.peis.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.common.core.validate.QueryGroup;
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
    public TableDataInfo<TjTeamSettleVo> list(TjTeamSettleBo bo, PageQuery pageQuery) {
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
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账开票", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/teamInvoice/{ids}")
    public R<Void> teamInvoice(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamSettleService.teamInvoice(List.of(ids)));
    }

    /**
     * 体检单位结账发票作废
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账发票作废", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamInvalidInvoice/{ids}")
    public R<Void> teamInvalidInvoice(@NotEmpty(message = "主键不能为空")
                               @PathVariable Long[] ids) {
        return toAjax(tjTeamSettleService.teamInvalidInvoice(List.of(ids)));
    }

    /**
     * 体检单位结账作废
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamSettle:add")
    @Log(title = "体检单位结账作废", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamInvalidSettle/{ids}")
    public R<Void> teamInvalidSettle(@NotEmpty(message = "主键不能为空")
                                      @PathVariable Long[] ids) {
        return toAjax(tjTeamSettleService.teamInvalidSettle(List.of(ids)));
    }

    /**
     * 体检单位结账审核通过
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账通过审核", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamSettleCheckPass/{ids}")
    public R<Void> teamSettleCheckPass(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        return toAjax(tjTeamSettleService.teamSettleCheckPass(List.of(ids)));
    }

    /**
     * 体检单位结账审核驳回
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamSettle:check")
    @Log(title = "体检单位结账审核驳回", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/teamSettleCheckReject/{ids}")
    public R<Void> teamSettleCheckReject(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(tjTeamSettleService.teamSettleCheckReject(List.of(ids)));
    }

    /**
     * 删除体检单位结账
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamSettle:remove")
    @Log(title = "体检单位结账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamSettleService.deleteWithValidByIds(List.of(ids), true));
    }
}
