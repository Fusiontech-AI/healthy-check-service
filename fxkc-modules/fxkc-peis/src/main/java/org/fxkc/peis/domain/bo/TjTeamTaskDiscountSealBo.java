package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjTeamTask;

import java.math.BigDecimal;

/**
 * 体检单位结账业务对象 tj_team_settle
 *
 * @author JunBaiChen
 * @date 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamTask.class, reverseConvertGenerate = false)
public class TjTeamTaskDiscountSealBo extends BaseEntity {

    /**
     * 单位ID
     */
    @NotNull(message = "单位ID不能为空", groups = { EditGroup.class })
    private Long teamId;

    /**
     * 单位任务ID
     */
    @NotNull(message = "单位任务ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 任务折扣实收金额
     */
    @DecimalMin(value = "0", inclusive = false, message = "实收金额不能小于0")
    private BigDecimal taskReceived;

    /**
     * 任务折扣优惠金额
     */
    private BigDecimal taskDiscount;

}
