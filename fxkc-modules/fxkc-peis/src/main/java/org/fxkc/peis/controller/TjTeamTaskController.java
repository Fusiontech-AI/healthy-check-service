package org.fxkc.peis.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.peis.domain.bo.TjTeamTaskQueryBo;
import org.fxkc.peis.domain.bo.VerifyGroupBo;
import org.fxkc.peis.domain.vo.TjTeamTaskDetailVo;
import org.fxkc.peis.domain.vo.VerifyMessageVo;
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
import org.fxkc.peis.domain.vo.TjTeamTaskVo;
import org.fxkc.peis.domain.bo.TjTeamTaskBo;
import org.fxkc.peis.service.ITjTeamTaskService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

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
     * 导出团检任务管理列表
     */
    @SaCheckPermission("peis:teamTask:export")
    @Log(title = "团检任务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTeamTaskQueryBo bo, HttpServletResponse response) {
        List<TjTeamTaskVo> list = tjTeamTaskService.queryList(bo);
        ExcelUtil.exportExcel(list, "团检任务管理", TjTeamTaskVo.class, response);
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
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjTeamTaskBo bo) {
        return toAjax(tjTeamTaskService.insertByBo(bo));
    }

    /**
     * 修改团检任务管理
     */
    @SaCheckPermission("peis:teamTask:edit")
    @Log(title = "团检任务管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjTeamTaskBo bo) {
        return toAjax(tjTeamTaskService.updateByBo(bo));
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
    public R<VerifyMessageVo> verifyGroupData(@RequestBody @Valid VerifyGroupBo bo) {
        return R.ok(tjTeamTaskService.verifyGroupData(bo));
    }
}
