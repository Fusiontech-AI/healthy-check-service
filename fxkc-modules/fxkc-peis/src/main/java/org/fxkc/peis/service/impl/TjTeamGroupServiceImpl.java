package org.fxkc.peis.service.impl;

import cn.hutool.core.util.StrUtil;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamGroupBo;
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.mapper.TjTeamGroupMapper;
import org.fxkc.peis.service.ITjTeamGroupService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 团检分组信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@RequiredArgsConstructor
@Service
public class TjTeamGroupServiceImpl implements ITjTeamGroupService {

    private final TjTeamGroupMapper baseMapper;

    /**
     * 查询团检分组信息
     */
    @Override
    public TjTeamGroupVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询团检分组信息列表
     */
    @Override
    public TableDataInfo<TjTeamGroupVo> queryPageList(TjTeamGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamGroup> lqw = buildQueryWrapper(bo);
        Page<TjTeamGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询团检分组信息列表
     */
    @Override
    public List<TjTeamGroupVo> queryList(TjTeamGroupBo bo) {
        LambdaQueryWrapper<TjTeamGroup> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamGroup> buildQueryWrapper(TjTeamGroupBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TjTeamGroup> lqw = Wrappers.lambdaQuery();
        lqw.like(StrUtil.isNotBlank(bo.getGroupName()), TjTeamGroup::getGroupName, bo.getGroupName());
        lqw.eq(bo.getDutyStatus() != null, TjTeamGroup::getDutyStatus, bo.getDutyStatus());
        lqw.eq(bo.getGroupType() != null, TjTeamGroup::getGroupType, bo.getGroupType());
        lqw.eq(StrUtil.isNotBlank(bo.getGender()), TjTeamGroup::getGender, bo.getGender());
        lqw.eq(bo.getStartAge() != null, TjTeamGroup::getStartAge, bo.getStartAge());
        lqw.eq(bo.getEndAge() != null, TjTeamGroup::getEndAge, bo.getEndAge());
        lqw.eq(StrUtil.isNotBlank(bo.getMarriage()), TjTeamGroup::getMarriage, bo.getMarriage());
        lqw.eq(bo.getPrice() != null, TjTeamGroup::getPrice, bo.getPrice());
        lqw.eq(StrUtil.isNotBlank(bo.getGroupPayType()), TjTeamGroup::getGroupPayType, bo.getGroupPayType());
        lqw.eq(StrUtil.isNotBlank(bo.getAddPayType()), TjTeamGroup::getAddPayType, bo.getAddPayType());
        lqw.eq(bo.getItemDiscount() != null, TjTeamGroup::getItemDiscount, bo.getItemDiscount());
        lqw.eq(bo.getAddDiscount() != null, TjTeamGroup::getAddDiscount, bo.getAddDiscount());
        lqw.eq(StrUtil.isNotBlank(bo.getIsSyncProject()), TjTeamGroup::getIsSyncProject, bo.getIsSyncProject());
        return lqw;
    }

    /**
     * 新增团检分组信息
     */
    @Override
    public Boolean insertByBo(TjTeamGroupBo bo) {
        TjTeamGroup add = MapstructUtils.convert(bo, TjTeamGroup.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改团检分组信息
     */
    @Override
    public Boolean updateByBo(TjTeamGroupBo bo) {
        TjTeamGroup update = MapstructUtils.convert(bo, TjTeamGroup.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamGroup entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除团检分组信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
