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
import org.fxkc.peis.domain.bo.TjRegCombinationProjectBo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectDelayBo;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectListBo;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectListVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.peis.service.ITjRegCombinationProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检人员综合项目信息
 * 前端访问路由地址为:/peis/regCombinationProject
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/regCombinationProject")
public class TjRegCombinationProjectController extends BaseController {

    private final ITjRegCombinationProjectService tjRegCombinationProjectService;

    /**
     * 查询体检人员综合项目信息列表
     */
    @GetMapping("/list")
    public TableDataInfo<TjRegCombinationProjectListVo> list(TjRegCombinationProjectListBo bo, PageQuery pageQuery) {
        return tjRegCombinationProjectService.queryPageList(bo, pageQuery);
    }

    /**
     * 根据登记组合项目主键id查询基础项目列表
     */
    @GetMapping("queryRegBasicProjectList")
    public R<List<TjRegBasicProjectVo>> queryRegBasicProjectList(@RequestParam(value = "id") @NotNull(message = "登记组合项目id不能为空") Long id) {
        return R.ok(tjRegCombinationProjectService.queryRegBasicProjectList(id));
    }

    /**
     * 导出体检人员综合项目信息列表
     */
    @SaCheckPermission("peis:regCombinationProject:export")
    @Log(title = "体检人员综合项目信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TjRegCombinationProjectBo bo, HttpServletResponse response) {
        List<TjRegCombinationProjectVo> list = tjRegCombinationProjectService.queryList(bo);
        ExcelUtil.exportExcel(list, "体检人员综合项目信息", TjRegCombinationProjectVo.class, response);
    }

    /**
     * 获取体检人员综合项目信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("peis:regCombinationProject:query")
    @GetMapping("/{id}")
    public R<TjRegCombinationProjectVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(tjRegCombinationProjectService.queryById(id));
    }

    /**
     * 新增体检人员综合项目信息
     */
    @SaCheckPermission("peis:regCombinationProject:add")
    @Log(title = "体检人员综合项目信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TjRegCombinationProjectBo bo) {
        return toAjax(tjRegCombinationProjectService.insertByBo(bo));
    }

    /**
     * 修改体检人员综合项目信息
     */
    @SaCheckPermission("peis:regCombinationProject:edit")
    @Log(title = "体检人员综合项目信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TjRegCombinationProjectBo bo) {
        return toAjax(tjRegCombinationProjectService.updateByBo(bo));
    }

    /**
     * 删除体检人员综合项目信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("peis:regCombinationProject:remove")
    @Log(title = "体检人员综合项目信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(tjRegCombinationProjectService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 体检人员批量放弃综合项目
     * @return
     */
    @Log(title = "体检人员批量放弃综合项目", businessType = BusinessType.UPDATE)
    @PutMapping("/abandonProjects/{ids}")
    public R<Void> abandonProjects(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids){
        return toAjax(tjRegCombinationProjectService.abandonProjects(List.of(ids)));
    }

    /**
     * 体检人员批量恢复综合项目
     * @return
     */
    @Log(title = "体检人员批量恢复综合项目", businessType = BusinessType.UPDATE)
    @PutMapping("/restoreProjects/{ids}")
    public R<Void> restoreProjects(@NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ids){
        return toAjax(tjRegCombinationProjectService.restoreProjects(List.of(ids)));
    }

    /**
     * 体检人员批量延期综合项目
     * @return
     */
    @Log(title = "体检人员批量延期综合项目", businessType = BusinessType.UPDATE)
    @PutMapping("/delayProjects")
    public R<Void> delayProjects(@RequestBody TjRegCombinationProjectDelayBo delayBo){
        return toAjax(tjRegCombinationProjectService.delayProjects(delayBo));
    }

}
