package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检操作记录日志对象 tj_record_log
 *
 * @author JunBaiChen
 * @date 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_record_log")
public class TjRecordLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检号
     */
    private String healthyCheckCode;

    /**
     * 证件号
     */
    private String credentialNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 操作类型
     */
    private String operType;

    /**
     * 操作描述
     */
    private String operDesc;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
