package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjArchives;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检档案视图对象 tj_archives
 *
 * @author JunBaiChen
 * @date 2024-02-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjArchives.class)
public class TjArchivesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 体检档案号
     */
    @ExcelProperty(value = "体检档案号")
    private String archivesNo;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 证件类型（0：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡）,见字典bus_credential_type
     */
    @ExcelProperty(value = "证件类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=：身份证，1：驾驶证，2：军官证，3：市民卡，4：学生卡，5：香港身份证，6：港澳通行证，7：台湾通行证，8：护照，9：澳门通行证，10：台湾通行证，11：电子健康卡")
    private String credentialType;

    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号")
    private String credentialNumber;


}
