package org.fxkc.peis.rule.execute;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.peis.domain.RuleTjInfo;
import org.fxkc.peis.domain.bo.RuleExecuteBo;
import org.fxkc.peis.domain.vo.RuleTjInfoExcuteResultVo;
import org.fxkc.peis.mapper.*;
import org.fxkc.peis.rule.aviator.ExpressionExecuteService;
import org.fxkc.peis.service.ITjTjxmKeywordsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractRuleExecute implements RuleExecuteService {

    protected String operateCode;

    @Autowired
    protected RuleExecuteHolder ruleExecuteHolder;

    @Autowired
    protected ExpressionExecuteService expressionExecuteService;

    @Autowired
    protected  RuleTjSetMapper ruleTjSetMapper;

    @Autowired
    protected  RuleTjInfoMapper ruleTjInfoMapper;

    @Autowired
    protected  RuleTjConditionMapper ruleTjConditionMapper;

    @Autowired
    protected  TjZdjywhMapper zdjywhMapper;

    @Autowired
    protected  TjTjxmKeywordsMapper tjTjxmKeywordsMapper;

    @Autowired
    protected  TjBasicProjectMapper basicProjectMapper;

    @Autowired
    protected  ITjTjxmKeywordsService tjTjxmKeywordsService;

    @PostConstruct
    public void init() {
        ruleExecuteHolder.putBuilder(operateCode, this);
    }


    @Override
    public List<RuleTjInfoExcuteResultVo> executeRule(RuleExecuteBo ruleExecuteBo) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("xmValue",ruleExecuteBo.getXmValue());
        paraMap.put("sex",ruleExecuteBo.getSex());
        paraMap.put("age",ruleExecuteBo.getAge());
        paraMap.put("isPositive",ruleExecuteBo.getIsPositive());
        paraMap.put("isAbnormal",ruleExecuteBo.getIsAbnormal());
        paraMap.put("splitSymbol",ruleExecuteBo.getSplitSymbol());

        List<RuleTjInfoExcuteResultVo> resultVOS = new ArrayList<>();
        if(ruleExecuteBo.getId()==null || StringUtils.isEmpty(ruleExecuteBo.getExpression())){
            log.info("根据体检项目id["+ruleExecuteBo.getBasicProjectId()+"]和体检类型["+ ruleExecuteBo.getOccupationalType() +"]未找到相应的规则集记录信息,直接返回！");
            return resultVOS;
        }

        List<String> resultInfoList= expressionExecuteService.execute(ruleExecuteBo.getExpression(), paraMap);

        log.info("体检项目id["+ruleExecuteBo.getBasicProjectId()+"]规则类型["+ ruleExecuteBo.getRuleType() +"]执行当前表达式的响应结果为"+ JSONArray.toJSONString(resultInfoList));
        if(CollUtil.isEmpty(resultInfoList)){
            return resultVOS;
        }
        RuleTjInfo ruleTjInfo = ruleTjInfoMapper.selectById(resultInfoList.get(0));
        RuleTjInfoExcuteResultVo respVo = new RuleTjInfoExcuteResultVo();
        BeanUtils.copyProperties(ruleTjInfo,respVo);
        respVo.setBasicProjectId(ruleExecuteBo.getBasicProjectId());
        respVo.setXmValue(ruleExecuteBo.getXmValue());
        respVo.setRuleType(ruleExecuteBo.getRuleType());
        resultVOS.add(respVo);
        log.info("根据原始输入值["+ruleExecuteBo.getXmValue()+"],规则类型["+ ruleExecuteBo.getRuleType() +"]最终响应的建议信息为"+ JSONArray.toJSONString(resultVOS));
        return resultVOS;
    }


}
