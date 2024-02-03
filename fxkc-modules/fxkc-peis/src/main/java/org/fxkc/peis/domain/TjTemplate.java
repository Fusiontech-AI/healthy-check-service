package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检报告维护对象 tj_template
 *
 * @author JunBaiChen
 * @date 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_template")
public class TjTemplate extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 模板ID
     */
    private String templateInfoId;

    /**
     * 体检类型
     */
    private String physicalType;

}
