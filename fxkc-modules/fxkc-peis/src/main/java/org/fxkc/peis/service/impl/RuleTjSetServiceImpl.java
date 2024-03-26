package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.RuleTjInfo;
import org.fxkc.peis.domain.RuleTjSet;
import org.fxkc.peis.domain.TjBasicProject;
import org.fxkc.peis.domain.TjZdjywh;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.RuleTjInfoExcuteResultVo;
import org.fxkc.peis.domain.vo.RuleTjSetVo;
import org.fxkc.peis.domain.vo.TjTjxmKeywordsVo;
import org.fxkc.peis.mapper.*;
import org.fxkc.peis.rule.aviator.ExpressionExecuteService;
import org.fxkc.peis.rule.execute.RuleExecuteHolder;
import org.fxkc.peis.rule.expression.ExpressionBuildService;
import org.fxkc.peis.service.IRuleTjSetService;
import org.fxkc.peis.service.ITjTjxmKeywordsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 体检项目规则集Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class RuleTjSetServiceImpl implements IRuleTjSetService {

    private final RuleTjSetMapper baseMapper;

    private final RuleTjInfoMapper ruleTjInfoMapper;

    private final RuleTjConditionMapper ruleTjConditionMapper;

    private final TjZdjywhMapper zdjywhMapper;

    private final TjTjxmKeywordsMapper tjTjxmKeywordsMapper;

    private final TjBasicProjectMapper basicProjectMapper;

    private final ITjTjxmKeywordsService tjTjxmKeywordsService;

    private final ExpressionBuildService expressionBuildService;

    private final ExpressionExecuteService expressionExecuteService;

    private final RuleExecuteHolder ruleExecuteHolder;

    @Override
    public TableDataInfo<RuleTjSetVo> queryRuleTjSetPages(RuleTjSetQueryBo dto, PageQuery pageQuery) {
        LambdaQueryWrapper<RuleTjSet> lqw = new LambdaQueryWrapper<RuleTjSet>();
        lqw.eq(dto.getBasicProjectId()!=null, RuleTjSet::getBasicProjectId, dto.getBasicProjectId());
        lqw.eq(StringUtils.isNotEmpty(dto.getOccupationalType()), RuleTjSet::getOccupationalType, dto.getOccupationalType());
        lqw.eq(StringUtils.isNotEmpty(dto.getRuleType()), RuleTjSet::getRuleType, dto.getRuleType());
        Page<RuleTjSetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public Long addRuleTjSet(RuleTjSetBo dto) {
        RuleTjSet ruleTjSet = getRuleTjSet(dto.getBasicProjectId(), dto.getOccupationalType(),dto.getRuleType());
        Assert.isNull(ruleTjSet,"根据基础项目id["+dto.getBasicProjectId()+"]已存在相应规则集信息，不能重复添加！");
        RuleTjSet tjSet = MapstructUtils.convert(dto, RuleTjSet.class);
        baseMapper.insert(tjSet);
        return tjSet.getId();
    }

    @Override
    public Boolean updateRuleTjSet(RuleTjSetBo dto) {
        RuleTjSet oldRuleTjSet = baseMapper.selectById(dto.getId());
        Assert.notNull(oldRuleTjSet,"根据规则集id["+dto.getId()+"]未找到相应的记录信息！");
        RuleTjSet ruleTjSet = getRuleTjSet(dto.getBasicProjectId(), dto.getOccupationalType(),dto.getRuleType());
        if(ruleTjSet!=null && !Objects.equals(ruleTjSet.getId(),oldRuleTjSet.getId())){
            throw new RuntimeException("根据检查项目id["+dto.getBasicProjectId()+"]已存在相应规则集信息，不能修改！");
        }
        RuleTjSet tjSet = MapstructUtils.convert(dto, RuleTjSet.class);
        refreshRuleTjSet(dto.getId());
        return baseMapper.updateById(tjSet)>0;
    }

    @Override
    public Boolean refreshRuleTjSet(Long rulesetId) {
        try {
            List<RuleTjSet> list = baseMapper.selectList(new LambdaQueryWrapper<RuleTjSet>()
                .isNull(RuleTjSet::getExpression)
            );
            list.stream().forEach(m->{
                try {
                    refreshRulesetInfo(m.getId());
                }catch (Exception e){

                }
            });
            refreshRulesetInfo(rulesetId);
        }catch (Exception e){
            log.error("根据规则集id"+ rulesetId +"刷新表达式时出现异常：",e);
        }
        return true;
    }

  /*  @Override
    public List<RuleTjInfoExcuteResultVo> executeRuleTjSet(RuleTjSetExecuteBo dto) {

        RuleTjSetExecuteParamBo paramDto = dto.getParamDto();
        if(StringUtils.isEmpty(paramDto.getXmValue())){
            return null;
        }
        Map<String, Object> paraMap = JSONObject.parseObject(JSON.toJSONString(paramDto), Map.class);
        log.info("paraMap"+paraMap);
        if (CollectionUtils.isEmpty(paraMap)) {
            throw new RuleRunTimeException("invalid parameter(paraMap)");
        }

        List<RuleTjSet> ruleTjSets = baseMapper.selectList(new LambdaQueryWrapper<RuleTjSet>()
                .eq(RuleTjSet::getBasicProjectId, dto.getBasicProjectId())
        );

        TjBasicProject tjTjxm = basicProjectMapper.selectById(dto.getBasicProjectId());
        dto.setBasicProjectName(tjTjxm.getBasicProjectName());
        List<RuleTjInfoExcuteResultVo> resultVOS = new ArrayList<>();

        //获取不同类型的规则集对象  诊断建议对象
        RuleTjSet rulesetInfoEntity = ruleTjSets.stream().filter(m->Objects.equals(m.getRuleType(),"1")).findFirst().orElse(null);

        //拿到非诊断建议的规则记录信息
        List<RuleTjSet> tjSets = ruleTjSets.stream().filter(m -> !Objects.equals(m.getRuleType(), "1")).collect(Collectors.toList());
        executeWjzRuleTjSet(tjSets,dto,paraMap,resultVOS);

        if(rulesetInfoEntity==null){
            log.info("登记id["+dto.getRegisterId()+"]根据体检项目id["+dto.getBasicProjectId()+"]和体检类型["+ dto.getOccupationalType() +"]未找到相应的记录信息！");
            String[] values = {paramDto.getXmValue()};
            excuteKeyWord(values,resultVOS,dto);
            return resultVOS;
        }
        String expression = rulesetInfoEntity.getExpression();
        if (StringUtils.isBlank(expression)) {
            log.info("登记id["+dto.getRegisterId()+"]根据体检项目id["+dto.getBasicProjectId()+"]和体检类型["+ dto.getOccupationalType() +"]规则表达式为空！");
            return null;
        }

        if(Objects.equals("1",rulesetInfoEntity.getXmRuleType())){
            //然后根据初步筛选的关键词
            String splitSymbol = rulesetInfoEntity.getSplitSymbol();
            //没有分隔符时用此符号分割可走公共代码逻辑 拆分成一个数组即可
            if(StringUtils.isEmpty(splitSymbol)){
                splitSymbol = "999999999666666";
            }
            splitSymbol = "["+splitSymbol+"]";
            String[] split = paramDto.getXmValue().split(splitSymbol);
            //拿到执行规则表达式的结果信息
            List<String> resultInfoList= expressionExecuteService.execute(expression, paraMap);
            if(CollUtil.isNotEmpty(resultInfoList)){
                log.info("登记id["+dto.getRegisterId()+"]体检项目id["+dto.getBasicProjectId()+"]执行当前表达式的响应结果为"+ JSONArray.toJSONString(resultInfoList));
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
                            fillRuleTjInfoExcuteResultVO(resultVO,ruleTjInfo.getZdjyId());
                            resultVO.setCombinProjectId(dto.getCombinProjectId());
                            resultVO.setBasicProjectId(dto.getBasicProjectId());
                            resultVO.setRegisterId(dto.getRegisterId());
                            resultVO.setXmValue(paramDto.getXmValue());
                            resultVO.setBasicProjectName(dto.getBasicProjectName());
                            resultVO.setRuleType("1");
                            resultVOS.add(resultVO);
                            log.info("登记id["+dto.getRegisterId()+"]体检项目id["+dto.getBasicProjectId()+"]拆分后的关键字["+split[i]+"],选取的精度最高记录是"+JSONObject.toJSONString(ruleTjConditionCompareVo));
                        }else{
                            //这里走匹配关键词库的逻辑
                            List<TjTjxmKeywordsVo> zdJyInfoByKeywords = tjTjxmKeywordsService.getZdJyInfoByKeywords(split[i]);
                            if(CollUtil.isNotEmpty(zdJyInfoByKeywords)){
                                zdJyInfoByKeywords.stream().forEach(m->{
                                    RuleTjInfoExcuteResultVo resultVO = new RuleTjInfoExcuteResultVo();
                                    fillRuleTjInfoExcuteResultVO(resultVO,m.getZdjyId());
                                    resultVO.setCombinProjectId(dto.getCombinProjectId());
                                    resultVO.setBasicProjectId(dto.getBasicProjectId());
                                    resultVO.setRegisterId(dto.getRegisterId());
                                    resultVO.setXmValue(paramDto.getXmValue());
                                    resultVO.setBasicProjectName(dto.getBasicProjectName());
                                    resultVO.setRuleType("1");
                                    resultVOS.add(resultVO);
                                });

                            }

                        }

                    }
                }else{
                    log.info("登记id["+dto.getRegisterId()+"]体检项目id["+dto.getBasicProjectId()+"]功能检查类不存在精度匹配符号！");
                    //不存在精度字符的 命中的直接组装响应结果
                    ruleTjInfos.stream().forEach(ruleTjInfo->{
                        RuleTjInfoExcuteResultVo respVo = new RuleTjInfoExcuteResultVo();
                        BeanUtils.copyProperties(ruleTjInfo,respVo);
                        respVo.setCombinProjectId(dto.getCombinProjectId());
                        respVo.setBasicProjectId(dto.getBasicProjectId());
                        respVo.setRuleType("1");
                        //构造响应vo 包含诊断名称 诊断建议信息
                        fillRuleTjInfoExcuteResultVO(respVo,ruleTjInfo.getZdjyId());
                        resultVOS.add(respVo);
                    });

                }

            }else{
                excuteKeyWord(split,resultVOS,dto);
            }
            log.info("登记id["+dto.getRegisterId()+"]体检项目id["+dto.getBasicProjectId()+"]功能检查类项目根据原始输入值["+paramDto.getXmValue()+"],最终响应的建议信息为"+ JSONArray.toJSONString(resultVOS));
            return resultVOS;
        }else{
            List<String> executeResult = expressionExecuteService.execute(expression, paraMap);
            log.info("非功能性登记id["+dto.getRegisterId()+"]体检项目id["+dto.getBasicProjectId()+"]执行当前表达式的响应结果为"+JSONArray.toJSONString(executeResult));
            if(CollUtil.isEmpty(executeResult)){
                return resultVOS;
            }
            RuleTjInfo ruleTjInfo = ruleTjInfoMapper.selectById(executeResult.get(0));
            RuleTjInfoExcuteResultVo respVo = new RuleTjInfoExcuteResultVo();
            BeanUtils.copyProperties(ruleTjInfo,respVo);
            respVo.setCombinProjectId(dto.getCombinProjectId());
            respVo.setBasicProjectId(dto.getBasicProjectId());
            respVo.setRegisterId(dto.getRegisterId());
            respVo.setXmValue(paramDto.getXmValue());
            respVo.setBasicProjectName(dto.getBasicProjectName());
            respVo.setRuleType("1");
            //构造响应vo 包含诊断名称 诊断建议信息
            fillRuleTjInfoExcuteResultVO(respVo,ruleTjInfo.getZdjyId());
            resultVOS.add(respVo);
            log.info("登记id["+dto.getRegisterId()+"]非功能检查类项目根据原始输入值["+paramDto.getXmValue()+"],最终响应的建议信息为"+ JSONArray.toJSONString(resultVOS));
            return resultVOS;
        }
    }*/

    @Override
    public List<RuleTjInfoExcuteResultVo> executeRule(RuleExecuteRequestBo ruleExecuteRequestBo) {
        //先根据体检项目id查询规则集记录
        List<RuleTjSet> tjSets = this.baseMapper.selectList(new LambdaQueryWrapper<RuleTjSet>()
            .eq(RuleTjSet::getBasicProjectId, ruleExecuteRequestBo.getBasicProjectId())
        );
        TjBasicProject tjBasicProject = basicProjectMapper.selectById(ruleExecuteRequestBo.getBasicProjectId());
        List<RuleExecuteBo> ruleExecuteBos = MapstructUtils.convert(tjSets, RuleExecuteBo.class);
        ruleExecuteBos.stream().forEach(ruleExecuteBo->{
            //组装执行参数对象
            ruleExecuteBo.setSex(ruleExecuteRequestBo.getSex());
            ruleExecuteBo.setIsPositive(ruleExecuteRequestBo.getIsPositive());
            ruleExecuteBo.setIsAbnormal(ruleExecuteRequestBo.getIsAbnormal());
            ruleExecuteBo.setXmValue(ruleExecuteRequestBo.getXmValue());
            ruleExecuteBo.setAge(ruleExecuteRequestBo.getAge());

        });
        //如果不存在诊断建议的规则集记录  则人为组装一条请求对象 后面可以走关键词逻辑
        Optional<RuleExecuteBo> any = ruleExecuteBos.stream().filter(m -> Objects.equals(m.getRuleType(), "1")).findAny();
        if(!any.isPresent()){
            //组装诊断建议执行参数对象
            RuleExecuteBo ruleExecuteBo = new RuleExecuteBo();
            ruleExecuteBo.setRuleType("1");
            ruleExecuteBo.setBasicProjectId(ruleExecuteRequestBo.getBasicProjectId());
            ruleExecuteBo.setSex(ruleExecuteRequestBo.getSex());
            ruleExecuteBo.setIsPositive(ruleExecuteRequestBo.getIsPositive());
            ruleExecuteBo.setIsAbnormal(ruleExecuteRequestBo.getIsAbnormal());
            ruleExecuteBo.setXmValue(ruleExecuteRequestBo.getXmValue());
            ruleExecuteBo.setAge(ruleExecuteRequestBo.getAge());
            ruleExecuteBos.add(ruleExecuteBo);
        }

        List<RuleTjInfoExcuteResultVo> resultVos = new ArrayList<>();
        ruleExecuteBos.stream().forEach(ruleExecuteBo->{
            List<RuleTjInfoExcuteResultVo> executeResultVos = ruleExecuteHolder.selectBuilder(ruleExecuteBo.getRuleType()).executeRule(ruleExecuteBo);
            resultVos.addAll(executeResultVos);
        });

        //最后批量填充诊断建议相关 和 体检项目名称等字段信息
        List<Long> zdjyIds = resultVos.stream().filter(resultVo -> resultVo.getZdjyId() != null).map(m -> m.getZdjyId()).collect(Collectors.toList());
        List<TjZdjywh> tjZdjywhs = new ArrayList<>();
        if(CollUtil.isNotEmpty(zdjyIds)){
            tjZdjywhs = zdjywhMapper.selectBatchIds(zdjyIds);
        }
        List<TjZdjywh> finalTjZdjywhs = tjZdjywhs;
        resultVos.stream().forEach(resultVo->{
            if(resultVo.getZdjyId()!=null){
                Optional<TjZdjywh> first = finalTjZdjywhs.stream().filter(m -> Objects.equals(m.getId(), resultVo.getZdjyId())).findFirst();
                if(first.isPresent()){
                    TjZdjywh tjZdjywh = first.get();
                    resultVo.setKpsm(tjZdjywh.getKpsm());
                    resultVo.setZyzd(resultVo.getZyzd());
                    resultVo.setIcdType(resultVo.getIcdType());
                    resultVo.setZybjy(resultVo.getZybjy());
                    resultVo.setZdjyId(resultVo.getZdjyId());
                }
            }
            resultVo.setBasicProjectName(tjBasicProject.getBasicProjectName());
        });
        return resultVos;
    }


    /**
     * 批量删除体检项目规则集
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    public RuleTjSet getRuleTjSet(Long basicProjectId, String occupationalType,String ruleType){
        List<RuleTjSet> ruleTjSets = baseMapper.selectList(new LambdaQueryWrapper<RuleTjSet>()
            .eq(RuleTjSet::getBasicProjectId, basicProjectId)
            .eq(RuleTjSet::getRuleType, ruleType)
        );
        if(CollUtil.isNotEmpty(ruleTjSets)){
            return ruleTjSets.get(0);
        }
        return null;
    }


    public void refreshRulesetInfo(Long rulesetId) {
        RuleTjSet rulesetInfoEntity = baseMapper.selectById(rulesetId);
        Assert.notNull(rulesetInfoEntity,"规则集id["+rulesetId+"],未找到相应记录信息！");
        String rulesetExpression = expressionBuildService.buildRulesetExpression(rulesetId);
        if (StringUtils.isNotEmpty(rulesetExpression)) {
            rulesetInfoEntity.setExpression(rulesetExpression);
            baseMapper.updateById(rulesetInfoEntity);
            log.info("ruleset({}) refresh completed", rulesetId);
        }
    }

    /**
     * 根据检查项目id和参数信息执行危急值和阳性相关规则
     * @param dto
     * @return
     */
    public void executeWjzRuleTjSet(List<RuleTjSet> tjSets, RuleTjSetExecuteBo dto, Map<String, Object> paraMap, List<RuleTjInfoExcuteResultVo> resultVOS){
        if(CollUtil.isEmpty(tjSets)){
            return ;
        }

        for (int i = 0; i < tjSets.size(); i++) {
            RuleTjSet ruleTjSet = tjSets.get(i);
            String expression = ruleTjSet.getExpression();
            if (StringUtils.isBlank(expression)) {
                log.info("登记id["+dto.getRegisterId()+"]根据体检项目id["+dto.getBasicProjectId()+"]和规则类型["+ ruleTjSet.getRuleType() +"]规则表达式为空！");
                continue;
            }

            List<String> executeResult = expressionExecuteService.execute(expression, paraMap);
            log.info("非功能性登记id["+dto.getRegisterId()+"]体检项目id["+dto.getBasicProjectId()+"]规则类型["+ ruleTjSet.getRuleType() +"]执行当前表达式的响应结果为"+JSONArray.toJSONString(executeResult));
            if(CollUtil.isEmpty(executeResult)){
                continue;
            }
            RuleTjInfo ruleTjInfo = ruleTjInfoMapper.selectById(executeResult.get(0));
            RuleTjInfoExcuteResultVo respVo = new RuleTjInfoExcuteResultVo();
            BeanUtils.copyProperties(ruleTjInfo,respVo);
            respVo.setCombinProjectId(dto.getCombinProjectId());
            respVo.setBasicProjectId(dto.getBasicProjectId());
            respVo.setRegisterId(dto.getRegisterId());
            respVo.setXmValue(dto.getParamDto().getXmValue());
            respVo.setBasicProjectName(dto.getBasicProjectName());
            respVo.setRuleType(ruleTjSet.getRuleType());
            //构造响应vo 包含诊断名称 诊断建议信息
            fillRuleTjInfoExcuteResultVO(respVo,ruleTjInfo.getZdjyId());
            resultVOS.add(respVo);
            log.info("登记id["+dto.getRegisterId()+"]非功能检查类项目根据原始输入值["+dto.getParamDto().getXmValue()+"],规则类型["+ ruleTjSet.getRuleType() +"]最终响应的建议信息为"+ JSONArray.toJSONString(resultVOS));
        }


    }


    public void excuteKeyWord(String[] split, List<RuleTjInfoExcuteResultVo> resultVOS, RuleTjSetExecuteBo dto){
        //执行的结果直接都是null  这里全部走关键词库匹配逻辑
        for (int i = 0; i < split.length; i++) {
            //这里走匹配关键词库的逻辑
            List<TjTjxmKeywordsVo> zdJyInfoByKeywords = tjTjxmKeywordsService.getZdJyInfoByKeywords(split[i]);
            if(CollUtil.isNotEmpty(zdJyInfoByKeywords)){
                zdJyInfoByKeywords.stream().forEach(m->{
                    RuleTjInfoExcuteResultVo resultVO = new RuleTjInfoExcuteResultVo();
                    resultVO.setCombinProjectId(dto.getCombinProjectId());
                    resultVO.setBasicProjectId(dto.getBasicProjectId());
                    resultVO.setRegisterId(dto.getRegisterId());
                    resultVO.setXmValue(dto.getParamDto().getXmValue());
                    resultVO.setBasicProjectName(dto.getBasicProjectName());
                    resultVO.setRuleType("1");
                    fillRuleTjInfoExcuteResultVO(resultVO,m.getZdjyId());
                    resultVOS.add(resultVO);
                });

            }

        }
    }

    private void fillRuleTjInfoExcuteResultVO(RuleTjInfoExcuteResultVo resultVO, Long zdjyId) {
        if(zdjyId!=null){
            TjZdjywh tjZdjywh = zdjywhMapper.selectById(zdjyId);
            if(tjZdjywh!=null){
                resultVO.setKpsm(tjZdjywh.getKpsm());
                resultVO.setZybjy(tjZdjywh.getZybjy());
                resultVO.setZyzd(tjZdjywh.getZyzd());
                resultVO.setZdjyId(zdjyId);
                resultVO.setIcdType(tjZdjywh.getIcdCode());
            }

        }
    }
}
