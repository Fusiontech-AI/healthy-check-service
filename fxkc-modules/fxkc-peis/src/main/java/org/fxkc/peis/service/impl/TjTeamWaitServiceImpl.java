package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.TjRegCombinationProject;
import org.fxkc.peis.domain.TjRegister;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.TjTeamGroupItem;
import org.fxkc.peis.domain.bo.TjTeamWaitSwitchGroupBo;
import org.fxkc.peis.domain.vo.TjTeamWaitTaskGroupVo;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.mapper.*;
import org.fxkc.peis.service.ITjTeamWaitService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 体检单位待检人员Service业务层处理
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TjTeamWaitServiceImpl implements ITjTeamWaitService {

    private final TjTeamTaskMapper tjTeamTaskMapper;
    private final TjTeamGroupMapper tjTeamGroupMapper;
    private final TjTeamGroupItemMapper tjTeamGroupItemMapper;
    private final TjRegisterMapper tjRegisterMapper;
    private final TjRegCombinationProjectMapper tjRegCombinationProjectMapper;

    /**
     * 查询体检单位待检人员详情任务分组树
     */
    @Override
    public List<TjTeamWaitTaskGroupVo> teamSettleWaitTaskGroupTree(Long teamId) {
        return tjTeamTaskMapper.teamSettleWaitTaskGroupTree(teamId);
    }

    /**
     * 体检单位待检人员批量换组
     */
    @Override
    public Boolean batchSwitchGroup(TjTeamWaitSwitchGroupBo bo) {
        TjTeamGroup tjTeamGroup = tjTeamGroupMapper.selectById(bo.getGroupId());
        if(ObjectUtil.isNotNull(tjTeamGroup)){
            List<Long> ids = List.of(bo.getId());
            List<TjRegister> tjRegisterList = tjRegisterMapper.selectBatchIds(ids);
            if(CollUtil.isNotEmpty(tjRegisterList)){
                long count = tjRegisterList.stream().filter(f -> !StrUtil.equals(f.getHealthyCheckStatus(),"0")).count();
                if(count > 0){
                    throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMWAIT_NOT_APPOINTMENT_STATUS);
                }
                List<TjRegCombinationProject> tjRegCombinationProjectList = Lists.newArrayList();
                tjRegisterList.stream().forEach(f -> {
                    if(!StrUtil.equals(tjTeamGroup.getGender(),"2") && !StrUtil.equals(f.getGender(),tjTeamGroup.getGender())){
                        throw new PeisException(ErrorCodeConstants.PEIS_TJTEAMWAIT_NOT_EQUALS_SEX);
                    }
                    f.setChargeStatus("0");
                    f.setTeamGroupId(tjTeamGroup.getId());
                    f.setPackageId(tjTeamGroup.getPackageId());
                    f.setTeamAmount(tjTeamGroup.getActualPrice());

                    if(ObjectUtil.equal(tjTeamGroup.getGroupType(),1)){
                        List<TjTeamGroupItem> tjTeamGroupItemList = tjTeamGroupItemMapper.selectList(new LambdaQueryWrapper<TjTeamGroupItem>()
                            .eq(TjTeamGroupItem::getGroupId,tjTeamGroup.getId()));
                        tjRegCombinationProjectList.addAll(tjTeamGroupItemList.stream().map(m ->
                            TjRegCombinationProject.builder()
                                .registerId(f.getId())
                                .combinationProjectId(m.getItemId())
                                .projectType(m.getInclude())
                                .standardAmount(m.getStandardPrice())
                                .discount(m.getDiscount())
                                .receivableAmount(m.getActualPrice())
                                .personAmount(StrUtil.equals(tjTeamGroup.getGroupPayType(),"0") ? m.getActualPrice() : BigDecimal.ZERO)
                                .teamAmount(StrUtil.equals(tjTeamGroup.getGroupPayType(),"1") ? m.getActualPrice() : BigDecimal.ZERO)
                                .payStatus("0")
                                .payMode(tjTeamGroup.getGroupPayType())
                                .checkStatus("0")
                                .projectRequiredType(m.getRequired() ? "1" : "0")
                                .delFlag("0")
                                .build()
                        ).collect(Collectors.toList()));
                    }
                });
                tjRegCombinationProjectMapper.update(TjRegCombinationProject.builder().delFlag("1").build(), new LambdaUpdateWrapper<TjRegCombinationProject>()
                    .in(TjRegCombinationProject::getRegisterId,ids)
                    .eq(TjRegCombinationProject::getDelFlag,"0"));
                if(CollUtil.isNotEmpty(tjRegCombinationProjectList)){
                    tjRegCombinationProjectMapper.insertBatch(tjRegCombinationProjectList);
                }
                tjRegisterMapper.updateBatchById(tjRegisterList);
            }
        }
        return Boolean.TRUE;
    }

}
