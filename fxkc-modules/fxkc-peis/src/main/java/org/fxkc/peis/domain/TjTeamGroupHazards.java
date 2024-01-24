package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 单位分组危害因素对象 tj_team_group_hazards
 *
 * @author JunBaiChen
 * @date 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_group_hazards")
public class TjTeamGroupHazards extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 危害因素编码
     */
    private String hazardFactorsCode;

    /**
     * 危害因素名称
     */
    private String hazardFactorsName;

    /**
     * 其他危害因素名称
     */
    private String hazardFactorsOtherName;


}
