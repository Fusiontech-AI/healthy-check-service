package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检单位信息对象 tj_team_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_info")
@Accessors(chain = true)
public class TjTeamInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 单位级别关联sys_dict_type(sys_team_level)
     */
    private Integer teamLevel;

    /**
     * 上级单位id
     */
    private Long parentId;

    /**
     * 单位编号
     */
    private String teamNo;

    /**
     * 单位名称
     */
    private String teamName;

    /**
     * 单位简称
     */
    private String shortName;

    /**
     * 拼音简码
     */
    private String phoneticCode;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 社会信用统一代码
     */
    private String creditCode;

    /**
     * 行业类型sys_dict_type
     */
    private Integer industryType;

    /**
     * 地区编码
     */
    private String regionCode;

    /**
     * 地区编码
     */
    private Integer economicType;

    /**
     * 单位注册地址
     */
    private String registerAddress;

    /**
     * 企业规模
     */
    private Integer enterpriseSize;

    /**
     * 职工总人数
     */
    private Integer employeeTotalNum;

    /**
     * 女职工人数
     */
    private Integer femaleEmployeeNum;

    /**
     * 生产工人数
     */
    private Integer productTotalNum;

    /**
     * 生产女职工人数
     */
    private Integer femaleProductNum;

    /**
     * 接害工人数
     */
    private Integer affectedTotalNum;

    /**
     * 接害女职工人数
     */
    private Integer femaleAffectedNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
