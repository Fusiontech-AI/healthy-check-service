package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.peis.domain.TjTeamGroup;

import java.math.BigDecimal;
import java.util.List;

/**
 * 团检分组信息业务对象 tj_team_group
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@AutoMapper(target = TjTeamGroup.class)
public class TjTeamGroupUpdateBo {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空")
    private Long id;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    @NotBlank(message = "分组方式不能为空")
    private String groupType;

    /**
     * 项目折扣
     */
    @NotNull(message = "项目折扣不能为空")
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    @NotNull(message = "加项折扣不能为空")
    private BigDecimal addDiscount;

    /**
     * 实际折扣
     */
    private BigDecimal discount;

    /**
     * 是否同步项目(0:是1:否)
     */
    @NotBlank(message = "是否同步项目")
    private String isSyncProject;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 分组项目信息集合
     */
    @Valid
    @NotEmpty(message = "分组项目信息集合不能为空")
    private List<TjTeamGroupItemBo> groupItemList;

    /**
     * 危害因素集合
     */
    private List<TjTeamGroupHazardsBo> groupHazardsList;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;

    /**
     * 标准价格
     */
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

}
