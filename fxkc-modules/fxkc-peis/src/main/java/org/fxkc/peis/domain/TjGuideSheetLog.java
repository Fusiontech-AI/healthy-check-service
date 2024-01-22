package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 导检单回收记录对象 tj_guide_sheet_log
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_guide_sheet_log")
public class TjGuideSheetLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 体检人员id
     */
    private Long registerId;

    /**
     * 导检单存储地址
     */
    private String imagePath;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
