package org.fxkc.peis.rule.execute.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import info.debatty.java.stringsimilarity.JaroWinkler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.peis.domain.RuleTjCondition;
import org.fxkc.peis.domain.RuleTjInfo;
import org.fxkc.peis.domain.bo.RuleExecuteBo;
import org.fxkc.peis.domain.vo.RuleTjConditionCompareVo;
import org.fxkc.peis.domain.vo.RuleTjInfoExcuteResultVo;
import org.fxkc.peis.domain.vo.TjTjxmKeywordsVo;
import org.fxkc.peis.enums.SimilarityCodeEnum;
import org.fxkc.peis.rule.execute.AbstractRuleExecute;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 诊断建议规则执行实现类
 */
@Service
@Slf4j
public class AdviceRuleImpl extends AbstractRuleExecute {

    public AdviceRuleImpl(){
        this.operateCode = "1";
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
            log.info("根据体检项目id["+ruleExecuteBo.getBasicProjectId()+"]和体检类型["+ ruleExecuteBo.getOccupationalType() +"]未找到相应的规则集记录信息,直接走关键词匹配逻辑！");
            String[] values = {ruleExecuteBo.getXmValue()};
            executeKeyWord(values,resultVOS,ruleExecuteBo);
            return resultVOS;
        }


        List<String> resultInfoList= expressionExecuteService.execute(ruleExecuteBo.getExpression(), paraMap);
        if(Objects.equals("1",ruleExecuteBo.getXmRuleType())){
            //然后根据初步筛选的关键词
            String splitSymbol = ruleExecuteBo.getSplitSymbol();
            //没有分隔符时用此符号分割可走公共代码逻辑 拆分成一个数组即可
            if(StringUtils.isEmpty(splitSymbol)){
                splitSymbol = "999999999666666";
            }
            splitSymbol = "["+splitSymbol+"]";
            String[] split = ruleExecuteBo.getXmValue().split(splitSymbol);
            //拿到执行规则表达式的结果信息
            if(CollUtil.isNotEmpty(resultInfoList)){
                log.info("体检项目id["+ruleExecuteBo.getBasicProjectId()+"]执行当前表达式的响应结果为"+ JSONArray.toJSONString(resultInfoList));
                List<RuleTjInfo> ruleTjInfos = ruleTjInfoMapper.selectBatchIds(resultInfoList);
                //根据规则项 筛出条件项中是精度的记录信息  根据条件枚举拿到对应的精度值 开始批量比对
                List<RuleTjCondition> list = ruleTjConditionMapper.selectList(new LambdaQueryWrapper<RuleTjCondition>()
                    .in(RuleTjCondition::getRuleId, ruleTjInfos.stream().map(m -> m.getId()).collect(Collectors.toList()))
                );

                //找出精度条件相关记录 因为只有精度条件记录才需要筛选比对最高精度记录
                List<RuleTjCondition> conditionList = list.stream().filter(m -> SimilarityCodeEnum.getNameList().contains(m.getRelationType())).collect(Collectors.toList());
                //存在精度符号才走原逻辑
                if(CollUtil.isNotEmpty(conditionList)){
                    HashMap<String,List<RuleTjConditionCompareVo>> hashMap = new HashMap<>();
                    for (int i = 0; i < split.length; i++) {
                        int finalI = i;
                        List<RuleTjConditionCompareVo> compareVos = conditionList.stream().filter(m -> {
                            JaroWinkler jaroWinkler = new JaroWinkler();
                            String s = split[finalI].contains(":") ? split[finalI].substring(split[finalI].indexOf(":") + 1) : split[finalI];
                            double similar = jaroWinkler.similarity(s.replace((char)12288, ' ').trim(), m.getReferenceValue());
                            return similar > SimilarityCodeEnum.getCodeByName(m.getRelationType()) ? true : false;
                        }).map(m -> {
                            RuleTjConditionCompareVo vo = new RuleTjConditionCompareVo();
                            BeanUtils.copyProperties(m, vo);
                            JaroWinkler jaroWinkler = new JaroWinkler();
                            String s = split[finalI].contains(":") ? split[finalI].substring(split[finalI].indexOf(":") + 1) : split[finalI];
                            double similar = jaroWinkler.similarity(s.replace((char)12288, ' ').trim(), m.getReferenceValue());
                            vo.setOldValue(split[finalI]);
                            vo.setSimilar(similar);
                            return vo;
                        }).collect(Collectors.toList());
                        if(CollUtil.isNotEmpty(compareVos)){
                            hashMap.put(split[finalI],compareVos);
                        }
                    }

                    //初步比对完成之后会出现两种情况 拆分后的关键字没有命中对应的规则信息  需要走关键词库逻辑  命中了选取命中率最高的记录
                    for (int i = 0; i < split.length; i++) {
                        if(hashMap.containsKey(split[i])){
                            List<RuleTjConditionCompareVo> ruleTjConditionCompareVos = hashMap.get(split[i]);
                            RuleTjConditionCompareVo ruleTjConditionCompareVo = ruleTjConditionCompareVos.stream().max(Comparator.comparingDouble(RuleTjConditionCompareVo::getSimilar)).orElse(new RuleTjConditionCompareVo());
                            RuleTjInfo ruleTjInfo = ruleTjInfoMapper.selectById(ruleTjConditionCompareVo.getRuleId());
                            RuleTjInfoExcuteResultVo resultVO = new RuleTjInfoExcuteResultVo();
                            BeanUtils.copyProperties(ruleTjInfo,resultVO);
                            resultVO.setOldValue(split[i]);
                            //构造响应vo 包含诊断名称 诊断建议信息
                            resultVO.setBasicProjectId(ruleExecuteBo.getBasicProjectId());
                            resultVO.setXmValue(ruleExecuteBo.getXmValue());
                            resultVO.setRuleType(ruleExecuteBo.getRuleType());
                            resultVOS.add(resultVO);
                            log.info("体检项目id["+ruleExecuteBo.getBasicProjectId()+"]拆分后的关键字["+split[i]+"],选取的精度最高记录是"+ JSONObject.toJSONString(ruleTjConditionCompareVo));
                        }else{
                            //这里走匹配关键词库的逻辑
                            List<TjTjxmKeywordsVo> zdJyInfoByKeywords = tjTjxmKeywordsService.getZdJyInfoByKeywords(split[i]);
                            if(CollUtil.isNotEmpty(zdJyInfoByKeywords)){
                                zdJyInfoByKeywords.stream().forEach(m->{
                                    RuleTjInfoExcuteResultVo resultVO = new RuleTjInfoExcuteResultVo();
                                    resultVO.setBasicProjectId(ruleExecuteBo.getBasicProjectId());
                                    resultVO.setXmValue(ruleExecuteBo.getXmValue());
                                    resultVO.setRuleType(ruleExecuteBo.getRuleType());
                                    resultVOS.add(resultVO);
                                });

                            }

                        }

                    }
                }else{
                    log.info("体检项目id["+ruleExecuteBo.getBasicProjectId()+"]功能检查类不存在精度匹配符号！");
                    //不存在精度字符的 命中的直接组装响应结果
                    ruleTjInfos.stream().forEach(ruleTjInfo->{
                        RuleTjInfoExcuteResultVo respVo = new RuleTjInfoExcuteResultVo();
                        BeanUtils.copyProperties(ruleTjInfo,respVo);
                        respVo.setBasicProjectId(ruleExecuteBo.getBasicProjectId());
                        respVo.setRuleType(ruleExecuteBo.getRuleType());
                        resultVOS.add(respVo);
                    });

                }

            }else{
                executeKeyWord(split,resultVOS,ruleExecuteBo);
            }
            log.info("体检项目id["+ruleExecuteBo.getBasicProjectId()+"]功能检查类项目根据原始输入值["+ruleExecuteBo.getXmValue()+"],最终响应的建议信息为"+ JSONArray.toJSONString(resultVOS));
            return resultVOS;
        }else{
            log.info("非功能性体检项目id["+ruleExecuteBo.getBasicProjectId()+"]执行当前表达式的响应结果为"+JSONArray.toJSONString(resultInfoList));
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
            log.info("非功能检查类项目根据原始输入值["+ruleExecuteBo.getXmValue()+"],最终响应的建议信息为"+ JSONArray.toJSONString(resultVOS));
            return resultVOS;
        }
    }



    public void executeKeyWord(String[] split, List<RuleTjInfoExcuteResultVo> resultVOS,RuleExecuteBo ruleExecuteBo){
        //执行的结果直接都是null  这里全部走关键词库匹配逻辑
        for (int i = 0; i < split.length; i++) {
            //这里走匹配关键词库的逻辑
            List<TjTjxmKeywordsVo> zdJyInfoByKeywords = tjTjxmKeywordsService.getZdJyInfoByKeywords(split[i]);
            if(CollUtil.isNotEmpty(zdJyInfoByKeywords)){
                zdJyInfoByKeywords.stream().forEach(m->{
                    RuleTjInfoExcuteResultVo resultVO = new RuleTjInfoExcuteResultVo();
                    resultVO.setBasicProjectId(ruleExecuteBo.getBasicProjectId());
                    resultVO.setXmValue(ruleExecuteBo.getXmValue());
                    resultVO.setRuleType(ruleExecuteBo.getRuleType());
                    resultVOS.add(resultVO);
                });

            }

        }
    }
}
