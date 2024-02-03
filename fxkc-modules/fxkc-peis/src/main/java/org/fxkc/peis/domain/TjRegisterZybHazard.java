package org.fxkc.peis.domain;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.Date;

/**
 * 体检登记职业病危害因素对象 tj_register_zyb_hazard
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_register_zyb_hazard")
public class TjRegisterZybHazard extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检登记id
     */
    private Long regId;

    /**
     * 危害因素编码
     */
    private String hazardFactor;

    /**
     * 危害因素名称
     */
    private String hazardFactorName;

    /**
     * 危害因素其他名称
     */
    private String hazardFactorOther;

    /**
     * 开始接害日期,条件必填，体检类型为岗前体检该项非必填
     */
    @JsonFormat(pattern = DatePattern.PURE_DATE_PATTERN)
    private Date hazardStartDate;

    /**
     * 接触所监测危害因素工龄年,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月
     */
    private Long hazardYear;

    /**
     * 接触所监测危害因素工龄月,必填，接触所监测危害因素工龄年月 ≤ 接触工龄年月
     */
    private Long hazardMonth;


}
