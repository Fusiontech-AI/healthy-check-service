package org.fxkc.peis.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.fxkc.peis.domain.TjRecordLog;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检操作记录日志视图对象 tj_record_log
 *
 * @author JunBaiChen
 * @date 2024-03-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjRecordLog.class)
public class TjRecordLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检号
     */
    @ExcelProperty(value = "体检号")
    private String healthyCheckCode;

    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号")
    private String credentialNumber;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    private String operType;

    /**
     * 操作描述
     */
    @ExcelProperty(value = "操作描述")
    private String operDesc;

    /**
     * 创建人名称
     */
    private String createName;
}
