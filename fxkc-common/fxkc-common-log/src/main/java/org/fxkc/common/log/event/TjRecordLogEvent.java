package org.fxkc.common.log.event;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 体检操作记录日志事件
 *
 * @author Lion Li
 */

@Data
@Builder
public class TjRecordLogEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;

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

}
