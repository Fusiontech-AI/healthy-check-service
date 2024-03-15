package org.fxkc.peis.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import org.fxkc.peis.domain.TjRecordLog;

/**
 * 体检操作记录日志业务对象 tj_record_log
 *
 * @author JunBaiChen
 * @date 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjRecordLog.class, reverseConvertGenerate = false)
public class TjRecordLogBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检号
     */
    @NotBlank(message = "体检号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String healthyCheckCode;

    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String credentialNumber;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 操作类型
     */
    @NotBlank(message = "操作类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String operType;

    /**
     * 操作描述
     */
    @NotBlank(message = "操作描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String operDesc;


    /**
     * 创建人名称
     */
    private String createName;
}
