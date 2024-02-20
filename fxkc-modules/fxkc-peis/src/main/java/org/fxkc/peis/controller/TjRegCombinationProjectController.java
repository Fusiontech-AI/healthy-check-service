package org.fxkc.peis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.log.annotation.Log;
import org.fxkc.common.log.enums.BusinessType;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.TjRegCombinationProjectDelayBo;
import org.fxkc.peis.domain.vo.TjRegBasicProjectVo;
import org.fxkc.peis.domain.vo.TjRegCombinationProjectVo;
import org.fxkc.peis.service.ITjRegCombinationProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检登记组合项目信息
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
     * 根据登记id查询组合项目列表
     */
    @GetMapping("queryRegCombinProjectList")
    public R<List<TjRegCombinationProjectVo>> queryRegCombinProjectList(@RequestParam(value = "id") @NotNull(message = "登记id不能为空") Long id) {
        return R.ok(tjRegCombinationProjectService.queryRegCombinProjectList(id));
    }


    /**
     * 根据登记组合项目主键id查询基础项目列表
     */
    @GetMapping("queryRegBasicProjectList")
    public R<List<TjRegBasicProjectVo>> queryRegBasicProjectList(@RequestParam(value = "id") @NotNull(message = "登记组合项目id不能为空") Long id) {
        return R.ok(tjRegCombinationProjectService.queryRegBasicProjectList(id));
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
