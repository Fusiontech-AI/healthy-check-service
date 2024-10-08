package org.fxkc.peis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.fxkc.common.core.constant.CommonConstants;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.excel.core.DropDownOptions;
import org.fxkc.common.excel.core.ExcelResult;
import org.fxkc.common.excel.utils.ExcelUtil;
import org.fxkc.common.mybatis.core.page.PageQuery;
import org.fxkc.common.mybatis.core.page.TableDataInfo;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.constant.ErrorCodeConstants;
import org.fxkc.peis.domain.*;
import org.fxkc.peis.domain.bo.*;
import org.fxkc.peis.domain.vo.*;
import org.fxkc.peis.enums.CertificateTypeEnum;
import org.fxkc.peis.enums.GroupTypeEnum;
import org.fxkc.peis.enums.HealthyCheckTypeEnum;
import org.fxkc.peis.enums.PhysicalTypeEnum;
import org.fxkc.peis.exception.PeisException;
import org.fxkc.peis.listener.TjTaskImportListener;
import org.fxkc.peis.mapper.*;
import org.fxkc.peis.register.change.RegisterChangeHolder;
import org.fxkc.peis.register.change.RegisterChangeService;
import org.fxkc.peis.register.insert.RegisterInsertHolder;
import org.fxkc.peis.register.insert.RegisterInsertService;
import org.fxkc.peis.service.ITjRegisterService;
import org.fxkc.peis.service.ITjTeamGroupService;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.fxkc.peis.service.ITjTeamTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

    private final TjRegisterMapper tjRegisterMapper;

    private final ITjRegisterService tjRegisterService;

    private final ITjTeamGroupService iTjTeamGroupService;

    private final RegisterInsertHolder registerInsertHolder;

    private final TjTeamGroupItemMapper tjTeamGroupItemMapper;

    private final TjTeamGroupHazardsMapper tjTeamGroupHazardsMapper;

    private final RegisterChangeHolder registerChangeHolder;

    private final TjRegisterZybMapper tjRegisterZybMapper;

    /**
     * 查询团检任务管理
     */
    @Override
    public TjTeamTaskDetailVo queryById(Long id){
        TjTeamTask teamTask = baseMapper.selectById(id);
        TjTeamTaskDetailVo vo = MapstructUtils.convert(teamTask, TjTeamTaskDetailVo.class);
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
            .eq(StrUtil.isNotBlank(bo.getIsReview()), TjTeamTask::getIsReview, bo.getIsReview());
        if(Objects.equals(CommonConstants.NORMAL, bo.getPendingReview())) {
            lqw.eq(TjTeamTask::getReviewResult, CommonConstants.NORMAL);
        }else if(Objects.equals(CommonConstants.DISABLE, bo.getPendingReview())) {
            lqw.in(TjTeamTask::getReviewResult, List.of("1","2"));
        }
        if(Objects.equals(CommonConstants.NORMAL, bo.getIsAccord())) {
            lqw.and(wrapper -> wrapper.eq(TjTeamTask::getIsReview, CommonConstants.DISABLE)
                .or().eq(TjTeamTask::getReviewResult, "1"));
        }
        if(StrUtil.isNotBlank(bo.getSignBeginDate())) {
            lqw.ge(TjTeamTask::getSignDate, DateUtil.parseDate(bo.getSignBeginDate()));
        }
        if(StrUtil.isNotBlank(bo.getSignEndDate())) {
            lqw.le(TjTeamTask::getSignDate, DateUtil.parseDate(bo.getSignEndDate()));
        }
        return lqw;
    }

    /**
     * 新增团检任务管理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TjTeamTaskCommonVo insertByBo(TjTeamTaskBo bo) {
        validEntityBeforeSave(bo, Boolean.TRUE);
        TjTeamTask add = MapstructUtils.convert(bo, TjTeamTask.class);
        String taskNumber = baseMapper.queryTaskNumber();
        add.setTaskNo(DateUtil.year(DateUtil.date()) + StringUtils.zeroPrefix(taskNumber, 3));
        baseMapper.insert(add);
        List<TjTeamGroup> groupList = MapstructUtils.convert(bo.getGroupList(), TjTeamGroup.class);
        String teamName = iTjTeamInfoService.selectTeamNameById(add.getTeamId());
        groupList.forEach(k -> k.setTaskId(add.getId()).setTaskName(add.getTaskName())
            .setTeamId(add.getTeamId()).setTeamName(teamName));
        tjTeamGroupMapper.insertBatch(groupList);
        return new TjTeamTaskCommonVo().setTaskId(add.getId())
            .setGroupList(getTaskItemGroupInfo(add.getId(), bo.getPhysicalType()));
    }

    /**
     * 修改团检任务管理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TjTeamTaskCommonVo updateByBo(TjTeamTaskBo bo) {
        validEntityBeforeSave(bo, Boolean.FALSE);
        TjTeamTask query = baseMapper.selectById(bo.getId());
        TjTeamTask update = MapstructUtils.convert(bo, TjTeamTask.class);
        baseMapper.updateById(update);
        List<TjTeamGroup> groupList = MapstructUtils.convert(bo.getGroupList(), TjTeamGroup.class);
        String teamName = iTjTeamInfoService.selectTeamNameById(update.getTeamId());
        //职业病、放射修改成其他在岗状态、照射源、照射源种类更新为空
        groupList.forEach(k -> {
            k.setTaskId(update.getId()).setTaskName(update.getTaskName())
            .setTeamId(update.getTeamId()).setTeamName(teamName);
            if(Objects.equals(PhysicalTypeEnum.FSTJ.name(), query.getPhysicalType()) &&
                !Objects.equals(PhysicalTypeEnum.FSTJ.name(), bo.getPhysicalType())) {
                k.setShineSource(StrUtil.EMPTY).setShineType(StrUtil.EMPTY);
            }
            if(PhysicalTypeEnum.isOccupational(query.getPhysicalType()) &&
                !PhysicalTypeEnum.isOccupational(bo.getPhysicalType())) {
                k.setDutyStatus(StrUtil.EMPTY);
            }
        });
        //修改任务分组记录分组人员分组信息
        List<TjTeamGroup> recordList = StreamUtils.filter(groupList, e -> Objects.nonNull(e.getId()));
        iTjTeamGroupService.recordGroupInfo(recordList);
        tjTeamGroupMapper.insertOrUpdateBatch(groupList);
        return new TjTeamTaskCommonVo().setTaskId(bo.getId())
            .setGroupList(getTaskItemGroupInfo(bo.getId(), bo.getPhysicalType()));
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
        //项目分组、金额加项折扣校验
        if(boList.stream().anyMatch(e -> Objects.isNull(e.getAddDiscount()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_ITEM_DISCOUNT_NOT_EMPTY);
        }
        //项目分组、金额加项支付方式校验
        if(boList.stream().anyMatch(e -> StrUtil.isBlank(e.getAddPayType()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_ADD_PAY_TYPE_NOT_EMPTY);
        }
        //金额分组金额校验
        if(bo.getGroupList().stream().anyMatch(e -> (Objects.isNull(e.getPrice()) || e.getPrice().compareTo(BigDecimal.ZERO) <= 0) &&
            Objects.equals(e.getGroupType(), GroupTypeEnum.PRICE.getCode()))) {
            throw new PeisException(ErrorCodeConstants.PEIS_PRICE_NOT_EMPTY);
        }
    }

    /**
     * 批量删除团检任务管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
            long count = tjRegisterMapper.selectCount(Wrappers.lambdaQuery(TjRegister.class)
                .in(TjRegister::getTaskId, ids)
                .gt(TjRegister::getHealthyCheckStatus, HealthyCheckTypeEnum.预约.getCode()));
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_TASK_NOT_APPIONT);
            }
        }
        List<TjTeamGroup> groupList = tjTeamGroupMapper.selectList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .in(TjTeamGroup::getTaskId, ids));
        if(CollUtil.isNotEmpty(groupList)) {
            List<Long> groupIds = StreamUtils.toList(groupList, TjTeamGroup::getId);
            tjTeamGroupMapper.deleteBatchIds(groupIds);
            tjTeamGroupItemMapper.delete(Wrappers.lambdaQuery(TjTeamGroupItem.class)
                .in(TjTeamGroupItem::getGroupId, groupIds));
            tjTeamGroupHazardsMapper.delete(Wrappers.lambdaQuery(TjTeamGroupHazards.class)
                .in(TjTeamGroupHazards::getGroupId, groupIds));
        }
        List<TjRegister> tjRegisterList = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
            .in(TjRegister::getTaskId, ids)
            .eq(TjRegister::getHealthyCheckStatus, HealthyCheckTypeEnum.预约.getCode()));
        if(CollUtil.isNotEmpty(tjRegisterList)) {
            List<Long> registerIds = StreamUtils.toList(tjRegisterList, TjRegister::getId);
            tjRegisterMapper.deleteBatchIds(registerIds);
            tjRegisterZybMapper.delete(Wrappers.lambdaQuery(TjRegisterZyb.class)
                .in(TjRegisterZyb::getRegId, registerIds));
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public TjVerifyMessageVo verifyGroupData(List<TjGroupVerifyBo> list) {
        TjVerifyMessageVo vo = new TjVerifyMessageVo();
        vo.setIsPrompt(Boolean.FALSE);
        List<TjTeamGroupBo> boList = MapstructUtils.convert(list, TjTeamGroupBo.class);
        validEntityBeforeSave(new TjTeamTaskBo().setGroupList(boList), Boolean.FALSE);
        List<Long> idList = StreamUtils.toList(list, TjGroupVerifyBo::getId);
        List<TjTeamGroup> groupList = tjTeamGroupMapper.selectBatchIds(idList);
        List<TjRegister> tjRegisterList = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
            .in(TjRegister::getTeamGroupId, idList)
            .eq(TjRegister::getHealthyCheckStatus, HealthyCheckTypeEnum.预约.getCode()));
        Map<Long, Long> tjRegisterMap = tjRegisterList.stream().collect(Collectors.groupingBy(
            TjRegister::getTeamGroupId, Collectors.counting()));
        StringBuffer buffer = new StringBuffer();
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(k -> groupList.forEach(s -> {
            if(Objects.equals(k.getId(), s.getId())) {
                boolean isPrompt = Boolean.FALSE;
                Long count = tjRegisterMap.getOrDefault(k.getId(), 0L);
                if(k.getItemDiscount().compareTo(s.getItemDiscount()) != 0) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【折扣调整为<span style='color:red'>").append(k.getItemDiscount())
                        .append("</span>】、");
                }
                if(ObjectUtil.notEqual(k.getGroupType(), GroupTypeEnum.DISCOUNT.getCode())) {
                    if(k.getAddDiscount().compareTo(s.getAddDiscount()) != 0) {
                        isPrompt = Boolean.TRUE;
                        buffer.append("【加项折扣调整为<span style='color:red'>").append(k.getAddDiscount())
                            .append("</span>】、");
                    }
                    if(ObjectUtil.notEqual(k.getAddPayType(), s.getAddPayType())) {
                        isPrompt = Boolean.TRUE;
                        buffer.append("【加项支付方式调整为<span style='color:red'>")
                            .append(Objects.equals("0", k.getAddPayType()) ? "个人" : "单位")
                            .append("</span>】、");
                    }
                }
                if(ObjectUtil.notEqual(k.getGroupPayType(), s.getGroupPayType())) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【分组内支付方式调整为<span style='color:red'>")
                        .append(Objects.equals("0", k.getGroupPayType()) ? "个人" : "单位")
                        .append("</span>】、");
                }
                if(ObjectUtil.equal(k.getGroupType(), GroupTypeEnum.PRICE.getCode()) &&
                    k.getPrice().compareTo(s.getPrice()) != 0) {
                    isPrompt = Boolean.TRUE;
                    BigDecimal difference = k.getPrice().subtract(s.getPrice());
                    buffer.append("【金额调整为<span style='color:red'>").append(k.getPrice())
                        .append("</span>，较原分组").append(difference.signum() > 0 ? "增加" : "减少").append("<span style='color:red'>")
                        .append(difference.abs()).append("</span>").append(count > 0 ? "，同步分组内预约状态人员后，总差额<span style='color:red'>"
                        .concat(difference.abs().multiply(new BigDecimal(count)).toPlainString()).concat("</span>元】、") : "】、");
                }
                if(!isPrompt && ObjectUtil.notEqual(k.getIsSyncProject(), s.getIsSyncProject())) {
                    buffer.insert(0, index.getAndIncrement() + ".【" + k.getGroupName() + "】").append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                        "确认后将此分组信息同步本至分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                            .concat("</span>人。") : "确认后新入组人员将按照此规则执行。").append("</br>");
                }else if(isPrompt) {
                    buffer.insert(0, index.getAndIncrement() + ".【" + k.getGroupName() + "】").deleteCharAt(buffer.length() - 1).append("，")
                        .append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                            "确认后将同步本分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                                .concat("</span>人。") : "新入组人员将按照此规则执行。").append("</br>");
                }
            }
        }));
        if(buffer.length() > 0) {
            vo.setPromptMessage(buffer.toString().concat("是否确认保存？"));
            vo.setIsPrompt(Boolean.TRUE);
        }
        return vo;
    }

    @Override
    public TjVerifyMessageVo verifyGroupPackageData(List<TjGroupVerifyPackageBo> list) {
        TjVerifyMessageVo vo = new TjVerifyMessageVo();
        vo.setIsPrompt(Boolean.FALSE);
        List<Long> idList = StreamUtils.toList(list, TjGroupVerifyPackageBo::getId);
        List<TjTeamGroup> groupList = tjTeamGroupMapper.selectBatchIds(idList);
        List<TjRegister> tjRegisterList = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
            .in(TjRegister::getTeamGroupId, idList)
            .eq(TjRegister::getHealthyCheckStatus, HealthyCheckTypeEnum.预约.getCode()));
        Map<Long, Long> tjRegisterMap = tjRegisterList.stream().collect(Collectors.groupingBy(
            TjRegister::getTeamGroupId, Collectors.counting()));
        StringBuffer buffer = new StringBuffer();
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(k -> groupList.forEach(s -> {
            if(Objects.equals(k.getId(), s.getId())) {
                boolean isPrompt = Boolean.FALSE;
                Long count = tjRegisterMap.getOrDefault(k.getId(), 0L);
                if(k.getItemDiscount().compareTo(s.getItemDiscount()) != 0) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【折扣调整为<span style='color:red'>").append(k.getItemDiscount())
                        .append("</span>】、");
                }
                if(k.getAddDiscount().compareTo(s.getAddDiscount()) != 0) {
                    isPrompt = Boolean.TRUE;
                    buffer.append("【加项折扣调整为<span style='color:red'>").append(k.getAddDiscount())
                        .append("</span>】、");
                }
                if(k.getActualPrice().compareTo(s.getActualPrice()) != 0) {
                    isPrompt = Boolean.TRUE;
                    BigDecimal difference = k.getActualPrice().subtract(s.getActualPrice());
                    buffer.append("【实际价格调整为<span style='color:red'>").append(k.getActualPrice())
                        .append("</span>，较原分组").append(difference.signum() > 0 ? "增加" : "减少").append("<span style='color:red'>")
                        .append(difference.abs()).append("</span>").append(count > 0 ? "，同步分组内预约状态人员后，总差额<span style='color:red'>"
                        .concat(difference.abs().multiply(new BigDecimal(count)).toPlainString()).concat("</span>元】、") : "】、");
                }
                if(!isPrompt && ObjectUtil.notEqual(k.getIsSyncProject(), s.getIsSyncProject())) {
                    buffer.insert(0, index.getAndIncrement() + ".【" + k.getGroupName() + "】").append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                        "确认后将此分组信息同步本至分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                            .concat("</span>人。") : "确认后新入组人员将按照此规则执行。").append("</br>");
                }else if(isPrompt) {
                    buffer.insert(0, index.getAndIncrement() + ".【" + k.getGroupName() + "】").deleteCharAt(buffer.length() - 1).append("，")
                        .append(ObjectUtil.equal("1", k.getIsSyncProject()) ?
                            "确认后将同步本分组内预约状态的未检人员<span style='color:red'>".concat(String.valueOf(count))
                                .concat("</span>人。") : "新入组人员将按照此规则执行。").append("</br>");
                }
            }
        }));
        if(buffer.length() > 0) {
            vo.setPromptMessage(buffer.toString().concat("是否确认保存？"));
            vo.setIsPrompt(Boolean.TRUE);
        }
        return vo;
    }

    @Override
    public void exportRegisterTemplate(String templateType, Long taskId, HttpServletResponse response) {
        TjTeamTask tjTeamTask = baseMapper.selectById(taskId);
        String teamName = iTjTeamInfoService.selectTeamNameById(tjTeamTask.getTeamId());
        List<TjTeamGroup> groupList = tjTeamGroupMapper.selectList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, taskId));
        if(CollUtil.isNotEmpty(groupList)) {
            List<TjTeamGroupItem> itemList = tjTeamGroupItemMapper.selectList(Wrappers.lambdaQuery(TjTeamGroupItem.class)
                .in(TjTeamGroupItem::getGroupId, StreamUtils.toList(groupList, TjTeamGroup::getId)));
            Set<Long> groupNewIdList = itemList.stream().map(TjTeamGroupItem::getGroupId).collect(Collectors.toSet());
            groupList= StreamUtils.filter(groupList,
                e -> ObjectUtil.notEqual(e.getGroupType(), GroupTypeEnum.ITEM.getCode())
                    || groupNewIdList.contains(e.getId()));
        }
        DropDownOptions options = new DropDownOptions(6, StreamUtils.toList(groupList, TjTeamGroup::getGroupName));
        List<DropDownOptions> optionsList = CollUtil.newArrayList(options);
        //如有其他类型再添加
        if(PhysicalTypeEnum.isOccupational(templateType)) {
            ExcelUtil.exportExcel(CollUtil.newArrayList(), teamName, TjTaskOccupationalExportVo.class,
                Boolean.FALSE, response, optionsList, null);
        }else {
            ExcelUtil.exportExcel(CollUtil.newArrayList(), teamName, TjTaskHealthExportVo.class,
                Boolean.FALSE, response, optionsList, null);
        }
    }

    @Override
    public ExcelResult<TjTaskOccupationalExportVo> importRegisterData(MultipartFile file, TjTaskImportBo bo)
        throws IOException {
        TjTeamTask tjTeamTask = baseMapper.selectById(bo.getTaskId());
        String teamName = iTjTeamInfoService.selectTeamNameById(tjTeamTask.getTeamId());
        bo.setTaskName(tjTeamTask.getTaskName());
        bo.setTeamName(teamName);
        return ExcelUtil.importExcel(file.getInputStream(),
            TjTaskOccupationalExportVo.class,
            new TjTaskImportListener(bo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRegisterData(TjRegisterImportBo bo) {
        TjTeamTask tjTeamTask = baseMapper.selectById(bo.getTaskId());
        //是否为职业病
        String occupationalType = PhysicalTypeEnum.isOccupational(bo.getPhysicalType()) ?
            CommonConstants.NORMAL : CommonConstants.DISABLE;
        List<TjRegisterImportDetailBo> registerList = bo.getRegisterList();
        Map<Long, List<TjTeamGroupHazardsVo>> hazardMap = MapUtil.newHashMap();
        List<Long> groupIdList = StreamUtils.toList(registerList, TjRegisterImportDetailBo::getTeamGroupId);
        if(Objects.equals(occupationalType, CommonConstants.NORMAL)) {
            if(CollUtil.isNotEmpty(groupIdList)) {
                List<TjTeamGroupHazardsVo> hazardsList = tjTeamGroupHazardsMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroupHazards.class)
                    .in(TjTeamGroupHazards::getGroupId, groupIdList));
                hazardMap.putAll(StreamUtils.groupByKey(hazardsList, TjTeamGroupHazardsVo::getGroupId));
            }
        }
        List<TjRegisterAddBo> addBoList = CollUtil.newArrayList();
        registerList.forEach(k -> {
            TjRegisterAddBo addBo = MapstructUtils.convert(k, TjRegisterAddBo.class);
            addBo.setPhysicalType(bo.getPhysicalType());
            addBo.setTaskId(bo.getTaskId());
            addBo.setTeamId(tjTeamTask.getTeamId());
            addBo.setTeamDeptId(tjTeamTask.getTeamDeptId());
            addBo.setBusinessCategory("2");
            addBo.setCredentialType(CertificateTypeEnum.身份证.getCode());
            addBo.setOccupationalType(occupationalType);
            if(Objects.equals(occupationalType, CommonConstants.NORMAL)) {
                TjRegisterZybBo occupational = MapstructUtils.convert(k, TjRegisterZybBo.class);
                addBo.setTjRegisterZybBo(occupational);
                long month = occupational.getContactSeniorityYear() * 12 + occupational.getContactSeniorityMonth();
                List<TjRegisterZybHazardBo> hazardBoList = MapstructUtils.convert(hazardMap.get(k.getTeamGroupId()),
                    TjRegisterZybHazardBo.class);
                hazardBoList.forEach(s -> s.setHazardStartDate(DateUtil.offsetMonth(
                    DateUtil.date(), -1 * Math.toIntExact(month))));
                addBo.setTjRegisterZybHazardBos(hazardBoList);
            }
            addBoList.add(addBo);
        });
        RegisterInsertService registerInsertService = registerInsertHolder.selectBuilder("2".concat(occupationalType));
        List<TjRegister> registers = registerInsertService.RegisterInsert(addBoList);
        if(CollUtil.isNotEmpty(groupIdList)) {
            List<TjTeamGroupItem> itemList = tjTeamGroupItemMapper.selectList(Wrappers.lambdaQuery(TjTeamGroupItem.class)
                .in(TjTeamGroupItem::getGroupId, groupIdList));
            Map<Long, List<TjTeamGroupItem>> itemMap = StreamUtils.groupByKey(itemList, TjTeamGroupItem::getGroupId);
            registers.forEach(k -> {
                List<TjTeamGroupItem> groupItemList = itemMap.get(k.getTeamGroupId());
                if(CollUtil.isNotEmpty(groupItemList)) {
                    List<TjRegCombinationProject> projectList = CollUtil.newArrayList();
                    groupItemList.forEach(s ->
                        projectList.add(TjRegCombinationProject.builder()
                            .registerId(k.getId())
                            .combinationProjectId(s.getItemId())
                            .projectType(s.getInclude())
                            .standardAmount(s.getStandardPrice())
                            .discount(s.getDiscount())
                            .receivableAmount(s.getActualPrice())
                            .payMode("1")
                            .projectRequiredType(s.getRequired() ? "1" : "0")
                            //.teamAmount(s.getActualPrice())
                            .build()));
                    AmountCalculationVo amountCalculationVo = tjRegisterService.billingByRegister(k, projectList,"1");
                    TjRegCombinAddBo addBo = new TjRegCombinAddBo();
                    addBo.setRegisterId(k.getId());
                    addBo.setStandardAmount(amountCalculationVo.getStandardAmount());
                    addBo.setReceivableAmount(amountCalculationVo.getReceivableAmount());
                    addBo.setPersonAmount(amountCalculationVo.getPersonAmount());
                    addBo.setDiscount(amountCalculationVo.getDiscount());
                    addBo.setTeamAmount(amountCalculationVo.getTeamAmount());
                    addBo.setPaidTotalAmount(amountCalculationVo.getPaidTotalAmount());
                    addBo.setPaidPersonAmount(amountCalculationVo.getPaidPersonAmount());
                    addBo.setPaidTeamAmount(amountCalculationVo.getPaidTeamAmount());
                    List<AmountCalculationItemVo> amountCalculationItemVos = amountCalculationVo.getAmountCalculationItemVos();
                    List<TjRegCombinItemBo> tjRegCombinItemBos = amountCalculationItemVos.stream().map(vo->{
                        TjRegCombinItemBo itemBo = new TjRegCombinItemBo();
                        itemBo.setDiscount(vo.getDiscount());
                        itemBo.setTeamAmount(vo.getTeamAmount());
                        itemBo.setPersonAmount(vo.getPersonAmount());
                        itemBo.setStandardAmount(vo.getStandardAmount());
                        itemBo.setReceivableAmount(vo.getReceivableAmount());
                        itemBo.setCombinationProjectId(vo.getCombinProjectId());
                        itemBo.setPayMode(vo.getPayType());
                        itemBo.setProjectRequiredType(vo.getProjectRequiredType());
                        itemBo.setProjectType(vo.getProjectType());
                        return itemBo;
                    }).collect(Collectors.toList());
                    //List<TjRegCombinItemBo> tjRegCombinItemBos = MapstructUtils.convert(projectList,TjRegCombinItemBo.class);
                    addBo.setTjRegCombinItemBos(tjRegCombinItemBos);
                    RegisterChangeService registerChangeService = registerChangeHolder.selectBuilder("4");//暂存
                    registerChangeService.changeRegCombin(addBo);
                }
            });

        }

    }

    @Override
    public TjTaskReviewDetailVo queryTaskReviewDetail(Long id) {
        return MapstructUtils.convert(baseMapper.selectById(id), TjTaskReviewDetailVo.class);
    }

    @Override
    public TableDataInfo<TjTaskReviewGroupVo> queryTaskReviewGroup(Long taskId, PageQuery pageQuery) {
        Page<TjTeamGroup> page = tjTeamGroupMapper.selectPage(pageQuery.build(), Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, taskId));
        List<Long> groupIds = StreamUtils.toList(page.getRecords(), TjTeamGroup::getId);
        Page<TjTaskReviewGroupVo> voPage = new Page<>();
        List<TjTaskReviewGroupVo> voList;
        if(CollUtil.isNotEmpty(groupIds)) {
            List<TjRegister> registerList = tjRegisterMapper.selectList(Wrappers.lambdaQuery(TjRegister.class)
                .in(TjRegister::getTeamGroupId, groupIds)
                .select(TjRegister::getTeamGroupId, TjRegister::getHealthyCheckStatus));
            Map<Long, Long> allMap = registerList.stream().collect(Collectors.groupingBy(TjRegister::getTeamGroupId,
                Collectors.counting()));
            Map<Long, Long> appointMap = StreamUtils.filter(registerList, e -> Objects.equals(HealthyCheckTypeEnum.预约.getCode(), e.getHealthyCheckStatus()))
                .stream().collect(Collectors.groupingBy(TjRegister::getTeamGroupId,
                Collectors.counting()));
            Map<Long, Long> checkInMap = StreamUtils.filter(registerList, e -> !Objects.equals(HealthyCheckTypeEnum.预约.getCode(), e.getHealthyCheckStatus()))
                .stream().collect(Collectors.groupingBy(TjRegister::getTeamGroupId,
                Collectors.counting()));
            voList = MapstructUtils.convert(page.getRecords(), TjTaskReviewGroupVo.class);
            voList.forEach(k -> k.setAllNum(allMap.getOrDefault(k.getId(),0L))
                .setAppointNum(appointMap.getOrDefault(k.getId(),0L))
                .setCheckInNum(checkInMap.getOrDefault(k.getId(),0L)));
            voPage.setRecords(voList);
            voPage.setTotal(page.getTotal());
        }
        return TableDataInfo.build(voPage);
    }

    @Override
    public TableDataInfo<TjTaskReviewRegisterVo> queryTaskReviewRegister(Long taskId, PageQuery pageQuery) {
        Page<TjRegister> page = tjRegisterMapper.selectPage(pageQuery.build(), Wrappers.lambdaQuery(TjRegister.class)
            .eq(TjRegister::getTaskId, taskId));
        List<TjTaskReviewRegisterVo> voList = MapstructUtils.convert(page.getRecords(), TjTaskReviewRegisterVo.class);
        List<Long> ids = StreamUtils.toList(voList, TjTaskReviewRegisterVo::getId);
        if(CollUtil.isNotEmpty(ids)) {
            List<TjRegisterZyb> zybList = tjRegisterZybMapper.selectList(Wrappers.lambdaQuery(TjRegisterZyb.class)
                .in(TjRegisterZyb::getRegId, ids));
            if(CollUtil.isNotEmpty(zybList)) {
                Map<Long, String> map = StreamUtils.toMap(zybList, TjRegisterZyb::getRegId, TjRegisterZyb::getDutyStatus);
                voList.forEach(k -> k.setDutyStatus(map.getOrDefault(k.getId(), StrUtil.EMPTY)));
            }
        }
        return TableDataInfo.build(new Page<TjTaskReviewRegisterVo>().setRecords(voList).setTotal(page.getTotal()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewTask(TjTaskReviewBo bo) {
        Long userId;
        try {
            userId = LoginHelper.getLoginUser().getUserId();
        }catch (Exception e) {
            throw new PeisException(ErrorCodeConstants.PEIS_NOT_LOGGED_IN);
        }
        if(Objects.nonNull(bo.getId())) {
            long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamTask.class).eq(TjTeamTask::getId, bo.getId())
                .eq(TjTeamTask::getReviewResult, "0"));
            if(count == 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_CAN_REVIEW);
            }
            baseMapper.updateById(new TjTeamTask().setId(bo.getId())
                .setReviewResult(bo.getReviewResult()).setReviewBy(userId));
        }else if(CollUtil.isNotEmpty(bo.getIdList())) {
            long count = baseMapper.selectCount(Wrappers.lambdaQuery(TjTeamTask.class).in(TjTeamTask::getId, bo.getIdList())
                .gt(TjTeamTask::getReviewResult, "0"));
            if(count > 0) {
                throw new PeisException(ErrorCodeConstants.PEIS_CAN_REVIEW);
            }
            bo.getIdList().forEach(k -> baseMapper.updateById(new TjTeamTask().setId(k).setReviewResult(bo.getReviewResult()).setReviewBy(userId)));
        }
    }

    @Override
    public void returnTask(List<Long> idList) {
        idList.forEach(k -> baseMapper.updateById(new TjTeamTask().setId(k).setReviewResult("0")));
    }

    @Override
    public TableDataInfo<TjTaskRegisterExportVo> queryTaskRegisterExportById(Long taskId,PageQuery pageQuery) {
        Page<TjTaskRegisterExportVo> result = tjRegisterMapper.queryTaskRegisterExportById(pageQuery.build(), taskId);
        return TableDataInfo.build(result);
    }

    @Override
    public List<TjTeamGroupVo> getTaskItemGroupInfo(Long id, String physicalType) {
        List<TjTeamGroupVo> voList = tjTeamGroupMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroup.class)
            .eq(TjTeamGroup::getTaskId, id)
            .eq(TjTeamGroup::getGroupType, GroupTypeEnum.ITEM.getCode()));
        List<Long> groupIds = StreamUtils.toList(voList, TjTeamGroupVo::getId);
        if(CollUtil.isNotEmpty(groupIds)) {
            List<TjTeamGroupItemVo> groupItemList = tjTeamGroupItemMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroupItem.class)
                .in(TjTeamGroupItem::getGroupId, groupIds));
            if(PhysicalTypeEnum.isOccupational(physicalType)) {
                List<TjTeamGroupHazardsVo> groupHazardsList = tjTeamGroupHazardsMapper.selectVoList(Wrappers.lambdaQuery(TjTeamGroupHazards.class)
                    .in(TjTeamGroupHazards::getGroupId, groupIds));
                voList.forEach(k -> k.setGroupHazardsList(StreamUtils.filter(groupHazardsList, e -> Objects.equals(e.getGroupId(),k.getId()))));
            }
            voList.forEach(k -> k.setGroupItemList(StreamUtils.filter(groupItemList, e -> Objects.equals(e.getGroupId(),k.getId()))));
        }
        return voList;
    }
}
