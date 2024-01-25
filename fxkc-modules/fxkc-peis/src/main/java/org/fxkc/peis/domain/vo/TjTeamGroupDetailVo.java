package org.fxkc.peis.domain.vo;


import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.domain.bo.TjTeamGroupHazardsBo;
import org.fxkc.peis.domain.bo.TjTeamGroupItemBo;

import java.math.BigDecimal;
import java.util.List;

@Data
@AutoMapper(target = TjTeamGroup.class)
public class TjTeamGroupDetailVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 分组方式sys_dict_type(bus_group_type)
     */
    private Integer groupType;

    /**
     * 项目折扣
     */
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    private BigDecimal addDiscount;

    /**
     * 是否同步项目(0:是1:否)
     */
    private String isSyncProject;

    /**
     * 套餐id
     */
    private Long packageId;

    /**
     * 分组项目信息集合
     */
    private List<TjTeamGroupItemVo> groupItemList;

    /**
     * 危害因素集合
     */
    private List<TjTeamGroupHazardsVo> groupHazardsList;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;
}
