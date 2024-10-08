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
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.TjRegisterPageVo;
import org.fxkc.peis.domain.vo.TjRegisterVo;
import org.fxkc.peis.service.ITjRegisterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检登记人员信息
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

    /**
     * 体检人员冻结
     *
     * @param ids 主键串
     */
    @Log(title = "体检人员冻结", businessType = BusinessType.UPDATE)
    @PutMapping("/freeze/{ids}")
    @RepeatSubmit()
    public R<Void> freeze(@NotEmpty(message = "主键不能为空")
                                  @PathVariable Long[] ids){
        return toAjax(tjRegisterService.freeze(List.of(ids)));
    }

    /**
     * 体检人员解冻
     *
     * @param ids 主键串
     */
    @Log(title = "体检人员冻结", businessType = BusinessType.UPDATE)
    @PutMapping("/unfreeze/{ids}")
    @RepeatSubmit()
    public R<Void> unfreeze(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids){
        return toAjax(tjRegisterService.unfreeze(List.of(ids)));
    }

    /**
     * 体检项目登记,报道,暂存(或变更)
     */
    @Log(title = "体检项目登记保存,报道,暂存或变更", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/changeRegCombin")
    public R<Void> changeRegCombin(@Valid @RequestBody TjRegCombinAddBo bo) {
        return  toAjax(tjRegisterService.changeRegCombin(bo));
    }


    /**
     * 体检项目批量报到
     */
    @Log(title = "体检项目批量报到", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/batchReport")
    public R<Void> batchReport(@RequestBody List<Long> regIds) {
        return  toAjax(tjRegisterService.batchReport(regIds));
    }

    /**
     * 体检项目诊断结果保存
     */
    @Log(title = "体检项目诊断结果保存", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/saveDiagnosis")
    public R<Void> saveDiagnosis(@Valid @RequestBody TjRegSaveDiagnosisBo bo) {
        return  toAjax(tjRegisterService.saveDiagnosis(bo));
    }

    /**
     * 体检替检登记
     */
    @Log(title = "替检登记", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/changeRegReplaceInfo")
    public R<Void> changeRegReplaceInfo(@Validated(AddGroup.class) @RequestBody TjRegReplaceInfoBo bo) {
        return  toAjax(tjRegisterService.changeRegReplaceInfo(bo));
    }

    /**
     * 报告领取方式信息登记
     */
    @RepeatSubmit()
    @PostMapping("/changeReportReceiveWay")
    public R<Void> changeReportReceiveWay(@Validated(AddGroup.class) @RequestBody TjRegReceiveWayBo bo) {
        return  toAjax(tjRegisterService.changeReportReceiveWay(bo));
    }

    /**
     * 删除任务体检人员信息
     *
     * @param id 主键
     */
    @Log(title = "删除任务体检人员信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteTaskRegister/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        return toAjax(tjRegisterService.deleteTaskRegister(id));
    }

    /**
     * 合并档案
     */
    @RepeatSubmit()
    @PostMapping("/mergeArchives")
    public R<Void> mergeArchives(@RequestBody @Valid TjArchivesBo bo) {
        tjRegisterService.mergeArchives(bo);
        return R.ok();
    }


    /**
     * 团转个接口
     */
    @Log(title = "团转个", businessType = BusinessType.UPDATE)
    @PostMapping("/teamToPerson")
    @RepeatSubmit()
    public R<Void> teamToPerson(@RequestBody TjRegTeamToPersonBo bo){
        return toAjax(tjRegisterService.teamToPerson(bo));
    }

    /**
     * 个转团接口
     */
    @Log(title = "个转团", businessType = BusinessType.UPDATE)
    @PostMapping("/personToTeam")
    @RepeatSubmit()
    public R<Void> personToTeam(@RequestBody TjRegPersonToTeamBo bo){
        return toAjax(tjRegisterService.personToTeam(bo));
    }

    /**
     * 查询该证件号第几次体检
     */
    @PostMapping("/getPeTimes")
    public R<Long> getPeTimes(@RequestBody TjRegPeTimesBo bo){
        return R.ok(tjRegisterService.getPeTimes(bo));
    }
}
