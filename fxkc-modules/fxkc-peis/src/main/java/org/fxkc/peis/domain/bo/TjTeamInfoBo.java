package org.fxkc.peis.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamInfo;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 体检单位信息业务对象 tj_team_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamInfo.class, reverseConvertGenerate = false)
public class TjTeamInfoBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 单位级别关联sys_dict_type(sys_team_level)
     */
    @NotNull(message = "单位级别", groups = {AddGroup.class, EditGroup.class})
    private Integer teamLevel;

    /**
     * 上级单位id
     */
    private Long parentId;

    /**
     * 单位编号
     */
    @NotBlank(message = "单位编号", groups = {AddGroup.class, EditGroup.class})
    private String teamNo;

    /**
     * 单位名称
     */
    @NotBlank(message = "单位名称", groups = {AddGroup.class, EditGroup.class})
    private String teamName;

    /**
     * 单位简称
     */
    private String shortName;

    /**
     * 拼音简码
     */
    @NotBlank(message = "拼音简码", groups = {AddGroup.class, EditGroup.class})
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


}
