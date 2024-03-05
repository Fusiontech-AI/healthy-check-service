package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import org.fxkc.common.translation.annotation.Translation;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.peis.domain.TjRegister;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 体检人员分页列表vo
 * @author: zry
 * @date: 2024-01-25 09:18
 **/
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRegister.class)
public class TjRegisterPageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 体检号
     */
    @ExcelProperty(value = "体检号")
    private String healthyCheckCode;

    /**
     * 档案号
     */
    @ExcelProperty(value = "档案号")
    private String recordCode;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @ExcelProperty(value = "证件类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_credential_type")
    private String credentialType;

    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号")
    private String credentialNumber;

    /**
     * 业务类别（1：个检，2：团检）见字典bus_category
     */
    @ExcelProperty(value = "业务类别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_category")
    private String businessCategory;

    /**
     * 体检类型，见字典bus_physical_type
     */
    @ExcelProperty(value = "体检类型，见字典bus_physical_type", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_physical_type")
    private String physicalType;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 婚姻状况（0：未婚，1：已婚，2：未知）,见字典bus_personnel_marriage_status
     */
    @ExcelProperty(value = "婚姻状况", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_marriage_status")
    private String marriageStatus;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * 性别（0：男，1：女，2：未知）,见字典sys_user_sex
     */
    @ExcelProperty(value = "性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String gender;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String phone;

    /**
     * 体检日期
     */
    @ExcelProperty(value = "体检日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date healthyCheckTime;

    /**
     * 是否回收指引单（0：是，1：否）
     */
    @ExcelProperty(value = "是否回收指引单", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：是，1：否")
    private String guideSheetReceived;

    /**
     * 是否冻结（0：是，1：否）
     */
    @ExcelProperty(value = "是否冻结", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：是，1：否")
    private String freezeStatus;

    /**
     * 体检状态（0：预约，1：登记，2：科室分检，3：分检完成，4：待总检，5：已终检）见字典bus_healthy_check_status
     */
    @ExcelProperty(value = "体检状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_healthy_check_status")
    private String healthyCheckStatus;

    /**
     * 是否需要总检（0：需要总检，1：无需总检）见字典bus_need_general_review
     */
    @ExcelProperty(value = "是否需要总检", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_need_general_review")
    private String needGeneralReview;

    /**
     * 费别,字典类型bus_cost_type
     */
    @ExcelProperty(value = "费别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "bus_cost_type")
    private String costType;

    /**
     * 总费用
     */
    @ExcelProperty(value = "总费用")
    private BigDecimal totalAmount;

    /**
     * 单位费用
     */
    @ExcelProperty(value = "单位费用")
    private BigDecimal teamAmount;

    /**
     * 个人费用
     */
    @ExcelProperty(value = "个人费用")
    private BigDecimal personAmount;



    /**
     * 单位id
     */
    private Long teamId;

    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位")
    @Translation(type=TransConstant.TEAM_ID_TO_NAME, mapper = "teamId")
    private String teamName;

    /**
     * 单位分组id
     */
    private Long teamGroupId;

    /**
     * 单位分组名称
     */
    @ExcelProperty(value = "分组")
    @Translation(type = TransConstant.TEAM_GROUP_ID_TO_NAME,mapper = "teamGroupId")
    private String groupName;

    /**
     * 单位部门id
     */
    private Long teamDeptId;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    @Translation(type = TransConstant.TEAM_DEPT_ID_TO_NAME, mapper = "teamDeptId")
    private String deptName;

    /**
     * 介绍人
     */

    private Long introducer;

    /**
     * 介绍人名称
     */
    @ExcelProperty(value = "介绍人")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "introducer")
    private String introducerName;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    @ExcelProperty(value = "创建人")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "createBy")
    private String createByName;

    /**
     * 替检标志 0是 1否
     */
    @ExcelProperty(value = "替检标志 0是 1否")
    private String replaceFlag;

    /**
     * 体检登记(报道)医生id
     */
    private Long registerDoctorId;

    /**
     * 报道人名称
     */
    @ExcelProperty(value = "体检登记(报道)医生")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "registerDoctorId")
    private String registerDoctorName;

    /**
     * 总检医生
     */
    private Long generalReviewDoctor;

    /**
     * 总检医生名称
     */
    @ExcelProperty(value = "总检医生")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "generalReviewDoctor")
    private String generalReviewDoctorName;

    /**
     * 总检时间
     */
    @ExcelProperty(value = "总检时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date generalReviewTime;

    /**
     * 审核医生
     */
    private Long auditDoctor;

    /**
     * 审核医生名称
     */
    @ExcelProperty(value = "审核医生")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "auditDoctor")
    private String auditDoctorName;

    /**
     * 审核时间
     */
    @ExcelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishTime;

    /**
     * 取消登记时间
     */
    @ExcelProperty(value = "取消登记时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cancelRegisterTime;

    /**
     * 取消登记操作人
     */
    private Long cancelRegisterOperator;

    /**
     * 取消登记操作人姓名
     */
    @ExcelProperty(value = "取消登记操作人")
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "cancelRegisterOperator")
    private String cancelRegisterOperatorName;

    /**
     * 体检检查类型11初检 21复检,见字典bus_tj_check_type
     */
    @ExcelProperty(value = "体检检查类型11初检 21复检,见字典bus_tj_check_type")
    private String checkType;

    /**
     * 单位结账时间
     */
    @ExcelProperty(value = "单位结账时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date teamSettleTime;

    /**
     * 已缴费
     */
    private BigDecimal paidTotalAmount;

    /**
     * 未缴费
     */
    private BigDecimal unPaidTotalAmount;

    /**
     * 单位任务id
     */
    private Long taskId;

    /**
     * 单位任务名称
     */
    @ExcelProperty(value = "单位任务名称")
    @Translation(type = TransConstant.TEAM_TASK_ID_TO_NAME,mapper = "taskId")
    private String taskName;

    /**
     * 体检次数
     */
    private Long peTimes;

    /**
     * 旧档案号
     */
    private String oldRecordCode;

    /**
     * 合并档案人
     */
    private Long mergeRecordBy;

    /**
     * 合并档案人名称
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME,mapper = "mergeRecordBy")
    private String mergeRecordByName;

    /**
     * 合并档案时间
     */
    private Date mergeRecordTime;

}
