package org.fxkc.peis.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.fxkc.peis.domain.bo.TjHazardFactorsCodeBo;
import org.fxkc.peis.domain.bo.TjTeamGroupUpdateBo;
import org.fxkc.peis.domain.vo.TjHazardFactorsRequireVo;
import org.fxkc.peis.domain.vo.TjTeamGroupDetailVo;
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
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.domain.bo.TjTeamGroupBo;
import org.fxkc.peis.service.ITjTeamGroupService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 团检分组信息
 * 前端访问路由地址为:/peis/teamGroup
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamGroup")
public class TjTeamGroupController extends BaseController {

    private final ITjTeamGroupService tjTeamGroupService;

    /**
     * 查询团检分组信息列表
     */
    @SaCheckPermission("peis:teamGroup:list")
    @GetMapping("/list")
    public TableDataInfo<TjTeamGroupVo> list(TjTeamGroupBo bo, PageQuery pageQuery) {
        return tjTeamGroupService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出团检分组信息列表
     */
    @SaCheckPermission("peis:teamGroup:export")
    @Log(title = "团检分组信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTeamGroupBo bo, HttpServletResponse response) {
        List<TjTeamGroupVo> list = tjTeamGroupService.queryList(bo);
        ExcelUtil.exportExcel(list, "团检分组信息", TjTeamGroupVo.class, response);
    }

    /**
     * 获取团检分组信息详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<TjTeamGroupDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjTeamGroupService.queryById(id));
    }

    /**
     * 删除团检分组信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamGroup:remove")
    @Log(title = "团检分组信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamGroupService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 修改任务项目分组信息
     */
    @PostMapping(value = "/updateGroupProjectInfo")
    public R<Void> updateGroupProjectInfo(@RequestBody @Valid List<TjTeamGroupUpdateBo> list)  {
        return toAjax(tjTeamGroupService.updateGroupProjectInfo(list));
    }
}
