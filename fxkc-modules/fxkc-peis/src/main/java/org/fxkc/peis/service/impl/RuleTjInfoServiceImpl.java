package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.peis.domain.RuleTjCondition;
import org.fxkc.peis.domain.RuleTjInfo;
import org.fxkc.peis.domain.bo.RuleTjInfoBo;
import org.fxkc.peis.domain.bo.RuleTjInfoQueryBo;
import org.fxkc.peis.domain.vo.RuleTjConditionVo;
import org.fxkc.peis.domain.vo.RuleTjInfoVo;
import org.fxkc.peis.mapper.RuleTjConditionMapper;
import org.fxkc.peis.mapper.RuleTjInfoMapper;
import org.fxkc.peis.service.IRuleTjInfoService;
import org.fxkc.peis.service.IRuleTjSetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 体检项目规则项Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@RequiredArgsConstructor
@Service
public class RuleTjInfoServiceImpl implements IRuleTjInfoService {

    private final RuleTjInfoMapper baseMapper;

    private final IRuleTjSetService ruleTjSetService;

    private final RuleTjConditionMapper ruleTjConditionMapper;

    @Override
    public TableDataInfo<RuleTjInfoVo> queryRuleTjInfoList(RuleTjInfoQueryBo dto, PageQuery pageQuery) {
        LambdaQueryWrapper<RuleTjInfo> lqw = new LambdaQueryWrapper<RuleTjInfo>();
        lqw.eq(dto.getRulesetId()!=null, RuleTjInfo::getRulesetId, dto.getRulesetId());
        lqw.like(StringUtils.isNotEmpty(dto.getName()), RuleTjInfo::getName, dto.getName());
        lqw.orderByDesc(RuleTjInfo::getPriority);
        Page<RuleTjInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<RuleTjInfoVo> ruleTjInfoVos = result.getRecords();
        if(CollUtil.isNotEmpty(ruleTjInfoVos)){
            List<RuleTjConditionVo> ruleTjConditionVos = ruleTjConditionMapper.selectVoList(new LambdaQueryWrapper<RuleTjCondition>()
                .in(RuleTjCondition::getRuleId, ruleTjInfoVos.stream().map(m -> m.getId()).collect(Collectors.toList()))
                .orderByDesc(RuleTjCondition::getPriority)
            );
            ruleTjInfoVos.stream().forEach(ruleTjInfoVo->{
                List<RuleTjConditionVo> tjConditionVos = ruleTjConditionVos.stream().filter(m -> Objects.equals(m.getRuleId(), ruleTjInfoVo.getId())).collect(Collectors.toList());
                ruleTjInfoVo.setRuleTjConditionVos(tjConditionVos);
            });
        }
        return TableDataInfo.build(result);
    }

    @Override
    public Long addRuleTjInfo(RuleTjInfoBo dto) {
        RuleTjInfo ruleTjInfo = this.selectRuleTjInfoByName(dto.getName(),dto.getRulesetId());
        if(ruleTjInfo!=null){
            throw new RuntimeException("规则描述名称["+ruleTjInfo.getName()+"]已存在,请检查后添加!");
        }
        RuleTjInfo tjInfo = MapstructUtils.convert(dto, RuleTjInfo.class);
        baseMapper.insert(tjInfo);
        return tjInfo.getId();
    }

    @Override
    public Boolean updateRuleTjInfo(RuleTjInfoBo dto) {
        RuleTjInfo ruleTjInfo = this.selectRuleTjInfoByName(dto.getName(),dto.getRulesetId());
        if(ruleTjInfo!=null && !Objects.equals(ruleTjInfo.getId(),dto.getId())){
            throw new RuntimeException("规则描述名称["+dto.getName()+"]已存在,请检查后修改!");
        }
        RuleTjInfo tjInfo = MapstructUtils.convert(dto, RuleTjInfo.class);
        return baseMapper.updateById(tjInfo)>0?true:false;
    }

    @Override
    public Boolean deleteRuleTjInfo(RuleTjInfoBo dto) {
        int i = baseMapper.deleteById(dto.getId());
        if(i>0){
            RuleTjInfo tjInfo = baseMapper.selectById(dto.getId());
            ruleTjSetService.refreshRuleTjSet(tjInfo.getRulesetId());
        }
        return true ;
    }


    public RuleTjInfo selectRuleTjInfoByName(String name, Long rulesetId){
        List<RuleTjInfo> tjInfos = baseMapper.selectList(new LambdaQueryWrapper<RuleTjInfo>()
            .eq(RuleTjInfo::getRulesetId, rulesetId)
            .eq(RuleTjInfo::getName, name)
        );
        if(CollUtil.isNotEmpty(tjInfos)){
            return tjInfos.get(0);
        }
        return null;

    }
}
