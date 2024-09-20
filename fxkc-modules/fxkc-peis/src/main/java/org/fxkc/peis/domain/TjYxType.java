package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检阳性分类对象 tj_yx_type
 *
 * @author JunBaiChen
 * @date 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_yx_type")
public class TjYxType extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 类别序号
     */
    private Integer sort;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
