package org.fxkc.peis.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.utils.SpringUtils;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.core.utils.ValidatorUtils;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.excel.core.ExcelListener;
import org.fxkc.common.excel.core.ExcelResult;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.bo.TjTaskImportBo;
import org.fxkc.peis.domain.vo.TjTaskOccupationalExportVo;
import org.fxkc.peis.enums.PhysicalTypeEnum;
import org.fxkc.peis.service.ITjTeamGroupService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 系统用户自定义导入
 *
 * @author Lion Li
 */
@Slf4j
public class TjTaskImportListener extends AnalysisEventListener<TjTaskOccupationalExportVo> implements ExcelListener<TjTaskOccupationalExportVo> {

    private final TjTaskImportBo tjTaskImportBo;

    private final ITjTeamGroupService iTjTeamGroupService;

    private int index = 1;

    private final Set<String> idCardSet = CollUtil.newHashSet();

    private final static List<String> otherJobCode = Arrays.asList("99-990", "99-9999", "00-14", "00-33");

    private final List<TjTaskOccupationalExportVo> successList = CollUtil.newArrayList();

    private final List<String> errorList = CollUtil.newArrayList();
    public TjTaskImportListener(TjTaskImportBo tjTaskImportBo) {
        this.tjTaskImportBo = tjTaskImportBo;
        this.iTjTeamGroupService = SpringUtils.getBean(ITjTeamGroupService.class);
    }

