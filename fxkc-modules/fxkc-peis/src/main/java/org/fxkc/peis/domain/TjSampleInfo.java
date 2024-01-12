package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检样本配置信息对象 tj_sample_info
 *
 * @author JunBaiChen
 * @date 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_sample_info")
public class TjSampleInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 样本主键id
     */
    private Long sampleId;

    /**
     * 组合项目主键id
     */
    private Long combinProjectId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
