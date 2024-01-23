package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 职业病字典对象 tj_occupational_dict
 *
 * @author JunBaiChen
 * @date 2024-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_occupational_dict")
public class TjOccupationalDict extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 编号
     */
    private String code;

    /**
     * 值
     */
    private String value;

    /**
     * 分类
     */
    private String sort;

    /**
     * 分类编号
     */
    private String sortCode;

    /**
     * 职业照射种类
     */
    private String shineSource;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
