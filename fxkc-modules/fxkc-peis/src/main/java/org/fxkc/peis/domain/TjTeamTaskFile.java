package org.fxkc.peis.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fxkc.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 体检单位任务文件对象 tj_team_task_file
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tj_team_task_file")
public class TjTeamTaskFile extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 文件类型1:职业健康检查委托协议书2:体检方案3:职业健康检查表
     */
    private String fileType;

    /**
     * 上传文件名
     */
    private String fileName;

    /**
     * 原文件名
     */
    private String originalName;

    /**
     * 后缀类型
     */
    private String suffixType;

    /**
     * 文件大小(单位字节)
     */
    private Long fileSize;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;


}
