package org.fxkc.peis.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.common.excel.core.ExcelResult;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
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
import org.fxkc.peis.service.ITjTeamTaskService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 团检任务管理
 * 前端访问路由地址为:/peis/teamTask
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamTask")
public class TjTeamTaskController extends BaseController {

    private final ITjTeamTaskService tjTeamTaskService;

    /**
     * 查询团检任务管理列表
     */
    @SaCheckPermission("peis:teamTask:list")
    @GetMapping("/list")
    public TableDataInfo<TjTeamTaskVo> list(TjTeamTaskQueryBo bo, PageQuery pageQuery) {
        return tjTeamTaskService.queryPageList(bo, pageQuery);
    }

    /**
     * 团检人员批量导出
     */
    @SaCheckPermission("peis:teamTask:export")
    @Log(title = "团检人员批量导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestParam("taskId") @NotNull(message = "任务id不能为空") Long taskId, HttpServletResponse response) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageSize(-1);
        TableDataInfo<TjTaskRegisterExportVo> result = tjTeamTaskService.queryTaskRegisterExportById(taskId, pageQuery);
        ExcelUtil.exportExcel(result.getRows(), "团检人员批量导出", TjTaskRegisterExportVo.class, response);
    }

    /**
     * 获取团检任务管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:teamTask:query")
    @GetMapping("/{id}")
    public R<TjTeamTaskDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjTeamTaskService.queryById(id));
    }

    /**
     * 新增团检任务管理
     */
    @SaCheckPermission("peis:teamTask:add")
    @Log(title = "团检任务管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<List<TjTeamGroupVo>> add(@Validated(AddGroup.class) @RequestBody TjTeamTaskBo bo) {
        return R.ok(tjTeamTaskService.insertByBo(bo));
    }

    /**
     * 修改团检任务管理
     */
    @SaCheckPermission("peis:teamTask:edit")
    @Log(title = "团检任务管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<List<TjTeamGroupVo>> edit(@Validated(EditGroup.class) @RequestBody TjTeamTaskBo bo) {
        return R.ok(tjTeamTaskService.updateByBo(bo));
    }

    /**
     * 删除团检任务管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamTask:remove")
    @Log(title = "团检任务管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamTaskService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 团检任务校验分组数据
     */
    @PostMapping("/verifyGroupData")
    @Log(title = "团检任务校验分组数据", businessType = BusinessType.OTHER)
    public R<TjVerifyMessageVo> verifyGroupData(@RequestBody @Valid List<TjGroupVerifyBo> list) {
        return R.ok(tjTeamTaskService.verifyGroupData(list));
    }

    /**
     * 团检任务校验分组套餐数据
     */
    @PostMapping("/verifyGroupPackageData")
    @Log(title = "团检任务校验分组套餐数据", businessType = BusinessType.OTHER)
    public R<TjVerifyMessageVo> verifyGroupPackageData(@RequestBody @Valid List<TjGroupVerifyPackageBo> list) {
        return R.ok(tjTeamTaskService.verifyGroupPackageData(list));
    }

    /**
     * 导出团检人员登记模板
     * @param templateType 模板类型(JKTJ:健康ZYJKTJ:职业健康体检FSTJ:放射体检)
     * @param taskId 任务id
     */
    @PostMapping("/exportRegisterTemplate")
    public void exportRegisterTemplate(@RequestParam("templateType") @NotBlank(message = "模板类型不能为空") String templateType,
                                       @RequestParam("taskId") @NotNull(message = "任务id不能为空") Long taskId,
                                       HttpServletResponse response) {
        tjTeamTaskService.exportRegisterTemplate(templateType,taskId, response);
    }

    /**
     * 导入团检人员
     */
    @Log(title = "团检任务导入人员", businessType = BusinessType.IMPORT)
    @PostMapping(value = "/importRegisterData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<ExcelResult<TjTaskOccupationalExportVo>> importRegisterData(@RequestPart("file") MultipartFile file, @ParameterObject @Valid TjTaskImportBo bo) throws IOException {
        return R.ok(tjTeamTaskService.importRegisterData(file, bo));
    }

    /**
     * 保存人员信息
     */
    @Log(title = "团检任务导入人员", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/insertRegisterData")
    public R<Void> insertRegisterData(@RequestBody TjRegisterImportBo bo) {
        tjTeamTaskService.insertRegisterData(bo);
        return R.ok();
    }

    /**
     * 团检任务审核基础信息
     */
    @GetMapping("/queryTaskReviewDetail/{id}")
    public R<TjTaskReviewDetailVo> queryTaskReviewDetail(@NotNull(message = "主键不能为空")
                                         @PathVariable Long id) {
        return R.ok(tjTeamTaskService.queryTaskReviewDetail(id));
    }

    /**
     * 团检任务审核分组信息
     */
    @GetMapping("/queryTaskReviewGroup")
    public TableDataInfo<TjTaskReviewGroupVo> queryTaskReviewGroup(@NotNull(message = "任务id不能为空") Long taskId,
                                                        PageQuery pageQuery) {
        return tjTeamTaskService.queryTaskReviewGroup(taskId, pageQuery);
    }

    /**
     * 团检任务审核人员列表
     */
    @GetMapping("/queryTaskReviewRegister")
    public TableDataInfo<TjTaskReviewRegisterVo> queryTaskReviewRegister(@NotNull(message = "任务id不能为空") Long taskId,
                                                           PageQuery pageQuery) {
        return tjTeamTaskService.queryTaskReviewRegister(taskId, pageQuery);
    }

    /**
     * 团检任务审核
     */
    @Log(title = "团检任务审核", businessType = BusinessType.REVIEW)
    @RepeatSubmit
    @PostMapping("/reviewTask")
    public R<Void> reviewTask(@RequestBody @Valid TjTaskReviewBo bo) {
        tjTeamTaskService.reviewTask(bo);
        return R.ok();
    }

    /**
     * 团检任务退回
     */
    @RepeatSubmit
    @PostMapping("/returnTask")
    public R<Void> returnTask(@RequestBody @NotEmpty(message = "所选审核任务不能为空") List<Long> idList) {
        tjTeamTaskService.returnTask(idList);
        return R.ok();
    }

    /**
     * 团检任务导入人员列表
     */
    @GetMapping("/queryTaskRegisterExportById")
    public TableDataInfo<TjTaskRegisterExportVo> queryTaskRegisterExportById(@NotNull(message = "任务id不能为空") Long taskId,
                                                                         PageQuery pageQuery) {
        return tjTeamTaskService.queryTaskRegisterExportById(taskId, pageQuery);
    }
}
