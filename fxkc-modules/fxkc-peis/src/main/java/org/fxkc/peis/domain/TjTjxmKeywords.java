package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检项目关键字库对象 tj_tjxm_keywords
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_tjxm_keywords")
public class TjTjxmKeywords extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 关键词编码
     */
    private String code;

    /**
     * 关键词名称
     */
    private String name;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 关键词类型ID
     */
    private String keyTypeId;

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