    @Override
    public void invoke(TjTaskOccupationalExportVo tjTaskOccupationalExportVo, AnalysisContext context) {
        log.info("解析导入数据======={}", JSONUtil.toJsonStr(tjTaskOccupationalExportVo));
        int i = context.readRowHolder().getRowIndex();
        Boolean isOccupational = PhysicalTypeEnum.isOccupational(tjTaskImportBo.getTemplateType());
        Boolean autoGroup = tjTaskImportBo.getAutoGroup();
        String errorMsg;
        StringBuilder failureMsg = new StringBuilder();
        if(isOccupational) {
            errorMsg = ValidatorUtils.validateMsg(tjTaskOccupationalExportVo, EditGroup.class);
        }else {
            errorMsg = ValidatorUtils.validateMsg(tjTaskOccupationalExportVo, AddGroup.class);
        }
        if(StrUtil.isNotBlank(errorMsg)) {
            failureMsg.append(errorMsg).append(StrUtil.COMMA);
        }
        if(StrUtil.isNotBlank(tjTaskOccupationalExportVo.getCredentialNumber()) &&
            !IdcardUtil.isValidCard(tjTaskOccupationalExportVo.getCredentialNumber())) {
           failureMsg.append("身份证有误,");
        }
        if(StrUtil.isNotBlank(tjTaskOccupationalExportVo.getPhone()) && tjTaskOccupationalExportVo.getPhone().length() != 11) {
            failureMsg.append("联系电话不是11位,");
        }
        if(!autoGroup && StrUtil.isNotBlank(tjTaskOccupationalExportVo.getGroupName())) {
            TjTeamGroup group = iTjTeamGroupService.getOne(Wrappers.lambdaQuery(TjTeamGroup.class)
                .eq(TjTeamGroup::getTaskId, tjTaskImportBo.getTaskId())
                .eq(TjTeamGroup::getGroupName, tjTaskOccupationalExportVo.getGroupName()));
            if(Objects.isNull(group)) {
                failureMsg.append("所选分组不存在或已删除,");
            }else {
                tjTaskOccupationalExportVo.setDutyStatus(group.getDutyStatus());
                tjTaskOccupationalExportVo.setTeamGroupId(group.getId());
                tjTaskOccupationalExportVo.setIlluminationSource(group.getShineSource());
                tjTaskOccupationalExportVo.setJobIlluminationType(group.getShineType());
            }
        }
        if(isOccupational &&  otherJobCode.contains(tjTaskOccupationalExportVo.getJobCode())
            && StrUtil.isBlank(tjTaskOccupationalExportVo.getOtherJobName())) {
            failureMsg.append("工种为其他时其他工种名称不能为空,");
        }
        if(isOccupational && Objects.nonNull(tjTaskOccupationalExportVo.getSeniorityYear())
            && Objects.nonNull(tjTaskOccupationalExportVo.getSeniorityMonth())
            && Objects.nonNull(tjTaskOccupationalExportVo.getContactSeniorityYear())
            && Objects.nonNull(tjTaskOccupationalExportVo.getContactSeniorityMonth())) {
            long seniorityMonth = tjTaskOccupationalExportVo.getSeniorityYear() * 12 + tjTaskOccupationalExportVo.getSeniorityMonth();
            long contactSeniorityMonth = tjTaskOccupationalExportVo.getContactSeniorityYear() * 12 + tjTaskOccupationalExportVo.getContactSeniorityMonth();
            if(seniorityMonth < contactSeniorityMonth) {
                failureMsg.append("总工龄年月不能小于接害工龄月,");
            }
        }
        if(idCardSet.contains(tjTaskOccupationalExportVo.getCredentialNumber())) {
            failureMsg.append("身份证").append(tjTaskOccupationalExportVo.getCredentialNumber()).append("重复,");
        }
        if(StrUtil.isNotBlank(tjTaskOccupationalExportVo.getCredentialNumber())) {
            idCardSet.add(tjTaskOccupationalExportVo.getCredentialNumber());
        }
        if(failureMsg.length() > 0) {
            failureMsg.insert(0, index + ".第" + i + "行").deleteCharAt(failureMsg.length() - 1);
            errorList.add(failureMsg.toString());
            index++;
        }else {
            if(autoGroup) {
                List<TjTeamGroup> groupList = iTjTeamGroupService.list(Wrappers.lambdaQuery(TjTeamGroup.class)
                    .eq(TjTeamGroup::getTaskId, tjTaskImportBo.getTaskId()));
                Integer gender = IdcardUtil.getGenderByIdCard(tjTaskOccupationalExportVo.getCredentialNumber()) == 1 ? 0 : 1 ;
                Integer age = IdcardUtil.getAgeByIdCard(tjTaskOccupationalExportVo.getCredentialNumber());
                TjTeamGroup group;
                List<TjTeamGroup> conformList = StreamUtils.filter(groupList, e -> Objects.nonNull(e.getStartAge()) && Objects.nonNull(e.getEndAge())
                    && (Objects.equals(String.valueOf(gender), e.getGender()) || Objects.equals("2", e.getGender()))
                    && rangeInDefined(age, e.getStartAge(), e.getEndAge()));
                if(StrUtil.isNotBlank(tjTaskOccupationalExportVo.getMarriageStatus())) {
                    conformList = StreamUtils.filter(conformList, e -> Objects.equals(tjTaskOccupationalExportVo.getMarriageStatus(), e.getMarriage())
                      || Objects.equals("2",  e.getMarriage()));
                }
                if(CollUtil.isNotEmpty(conformList)) {
                    group = conformList.get(ThreadLocalRandom.current().nextInt(conformList.size()));
                }else {
                    group = groupList.get(ThreadLocalRandom.current().nextInt(groupList.size()));
                }
                tjTaskOccupationalExportVo.setDutyStatus(group.getDutyStatus());
                tjTaskOccupationalExportVo.setGroupName(group.getGroupName());
                tjTaskOccupationalExportVo.setTeamGroupId(group.getId());
                tjTaskOccupationalExportVo.setIlluminationSource(group.getShineSource());
                tjTaskOccupationalExportVo.setJobIlluminationType(group.getShineType());
            }
            tjTaskOccupationalExportVo.setBirthday(IdcardUtil.getBirthDate(tjTaskOccupationalExportVo.getCredentialNumber()));
            tjTaskOccupationalExportVo.setAddTime(tjTaskImportBo.getTime());
            tjTaskOccupationalExportVo.setTeamName(tjTaskImportBo.getTeamName());
            tjTaskOccupationalExportVo.setTaskName(tjTaskImportBo.getTaskName());
            successList.add(tjTaskOccupationalExportVo);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //todo 数据库操作
    }

    @Override
    public ExcelResult<TjTaskOccupationalExportVo> getExcelResult() {
        return new ExcelResult<TjTaskOccupationalExportVo>() {
            @Override
            public String getAnalysis() {
                return null;
            }

            @Override
            public List<TjTaskOccupationalExportVo> getList() {
                return successList;
            }

            @Override
            public List<String> getErrorList() {
                return errorList;
            }
        };
    }

    private Boolean rangeInDefined(int current, int min, int max) {
        return Math.max(min, current) == Math.min(current, max);
    }
}
