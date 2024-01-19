package org.fxkc.peis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.bo.TjTeamGroupBo;
import org.fxkc.peis.domain.bo.TjTeamTaskQueryBo;
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.domain.vo.TjTeamTaskDetailVo;
import org.fxkc.peis.enums.GroupTypeEnum;
import org.fxkc.peis.enums.PhysicalTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.TjTeamGroupMapper;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamTaskBo;
import org.fxkc.peis.domain.vo.TjTeamTaskVo;
import org.fxkc.peis.domain.TjTeamTask;
import org.fxkc.peis.mapper.TjTeamTaskMapper;
import org.fxkc.peis.service.ITjTeamTaskService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 团检任务管理Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@RequiredArgsConstructor
@Service
public class TjTeamTaskServiceImpl extends ServiceImpl<TjTeamTaskMapper, TjTeamTask> implements ITjTeamTaskService {

    private final TjTeamGroupMapper tjTeamGroupMapper;

    private final ITjTeamInfoService iTjTeamInfoService;

    /**
     * 查询团检任务管理
     */
    @Override
    public TjTeamTaskDetailVo queryById(Long id){
        TjTeamTask teamTask = baseMapper.selectById(id);
        TjTeamTaskDetailVo vo = BeanUtil.toBean(teamTask, TjTeamTaskDetailVo.class);
        List<TjTeamGroupVo> groupVoList = tjTeamGroupMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, id));
        vo.setGroupList(groupVoList);
        return vo;
    }

    /**
     * 查询团检任务管理列表
     */
    @Override
    public TableDataInfo<TjTeamTaskVo> queryPageList(TjTeamTaskQueryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TjTeamTask> lqw = buildQueryWrapper(bo);
        Page<TjTeamTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询团检任务管理列表
     */
    @Override
    public List<TjTeamTaskVo> queryList(TjTeamTaskQueryBo bo) {
        LambdaQueryWrapper<TjTeamTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TjTeamTask> buildQueryWrapper(TjTeamTaskQueryBo bo) {
        LambdaQueryWrapper<TjTeamTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(Objects.nonNull(bo.getTeamId()), TjTeamTask::getTeamId, bo.getTeamId())
            .like(StrUtil.isNotBlank(bo.getTaskName()), TjTeamTask::getTaskName, bo.getTaskName())
            .ge(Objects.nonNull(bo.getSignBeginDate()), TjTeamTask::getSignDate, bo.getSignBeginDate())
            .le(Objects.nonNull(bo.getSignEndDate()), TjTeamTask::getSignDate, bo.getSignEndDate());
        return lqw;
    }

    /**
     * 新增团检任务管理
     */
    @Override
    public Boolean insertByBo(TjTeamTaskBo bo) {
        validEntityBeforeSave(bo, Boolean.TRUE);
        TjTeamTask add = MapstructUtils.convert(bo, TjTeamTask.class);
        boolean flag = baseMapper.insert(add) > 0;
        List<TjTeamGroup> groupList = MapstructUtils.convert(bo.getGroupList(), TjTeamGroup.class);
        String teamName = iTjTeamInfoService.selectTeamNameById(add.getTeamId());
        groupList.forEach(k -> k.setTaskId(add.getId()).setTaskName(add.getTaskName())
            .setTeamId(add.getTeamId()).setTeamName(teamName));
        tjTeamGroupMapper.insertBatch(groupList);
        return flag;
    }

    /**
     * 修改团检任务管理
     */
    @Override
    public Boolean updateByBo(TjTeamTaskBo bo) {
        validEntityBeforeSave(bo, Boolean.FALSE);
        TjTeamTask update = MapstructUtils.convert(bo, TjTeamTask.class);
        boolean flag = baseMapper.updateById(update) > 0;
        List<TjTeamGroup> groupList = MapstructUtils.convert(bo.getGroupList(), TjTeamGroup.class);
        String teamName = iTjTeamInfoService.selectTeamNameById(update.getTeamId());
        groupList.forEach(k -> k.setTaskId(update.getId()).setTaskName(update.getTaskName())
            .setTeamId(update.getTeamId()).setTeamName(teamName));
        tjTeamGroupMapper.insertOrUpdateBatch(groupList);
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TjTeamTaskBo bo, Boolean isAdd){
        //TODO 做一些数据校验,如唯一约束
        //同单位任务名称校验
        if(isAdd) {
            long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamTask.class)
                .eq(TjTeamTask::getTaskName, bo.getTaskName())
                .eq(TjTeamTask::getTeamId, bo.getTeamId()));
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_TASKNAME_ISEXIST, bo.getTaskName());
            }
        }
        //同任务相同分组名称校验
        Set<String> set = CollUtil.newHashSet();
        Set<TjTeamGroupBo> groupNameSet = bo.getGroupList().stream().filter(e -> !set.add(e.getGroupName())).collect(Collectors.toSet());
        if(CollUtil.isNotEmpty(groupNameSet)) {
            throw new PeisException(ErrorCodeConstants.PEIS_GROUPNAME_REPEAT,
                groupNameSet.stream().map(TjTeamGroupBo::getGroupName).collect(Collectors.joining(StrUtil.COMMA)));
        }
        //职业病在岗状态校验
        boolean flag = (Objects.equals(bo.getPhysicalType(), PhysicalTypeEnum.ZYJKTJ.name()) ||
            Objects.equals(bo.getPhysicalType(), PhysicalTypeEnum.FSTJ.name())) &&
            bo.getGroupList().stream().anyMatch(e -> Objects.isNull(e.getDutyStatus()));
        Map<String, Object> enumMap = EnumUtil.getNameFieldMap(PhysicalTypeEnum.class, "desc");
        if(flag) {
            throw new PeisException(ErrorCodeConstants.PEIS_DUTY_STATUS_NOT_EMPTY, enumMap.get(bo.getPhysicalType()));
        }
        List<TjTeamGroupBo> boList = bo.getGroupList().stream().filter(e -> Objects.equals(e.getGroupType(), GroupTypeEnum.ITEM.getCode()) ||
            Objects.equals(e.getGroupType(), GroupTypeEnum.PRICE.getCode())).toList();
        //项目分组、金额分组折扣校验
        if(boList.stream().anyMatch(e -> Objects.isNull(e.getItemDiscount()) || Objects.isNull(e.getAddDiscount()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_ITEM_DISCOUNT_NOT_EMPTY);
        }
        //项目分组、金额分组支付方式校验
        if(boList.stream().anyMatch(e -> StrUtil.isBlank(e.getGroupPayType()) || StrUtil.isBlank(e.getAddPayType()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_PAY_TYPE_NOT_EMPTY);
        }
        //金额分组金额校验
        if(bo.getGroupList().stream().anyMatch(e -> (Objects.isNull(e.getPrice()) || e.getPrice().compareTo(BigDecimal.ZERO) <= 0) &&
            Objects.equals(e.getGroupType(), GroupTypeEnum.PRICE.getCode()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_PRICE_NOT_EMPTY);
        }
        //折扣分组折扣校验
        if(bo.getGroupList().stream().anyMatch(e -> Objects.isNull(e.getItemDiscount()) &&
            Objects.equals(e.getGroupType(), GroupTypeEnum.DISCOUNT.getCode()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_DISCOUNT_NOT_EMPTY);
        }
        //折扣分组支付方式校验
        if(bo.getGroupList().stream().anyMatch(e -> StrUtil.isBlank(e.getGroupPayType()) &&
            Objects.equals(e.getGroupType(), GroupTypeEnum.DISCOUNT.getCode()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_DISCOUNT_PAY_TYPE_NOT_EMPTY);
        }
    }

    /**
     * 批量删除团检任务管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
