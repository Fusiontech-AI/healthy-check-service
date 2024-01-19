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
import org.fxkc.peis.domain.vo.TjTeamGroupItemVo;
import org.fxkc.peis.domain.bo.TjTeamGroupItemBo;
import org.fxkc.peis.service.ITjTeamGroupItemService;
import org.fxkc.common.mybatis.core.page.TableDataInfo;

/**
 * 团检分组对应项目
 * 前端访问路由地址为:/peis/teamGroupItem
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/teamGroupItem")
public class TjTeamGroupItemController extends BaseController {

    private final ITjTeamGroupItemService tjTeamGroupItemService;

    /**
     * 查询团检分组对应项目列表
     */
    @SaCheckPermission("peis:teamGroupItem:list")
    @GetMapping("/list")
    public TableDataInfo<TjTeamGroupItemVo> list(TjTeamGroupItemBo bo, PageQuery pageQuery) {
        return tjTeamGroupItemService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出团检分组对应项目列表
     */
    @SaCheckPermission("peis:teamGroupItem:export")
    @Log(title = "团检分组对应项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjTeamGroupItemBo bo, HttpServletResponse response) {
        List<TjTeamGroupItemVo> list = tjTeamGroupItemService.queryList(bo);
        ExcelUtil.exportExcel(list, "团检分组对应项目", TjTeamGroupItemVo.class, response);
    }

    /**
     * 获取团检分组对应项目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:teamGroupItem:query")
    @GetMapping("/{id}")
    public R<TjTeamGroupItemVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjTeamGroupItemService.queryById(id));
    }

    /**
     * 新增团检分组对应项目
     */
    @SaCheckPermission("peis:teamGroupItem:add")
    @Log(title = "团检分组对应项目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjTeamGroupItemBo bo) {
        return toAjax(tjTeamGroupItemService.insertByBo(bo));
    }

    /**
     * 修改团检分组对应项目
     */
    @SaCheckPermission("peis:teamGroupItem:edit")
    @Log(title = "团检分组对应项目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjTeamGroupItemBo bo) {
        return toAjax(tjTeamGroupItemService.updateByBo(bo));
    }

    /**
     * 删除团检分组对应项目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:teamGroupItem:remove")
    @Log(title = "团检分组对应项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjTeamGroupItemService.deleteWithValidByIds(List.of(ids), true));
    }
}
