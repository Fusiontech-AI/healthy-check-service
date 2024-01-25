package org.fxkc.peis.domain.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjTeamTaskFile;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 体检单位任务文件业务对象 tj_team_task_file
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjTeamTaskFile.class, reverseConvertGenerate = false)
public class TjTeamTaskFileBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 任务id
     */
    @NotNull(message = "任务id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long taskId;

    /**
     * 文件类型1:职业健康检查委托协议书2:体检方案3:职业健康检查表
     */
    @NotBlank(message = "文件类型1:职业健康检查委托协议书2:体检方案3:职业健康检查表不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileType;


    /**
     * 上传的导检单文件
     */
    @NotNull(message = "上传文件不能为空",groups = { AddGroup.class, EditGroup.class })
    private MultipartFile file;
}
