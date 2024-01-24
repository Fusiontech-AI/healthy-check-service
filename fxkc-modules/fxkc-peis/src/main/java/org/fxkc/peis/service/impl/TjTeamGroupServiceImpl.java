package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.*;
import org.fxkc.peis.enums.GroupTypeEnum;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.*;
import org.springframework.stereotype.Service;
import org.fxkc.peis.domain.bo.TjTeamGroupBo;
import org.fxkc.peis.domain.vo.TjTeamGroupVo;
import org.fxkc.peis.service.ITjTeamGroupService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 团检分组信息Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@RequiredArgsConstructor
@Service
public class TjTeamGroupServiceImpl extends ServiceImpl<TjTeamGroupMapper, TjTeamGroup> implements ITjTeamGroupService {

    private final TjRegisterMapper tjRegisterMapper;

    private final TjTeamGroupItemMapper tjTeamGroupItemMapper;

    private final TjTeamGroupHazardsMapper tjTeamGroupHazardsMapper;

    private final TjTeamGroupHistoryMapper tjTeamGroupHistoryMapper;

    private final TjRegCombinationProjectMapper tjRegCombinationProjectMapper;

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
            long count = tjRegisterMapper.selectCount(Wrappers.lambdaQuery(TjRegister.class)
                .in(TjRegister::getTeamGroupId, ids));
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_REGISTER_GROUP_ISEXIST);
            }
        }
        tjTeamGroupItemMapper.delete(Wrappers.lambdaQuery(TjTeamGroupItem.class)
            .in(TjTeamGroupItem::getGroupId, ids));
        tjTeamGroupHazardsMapper.delete(Wrappers.lambdaQuery(TjTeamGroupHazards.class)
            .in(TjTeamGroupHazards::getGroupId, ids));
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordGroupInfo(List<TjTeamGroupBo> groupList) {
        List<TjRegCombinationProject> projectList = CollUtil.newArrayList();
        List<TjTeamGroupHistory> groupHistoryList = CollUtil.newArrayList();
        if(CollUtil.isNotEmpty(groupList)) {
            List<Long> groupIds = StreamUtils.toList(groupList, TjTeamGroupBo::getId);
            List<TjRegister> list = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
                .in(TjRegister::getTeamGroupId, groupIds)
                .select(TjRegister::getId, TjRegister::getHealthyCheckStatus));
            List<TjTeamGroup> orgList = baseMapper.selectBatchIds(groupIds);
            Map<Long, TjTeamGroup> orgMap = StreamUtils.toMap(orgList, TjTeamGroup::getId, e -> e);
            List<Long> historyList = tjTeamGroupHistoryMapper.selectList(Wrappers.lambdaQuery(TjTeamGroupHistory.class)
                .in(TjTeamGroupHistory::getGroupId, groupIds)
                .select(TjTeamGroupHistory::getRegId)).stream().map(TjTeamGroupHistory::getRegId).toList();
            groupList.forEach(k -> {
                List<TjRegister> groupRegisters = StreamUtils.filter(list, e -> Objects.equals(k.getId(), e.getTeamGroupId()));
                if(Objects.equals(CommonConstants.NORMAL, k.getIsSyncProject())) {
                    boolean isTeamPay = Objects.equals("1", k.getGroupPayType());
                    groupRegisters.stream().filter(e -> Objects.equals(HealthyCheckTypeEnum.预约.getCode(), e.getHealthyCheckStatus()))
                        .forEach(s -> {
                            //todo tjRegisterMapper更新个人金额、团检金额、体检类型

                            //删除已记录的人员分组信息
                            tjTeamGroupHistoryMapper.delete(Wrappers.lambdaUpdate(TjTeamGroupHistory.class)
                                .eq(TjTeamGroupHistory::getRegId, s.getId()));
                            if(Objects.equals(GroupTypeEnum.ITEM.getCode(), k.getGroupType())) {
                                //删除人员项目信息,新增新分组项目信息
                                tjRegCombinationProjectMapper.delete(Wrappers.lambdaUpdate(TjRegCombinationProject.class)
                                    .eq(TjRegCombinationProject::getRegisterId, s.getId()));
                                //todo 缺少套餐id
                                k.getGroupItemList().forEach(d -> {
                                    projectList.add(TjRegCombinationProject.builder()
                                        .registerId(s.getId())
                                        .combinationProjectId(d.getItemId())
                                        .receivableAmount(d.getActualPrice())
                                        .standardAmount(d.getStandardPrice())
                                        .payMode(k.getGroupPayType())
                                        .payStatus(isTeamPay ? "1" : "0")
                                        .checkStatus("0")
                                        .projectType(d.getInclude())
                                        .discount(d.getDiscount())
                                        .projectRequiredType(d.getIsRequired() ?  "1" : "0")
                                        .build());
                                });
                            }
                        });
                    groupRegisters = StreamUtils.filter(groupRegisters, e -> !Objects.equals(HealthyCheckTypeEnum.预约.getCode(), e.getHealthyCheckStatus()));
                }
                TjTeamGroup orgGroup = orgMap.get(k.getId());
                //过滤出未记录的人员记录编辑前分组信息
                groupRegisters.stream().filter(e -> !historyList.contains(e.getId())).forEach(x ->
                    groupHistoryList.add(Objects.requireNonNull(MapstructUtils.convert(orgGroup, TjTeamGroupHistory.class))
                    .setId(null)
                    .setGroupId(orgGroup.getId())
                    .setRegId(x.getId())));
            });
        }
        if(CollUtil.isNotEmpty(projectList)) {
            tjRegCombinationProjectMapper.insertBatch(projectList);
        }
        if(CollUtil.isNotEmpty(groupHistoryList)) {
            tjTeamGroupHistoryMapper.insertBatch(groupHistoryList);
        }

    }
}
