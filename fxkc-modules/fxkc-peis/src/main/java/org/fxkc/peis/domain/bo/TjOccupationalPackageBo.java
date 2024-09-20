package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.fxkc.common.mybatis.core.page.PageQuery;

import java.util.List;

@Data
public class TjOccupationalPackageBo extends PageQuery {

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 体检类型sys_dict_type(bus_physical_type)
     */
    @NotBlank(message = "体检类型不能为空")
    private String physicalType;

    /**
     * 在岗状态sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

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
}
