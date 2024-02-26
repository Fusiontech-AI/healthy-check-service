package org.fxkc.peis.register.change;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.enums.SexEnum;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.peis.domain.*;
import org.fxkc.peis.domain.bo.TjRegCombinAddBo;
import org.fxkc.peis.domain.bo.TjRegCombinItemBo;
import org.fxkc.peis.enums.CheckStatusEnum;
import org.fxkc.peis.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractRegisterChange implements RegisterChangeService {

    protected String operateCode;

    @Autowired
    RegisterChangeHolder registerChangeHolder;

    @Autowired
    protected TjRegisterMapper tjRegisterMapper;

    @Autowired
    protected TjRegCombinationProjectMapper tjRegCombinationProjectMapper;

    @Autowired
    protected TjRegBasicProjectMapper tjRegBasicProjectMapper;

    @Autowired
    protected TjCombinationProjectInfoMapper tjCombinationProjectInfoMapper;

    @Autowired
    protected TjCombinationProjectMapper tjCombinationProjectMapper;
    @PostConstruct
    public void init() {
        registerChangeHolder.putBuilder(operateCode, this);
    }

    @Override
    public void changeRegCombin(TjRegCombinAddBo tjRegCombinAddBo){
        TjRegister tjRegister = tjRegisterMapper.selectById(tjRegCombinAddBo.getRegisterId());
        Assert.notNull(tjRegister,"根据登记id"+tjRegCombinAddBo.getRegisterId() +",未找到记录信息!");
        //前置参数检查
        changeBeforeCheck(tjRegCombinAddBo,tjRegister);
        //逻辑处理
        doService(tjRegCombinAddBo);
        //后置信息更新
        TjRegister updateRegister = new TjRegister();
        updateRegister.setId(tjRegCombinAddBo.getRegisterId());
        changeAfterUpdate(tjRegCombinAddBo,updateRegister);
    }


    /**
     * 前置参数检查
     * @param tjRegCombinAddBo
     */
    public void changeBeforeCheck(TjRegCombinAddBo tjRegCombinAddBo,TjRegister tjRegister){
        List<TjRegCombinItemBo> tjRegCombinItemBos = tjRegCombinAddBo.getTjRegCombinItemBos();
        if(CollUtil.isNotEmpty(tjRegCombinItemBos)){
            //对组合项目适应性别进行校验。
            List<TjCombinationProject> tjCombinationProjects = tjCombinationProjectMapper.selectBatchIds(tjRegCombinItemBos.stream().map(m -> m.getCombinationProjectId()).collect(Collectors.toList()));
            Optional<TjCombinationProject> sexAny = tjCombinationProjects.stream().filter(m -> !Objects.equals(SexEnum.不限.getCode(), m.getSuitSex())
                && !Objects.equals(tjRegister.getGender(), m.getSuitSex())
            ).findAny();
            if(sexAny.isPresent()){
                throw new RuntimeException("当前进行体检登记时,选择项目要求性别和当前登记人性别不匹配!");
            }
        }

    };

    /**
     * 逻辑处理
     * @param bo
     */
    public void doService(TjRegCombinAddBo bo){
        List<TjRegCombinItemBo> combinItemBos = bo.getTjRegCombinItemBos();
        List<TjRegCombinationProject> tjRegCombinationProjects = tjRegCombinationProjectMapper.selectList(new LambdaQueryWrapper<TjRegCombinationProject>()
            .eq(TjRegCombinationProject::getRegisterId, bo.getRegisterId()));
        if(CollUtil.isNotEmpty(tjRegCombinationProjects)){
            //筛选出需要删除的记录信息
            List<TjRegCombinItemBo> comBinIds = combinItemBos.stream().filter(m -> Objects.nonNull(m.getId())).collect(Collectors.toList());
            List<TjRegCombinationProject> deleteItems = tjRegCombinationProjects.stream().filter(m -> !comBinIds.contains(m.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(deleteItems)){
                //待删除记录中，存在项目己检查的 无法删除 (后面还要除去 不显示的项目记录)
                if(deleteItems.stream().anyMatch(m -> Objects.equals(CheckStatusEnum.已检查.getCode(), m.getCheckStatus()))){
                    throw new RuntimeException("已检查的项目无法删除!");
                }
                //删除相关记录 并删除管理的子项记录
                tjRegCombinationProjectMapper.deleteBatchIds(deleteItems);
                tjRegBasicProjectMapper.delete(new LambdaQueryWrapper<TjRegBasicProject>()
                    .in(TjRegBasicProject::getRegItemId,deleteItems.stream().map(m->m.getId()).collect(Collectors.toList())));
            }

        }

        //筛选出需要变更的记录信息 这里做价格的更新
        List<TjRegCombinItemBo> updateItems = combinItemBos.stream().filter(m -> Objects.nonNull(m.getId())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(updateItems)){
            List<TjRegCombinationProject> collect = updateItems.stream().map(m -> {
                TjRegCombinationProject build = TjRegCombinationProject.builder()
                    .id(m.getId())
                    .standardAmount(m.getStandardAmount())
                    .receivableAmount(m.getReceivableAmount())
                    .discount(m.getDiscount()).build();
                return build;
            }).collect(Collectors.toList());
            tjRegCombinationProjectMapper.updateBatchById(collect);
        }

        //筛选出需要新增的记录信息
        List<TjRegCombinItemBo> addItems = combinItemBos.stream().filter(m -> Objects.isNull(m.getId())).collect(Collectors.toList());
        addRegCombinItems(addItems,bo.getRegisterId());
    };


    /**
     * 后置信息更新
     * @param bo
     */
    public void changeAfterUpdate(TjRegCombinAddBo bo,TjRegister tjRegister){
        //对待修改的金额相关信息 进行更新前置赋值操作
        tjRegister.setTotalStandardAmount(bo.getStandardAmount());
        tjRegister.setTotalAmount(bo.getReceivableAmount());
        tjRegister.setPersonAmount(bo.getPersonAmount());
        tjRegister.setDiscount(bo.getDiscount());
        tjRegister.setTeamAmount(bo.getTeamAmount());
        tjRegister.setPaidTotalAmount(bo.getPaidTotalAmount());
        tjRegister.setPaidPersonAmount(bo.getPaidPersonAmount());
        tjRegister.setPaidTeamAmount(bo.getPaidTeamAmount());
    };


    public void addRegCombinItems(List<TjRegCombinItemBo> addItems,Long registerId){
        if(CollUtil.isNotEmpty(addItems)){
            List<TjRegCombinationProject> combinationProjects = MapstructUtils.convert(addItems, TjRegCombinationProject.class);
            combinationProjects.stream().forEach(m->{
                m.setRegisterId(registerId);
            });
            //这里需要针对于项目无需显示的项目信息 默认赋值已检查
            tjRegCombinationProjectMapper.insertBatch(combinationProjects);
            //需要根据组合项目id 关联查询出对应的基础项目信息 并插入
            List<TjCombinationProjectInfo> tjCombinationProjectInfos = tjCombinationProjectInfoMapper.selectList(new LambdaQueryWrapper<TjCombinationProjectInfo>()
                .in(TjCombinationProjectInfo::getCombinProjectId, combinationProjects.stream().map(m -> m.getCombinationProjectId()).collect(Collectors.toList())));

            if(CollUtil.isNotEmpty(tjCombinationProjectInfos)){
                combinationProjects.stream().forEach(m->{
                    List<TjCombinationProjectInfo> infos = tjCombinationProjectInfos.stream().filter(f -> Objects.equals(f.getCombinProjectId(), m.getCombinationProjectId())).collect(Collectors.toList());
                    List<TjRegBasicProject> tjRegBasicProjectList = infos.stream().map(f -> {
                        TjRegBasicProject build = TjRegBasicProject.builder()
                            .regId(registerId)
                            .regItemId(m.getId())
                            .combinationProjectId(m.getCombinationProjectId())
                            .basicProjectId(f.getBasicProjectId()).build();
                        return build;
                    }).collect(Collectors.toList());
                    tjRegBasicProjectMapper.insertBatch(tjRegBasicProjectList);
                });
            }

        }
    }


}
