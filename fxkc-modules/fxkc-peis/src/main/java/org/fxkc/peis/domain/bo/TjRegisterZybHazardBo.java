package org.fxkc.peis.domain.bo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRegisterZybHazard;

import java.util.Date;

/**
 * 体检登记职业病危害因素业务对象 tj_register_zyb_hazard
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRegisterZybHazard.class, reverseConvertGenerate = false)
public class TjRegisterZybHazardBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检登记id
     */
    @NotNull(message = "体检登记id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regId;

    /**
     * 危害因素编码
     */
    @NotBlank(message = "危害因素编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactor;

    /**
     * 危害因素名称
     */
    @NotBlank(message = "危害因素名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorName;

    /**
     * 危害因素其他名称
     */
    @NotBlank(message = "危害因素其他名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hazardFactorOther;

    /**
     * 开始接害日期,条件必填，体检类型为岗前体检该项非必填
     */
    @NotNull(message = "开始接害日期,条件必填，体检类型为岗前体检该项非必填不能为空", groups = { AddGroup.class, EditGroup.class })
    @JsonFormat(pattern = DatePattern.PURE_DATE_PATTERN)
    private Date hazardStartDate;

    /**
     * 接触所监测危害因素工龄年,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月
     */
    @NotNull(message = "接触所监测危害因素工龄年,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long hazardYear;

    /**
     * 接触所监测危害因素工龄月,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月
     */
    @NotNull(message = "接触所监测危害因素工龄月,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long hazardMonth;


}
