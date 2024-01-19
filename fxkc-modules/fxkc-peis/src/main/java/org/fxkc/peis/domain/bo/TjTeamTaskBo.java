package org.fxkc.peis.domain.bo;

import cn.hutool.core.date.DatePattern;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamTask;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 团检任务管理业务对象 tj_team_task
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamTask.class, reverseConvertGenerate = false)
public class TjTeamTaskBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 单位id
     */
    @NotNull(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teamId;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String taskName;

    /**
     * 体检类型sys_dict_type(bus_physical_type)
     */
    @NotBlank(message = "体检类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String physicalType;

    /**
     * 签订日期
     */
    @NotNull(message = "签订日期不能为空", groups = { AddGroup.class, EditGroup.class })
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date signDate;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date endDate;

    /**
     * 收费类型sys_dict_type(bus_charge_type)
     */
    @NotNull(message = "收费类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer chargeType;

    /**
     * 是否审核(1:是0:否)
     */
    @NotBlank(message = "是否审核不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isReview;

    /**
     * 分组信息
     */
    @NotEmpty(message = "分组信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private List<TjTeamGroupBo> groupList;
}
