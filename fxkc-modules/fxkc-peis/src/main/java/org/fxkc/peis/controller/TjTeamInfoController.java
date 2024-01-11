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
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.TjTeamInfo;
import org.fxkc.peis.domain.bo.TjTeamInfoBo;
import org.fxkc.peis.domain.vo.TjTeamInfoVo;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检单位信息
 * 前端访问路由地址为:/peis/teamInfo
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamInfo")
public class TjTeamInfoController extends BaseController {

    private final ITjTeamInfoService tjTeamInfoService;

    /**
     * 查询体检单位信息列表
     */
    @SaCheckPermission("peis:teamInfo:list")
    @GetMapping("/list")
    public R<List<TjTeamInfoVo>> list(TjTeamInfoBo bo) {
        return R.ok(tjTeamInfoService.queryList(bo));
    }

    /**
     * 导出体检单位信息列表
     */
    @SaCheckPermission("peis:teamInfo:export")
    @Log(title = "体检单位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTeamInfoBo bo, HttpServletResponse response) {
        List<TjTeamInfoVo> list = tjTeamInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检单位信息", TjTeamInfoVo.class, response);
    }

    /**
     * 获取体检单位信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:teamInfo:query")
    @GetMapping("/{id}")
    public R<TjTeamInfoVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(tjTeamInfoService.queryById(id));
    }

    /**
     * 新增体检单位信息
     */
    @SaCheckPermission("peis:teamInfo:add")
    @Log(title = "体检单位信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<TjTeamInfo> add(@Validated(AddGroup.class) @RequestBody TjTeamInfoBo bo) {
        return R.ok(tjTeamInfoService.insertByBo(bo));
    }

    /**
     * 修改体检单位信息
     */
    @SaCheckPermission("peis:teamInfo:edit")
    @Log(title = "体检单位信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<TjTeamInfo> edit(@Validated(EditGroup.class) @RequestBody TjTeamInfoBo bo) {
        return R.ok(tjTeamInfoService.updateByBo(bo));
    }

    /**
     * 删除体检单位信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamInfo:remove")
    @Log(title = "体检单位信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamInfoService.deleteWithValidByIds(List.of(ids), true));
    }

    @GetMapping("/getTeamNoById/{id}")
    public R<String> getTeamNoById(@NotNull(message = "所选上级单位Id不能为空") @PathVariable Long id) {
        return R.ok(tjTeamInfoService.getTeamNoById(id));
    }
}
