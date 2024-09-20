package org.fxkc.peis.controller;

import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.RuleTjCondition;
import org.fxkc.peis.domain.bo.RuleTjConditionBo;
import org.fxkc.peis.domain.bo.RuleTjConditionQueryBo;
import org.fxkc.peis.service.IRuleTjConditionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 体检项目规则条件
 * 前端访问路由地址为:/peis/tjCondition
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tjCondition")
public class RuleTjConditionController extends BaseController {

    private final IRuleTjConditionService ruleTjConditionService;

    /**
     * 规则项条件查询
     */
    @PostMapping("/queryRuleTjConditionList")
    public R<List<RuleTjCondition>> queryRuleTjConditionList(@RequestBody RuleTjConditionQueryBo dto) {
        return R.ok(ruleTjConditionService.queryRuleTjConditionList(dto));
    }

    /**
     * 规则项条件新增
     */
    @PostMapping("/addRuleTjCondition")
    public R<Long> addRuleTjCondition(@RequestBody @Validated(AddGroup.class) RuleTjConditionBo dto) {
        return R.ok(ruleTjConditionService.addRuleTjCondition(dto));
    }


    /**
     * 规则项条件修改
     */
    @PostMapping("/updateRuleTjCondition")
    public R<Boolean> updateRuleTjCondition(@RequestBody @Validated(EditGroup.class) RuleTjConditionBo dto) {
        return R.ok(ruleTjConditionService.updateRuleTjCondition(dto));
    }

    /**
     *规则项条件删除
     */
    @PostMapping("/deleteRuleTjCondition")
    public R<Boolean> deleteRuleTjCondition(@RequestBody @Validated(EditGroup.class) RuleTjCondition dto) {
        return R.ok(ruleTjConditionService.deleteRuleTjCondition(dto));
    }
}
