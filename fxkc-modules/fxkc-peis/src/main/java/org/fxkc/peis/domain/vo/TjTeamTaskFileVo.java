package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTeamTaskFile;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检单位任务文件视图对象 tj_team_task_file
 *
 * @author JunBaiChen
 * @date 2024-01-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamTaskFile.class)
public class TjTeamTaskFileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 任务id
     */
    @ExcelProperty(value = "任务id")
    private Long taskId;

    /**
     * 文件类型1:职业健康检查委托协议书2:体检方案3:职业健康检查表
     */
    @ExcelProperty(value = "文件类型1:职业健康检查委托协议书2:体检方案3:职业健康检查表")
    private String fileType;

    /**
     * 上传文件名
     */
    @ExcelProperty(value = "上传文件名")
    private String fileName;

    /**
     * 原文件名
     */
    @ExcelProperty(value = "原文件名")
    private String originalName;

    /**
     * 后缀类型
     */
    @ExcelProperty(value = "后缀类型")
    private String suffixType;

    /**
     * 文件大小(单位字节)
     */
    @ExcelProperty(value = "文件大小(单位字节)")
    private Long fileSize;


}
