package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检样本对象 tj_sample
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_sample")
public class TjSample extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 样本编码
     */
    private String sampleCode;

    /**
     * 样本名称
     */
    private String sampleName;

    /**
     * 样本类别
     */
    private String sampleCategory;

    /**
     * 样本类型
     */
    private String sampleType;

    /**
     * 条码类型
     */
    private String barCodeType;

    /**
     * 打印顺序
     */
    private Long printSort;

    /**
     * 打印份数
     */
    private Long printNumber;

    /**
     * 是否打印 0是 1否
     */
    private String printFlag;

    /**
     * 打印申请单 0打印 1不打印
     */
    private String printApplyFlag;

    /**
     * 申请单份数
     */
    private Long printApplyNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
