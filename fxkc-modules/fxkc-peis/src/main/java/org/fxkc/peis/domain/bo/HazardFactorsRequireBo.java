package org.fxkc.peis.domain.bo;

import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.HazardFactorsRequire;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 危害因素必检项目主业务对象 hazard_factors_require
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@AutoMapper(target = HazardFactorsRequire.class, reverseConvertGenerate = false)
public class HazardFactorsRequireBo {

    /**
     * 危害因素名称
     */
    private String hazardFactorsName;

    /**
     * 危害因素编码
     */
    @NotBlank(message = "危害因素编码不能为空")
    private String hazardFactorsCode;

    /**
     * 在岗状态(对应字典sys_dict_item的value)
     */
    private String dutyStatus;

    /**
     * "关联类型(1:必检项目2:目标类型3:职业禁忌症4:症状询问重点5:体格检查6:检查周期7:评价标准)
     */
    @NotBlank(message = "关联类型不能为空")
    private String associationType;


}
