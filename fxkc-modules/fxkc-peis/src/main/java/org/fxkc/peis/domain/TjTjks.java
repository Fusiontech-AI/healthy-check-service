package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检科室对象 tj_tjks
 *
 * @author JunBaiChen
 * @date 2024-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_tjks")
public class TjTjks extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 科室编码
     */
    private String ksCode;

    /**
     * 科室名称
     */
    private String ksName;

    /**
     * 科室简拼
     */
    private String ksSimplePy;

    /**
     * 是否打印条码 0是 1否
     */
    private String printFlag;

    /**
     * 科室显示序号
     */
    private Long ksSort;

    /**
     * 科室状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
