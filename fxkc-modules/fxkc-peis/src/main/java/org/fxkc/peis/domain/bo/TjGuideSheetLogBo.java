package org.fxkc.peis.domain.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.fxkc.common.core.validate.AddGroup;
import org.fxkc.common.core.validate.EditGroup;
import org.fxkc.peis.domain.TjGuideSheetLog;
import org.fxkc.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 * 导检单回收记录业务对象 tj_guide_sheet_log
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TjGuideSheetLog.class, reverseConvertGenerate = false)
public class TjGuideSheetLogBo extends BaseEntity {

    /**
     * 主键id
     */
    @NotNull(message = "主键id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 体检人员id
     */
    @NotNull(message = "体检人员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long registerId;

    private String imagePath;

//    /**
//     * 上传的导检单文件
//     */
//    @NotNull(message = "上传文件不能为空",groups = {AddGroup.class})
//    @JsonIgnore
//    private MultipartFile file;


    /**
     * 上传时间
     */
    @NotNull(message = "上传时间不能为空", groups = { AddGroup.class, EditGroup.class })
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

    /**
     * 是否职业病问询单(0：是，1：否)
     */
    @NotBlank(message = "是否职业病问询单(0：是，1：否)",groups = {AddGroup.class,EditGroup.class})
    private String occupationalType;

}
