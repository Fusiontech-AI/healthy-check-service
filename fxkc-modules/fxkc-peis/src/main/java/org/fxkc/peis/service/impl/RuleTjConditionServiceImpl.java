package org.fxkc.peis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.peis.domain.RuleTjCondition;
import org.fxkc.peis.domain.RuleTjInfo;
import org.fxkc.peis.domain.bo.RuleTjConditionBo;
import org.fxkc.peis.domain.bo.RuleTjConditionQueryBo;
import org.fxkc.peis.mapper.RuleTjConditionMapper;
import org.fxkc.peis.mapper.RuleTjInfoMapper;
import org.fxkc.peis.service.IRuleTjConditionService;
import org.fxkc.peis.service.IRuleTjSetService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 体检项目规则条件Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@RequiredArgsConstructor
@Service
public class RuleTjConditionServiceImpl implements IRuleTjConditionService {

    private final RuleTjConditionMapper baseMapper;

    private final IRuleTjSetService ruleTjSetService;

    private final RuleTjInfoMapper ruleTjInfoMapper;

    @Override
    public List<RuleTjCondition> queryRuleTjConditionList(RuleTjConditionQueryBo dto) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleTjCondition>()
            .like(StringUtils.isNotEmpty(dto.getName()), RuleTjCondition::getName, dto.getName())
            .eq(dto.getRuleId()!=null, RuleTjCondition::getRuleId, dto.getRuleId()));
    }

    @Override
    public Long addRuleTjCondition(RuleTjConditionBo dto) {
        RuleTjCondition tjCondition = MapstructUtils.convert(dto, RuleTjCondition.class);
        baseMapper.insert(tjCondition);
        RuleTjInfo ruleTjInfo = getRuleTjInfoByRuleId(dto.getRuleId());
        ruleTjSetService.refreshRuleTjSet(ruleTjInfo.getRulesetId());
        return tjCondition.getId();
    }

    @Override
    public Boolean updateRuleTjCondition(RuleTjConditionBo dto) {
        int i = baseMapper.updateById(MapstructUtils.convert(dto, RuleTjCondition.class));
        if(i>0){
            RuleTjInfo ruleTjInfo = getRuleTjInfoByRuleId(dto.getRuleId());
            ruleTjSetService.refreshRuleTjSet(ruleTjInfo.getRulesetId());
        }
        return true ;
    }

    @Override
    public Boolean deleteRuleTjCondition(RuleTjCondition dto) {
        RuleTjCondition tjCondition = baseMapper.selectById(dto.getId());
        int i = baseMapper.deleteById(dto.getId());
        if(i>0){
            RuleTjInfo ruleTjInfo = getRuleTjInfoByRuleId(tjCondition.getRuleId());
            ruleTjSetService.refreshRuleTjSet(ruleTjInfo.getRulesetId());
        }
        return true ;
    }


    private RuleTjInfo getRuleTjInfoByRuleId(Long ruleId) {
        RuleTjInfo ruleTjInfo = ruleTjInfoMapper.selectById(ruleId);
        Assert.notNull(ruleTjInfo,"根据规则项id[],未找到相应规则配置信息！");
        return ruleTjInfo;
    }

}
