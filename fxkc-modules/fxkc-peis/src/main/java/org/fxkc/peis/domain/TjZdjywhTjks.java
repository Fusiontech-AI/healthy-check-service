package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 诊断建议和科室关联对象 tj_zdjywh_tjks
 *
 * @author JunBaiChen
 * @date 2024-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_zdjywh_tjks")
public class TjZdjywhTjks extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检科室id
     */
    private Long ksId;

    /**
     * 诊断建议id
     */
    private Long zdjyId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
