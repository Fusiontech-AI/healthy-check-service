package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 体检套餐对象 tj_package
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_package")
public class TjPackage extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检类型
     */
    private String tjType;

    /**
     * 适用性别 0男 1女 2不限
     */
    private String suitSex;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐简称
     */
    private String packageSimpleName;

    /**
     * 拼音简码
     */
    private String pySimpleCode;

    /**
     * 组合项目排序
     */
    private Long packageSort;

    /**
     * 标准价格
     */
    private BigDecimal standardAmount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 应收金额
     */
    private BigDecimal receivableAmount;

    /**
     * 组合项目状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 照射源sys_dict_type(bus_duty_status)
     */
    private String dutyStatus;

    /**
     * 照射源sys_dict_type(bus_shine_source)
     */
    private String shineSource;

    /**
     * 照射源种类sys_dict_type(bus_job_illumination_source)
     */
    private String shineType;


}
