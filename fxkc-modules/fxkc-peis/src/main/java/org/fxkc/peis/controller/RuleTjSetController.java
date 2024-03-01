package org.fxkc.peis.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.domain.R;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.web.core.BaseController;
import org.fxkc.peis.domain.bo.RuleExecuteRequestBo;
import org.fxkc.peis.domain.bo.RuleTjSetBo;
import org.fxkc.peis.domain.bo.RuleTjSetQueryBo;
import org.fxkc.peis.domain.vo.RuleTjInfoExcuteResultVo;
import org.fxkc.peis.domain.vo.RuleTjSetVo;
import org.fxkc.peis.rule.constants.ConditionLogicType;
import org.fxkc.peis.rule.constants.ConditionRelationType;
import org.fxkc.peis.rule.constants.ParamType;
import org.fxkc.peis.rule.constants.RuleLogicType;
import org.fxkc.peis.service.IRuleTjSetService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 体检项目规则集
 * 前端访问路由地址为:/peis/tjSet
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/tjSet")
public class RuleTjSetController extends BaseController {

    private final IRuleTjSetService ruleTjSetService;

    /**
     * 查询条件所有逻辑运算类型
     */
    @GetMapping("/conditionLogicType/logicTypeMap")
    public R<Map<String, String>> logicTypeMap() {
        return R.ok(ConditionLogicType.getOptions());
    }

    /**
     * 查询条件所有关系运算类型
     */
    @GetMapping("/conditionLogicType/relationTypeMap")
    public R<Map<String, String>> relationTypeMap(@RequestParam("type") String type) {
        return R.ok(ConditionRelationType.getOptions(type));
    }

    /**
     * 查询规则项所有逻辑运算类型
     */
    @GetMapping("/ruleLogicType/ruleLogicTypeMap")
    public R<Map<String, String>> ruleLogicTypeMap() {
        return R.ok(RuleLogicType.getOptions());
    }

    /**
     * 查询输入比较参数列表
     */
    @GetMapping("/ruleLogicType/paramTypeMap")
    public R<Map<String, String>> paramTypeMap() {
        return R.ok(ParamType.getOptions());
    }

    /**
     *规则集分页查询
     */
    @GetMapping("/queryRuleTjSetPages")
    public TableDataInfo<RuleTjSetVo> queryRuleTjSetPages(RuleTjSetQueryBo dto, PageQuery pageQuery) {
        return ruleTjSetService.queryRuleTjSetPages(dto,pageQuery);
    }

    /**
     *规则集新增
     */
    @PostMapping("/addRuleTjSet")
    public R<Long> addRuleTjSet(@RequestBody @Validated(AddGroup.class)RuleTjSetBo dto) {
        return R.ok(ruleTjSetService.addRuleTjSet(dto));
    }


    /**
     * 规则集修改
     */
    @PostMapping("/updateRuleTjSet")
    public R<Boolean> updateRuleTjSet(@RequestBody @Validated() RuleTjSetBo dto) {
        return R.ok(ruleTjSetService.updateRuleTjSet(dto));
    }

    /**
     * 规则信息表达式刷新
     */
    @PostMapping("/refreshRuleTjSet")
    public R<Boolean> refreshRuleTjSet(@RequestBody Long rulesetId) {
        return R.ok(ruleTjSetService.refreshRuleTjSet(rulesetId));
    }

    /**
     * 执行规则表达式信息(根据基础项目id)
     */
   /* @PostMapping("/executeRuleTjSet")
    public R<List<RuleTjInfoExcuteResultVo>> executeRuleTjSet(@RequestBody RuleTjSetExecuteBo dto) {
        return R.ok(ruleTjSetService.executeRuleTjSet(dto));
    }*/


    /**
     * 根据基础项目id和项目值执行规则知识库(新版)
     */
    @PostMapping("/executeRule")
    public R<List<RuleTjInfoExcuteResultVo>> executeRule(@RequestBody @Valid RuleExecuteRequestBo ruleExecuteRequestBo) {
        return R.ok(ruleTjSetService.executeRule(ruleExecuteRequestBo));
    }

}
