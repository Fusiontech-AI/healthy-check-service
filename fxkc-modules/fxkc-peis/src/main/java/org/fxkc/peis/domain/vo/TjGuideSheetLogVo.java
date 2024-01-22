package org.fxkc.peis.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.fxkc.peis.domain.TjGuideSheetLog;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 导检单回收记录视图对象 tj_guide_sheet_log
 *
 * @author JunBaiChen
 * @date 2024-01-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjGuideSheetLog.class)
public class TjGuideSheetLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检人员id
     */
    @ExcelProperty(value = "体检人员id")
    private Long registerId;

    /**
     * 导检单存储地址
     */
    @ExcelProperty(value = "导检单存储地址")
    private String imagePath;

    /**
     * 上传时间
     */
    @ExcelProperty(value = "上传时间")
    private Date uploadTime;


}
