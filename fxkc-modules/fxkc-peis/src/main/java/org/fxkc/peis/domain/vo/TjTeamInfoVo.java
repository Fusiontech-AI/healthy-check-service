package org.fxkc.peis.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.fxkc.peis.domain.TjTeamInfo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 体检单位信息视图对象 tj_team_info
 *
 * @author JunBaiChen
 * @date 2024-01-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamInfo.class)
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TjTeamInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 单位级别关联sys_dict_type(sys_team_level)
     */
    @ExcelProperty(value = "单位级别关联sys_dict_type(sys_team_level)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_team_level")
    private Integer teamLevel;

    /**
     * 上级单位id
     */
    @ExcelProperty(value = "上级单位id")
    private Long parentId;

    /**
     * 单位编号
     */
    @ExcelProperty(value = "单位编号")
    private String teamNo;

    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称")
    private String teamName;

    /**
     * 单位简称
     */
    @ExcelProperty(value = "单位简称")
    private String shortName;

    /**
     * 拼音简码
     */
    @ExcelProperty(value = "拼音简码")
    private String phoneticCode;

    /**
     * 联系人电话
     */
    @ExcelProperty(value = "联系人电话")
    private String contactPhone;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人姓名")
    private String contactName;

    /**
     * 社会信用统一代码
     */
    @ExcelProperty(value = "社会信用统一代码")
    private String creditCode;

    /**
     * 行业类型sys_dict_type
     */
    @ExcelProperty(value = "行业类型sys_dict_type")
    private Integer industryType;

    /**
     * 地区编码
     */
    @ExcelProperty(value = "地区编码")
    private String regionCode;

    /**
     * 地区编码
     */
    @ExcelProperty(value = "地区编码")
    private Integer economicType;

    /**
     * 单位注册地址
     */
    @ExcelProperty(value = "单位注册地址")
    private String registerAddress;

    /**
     * 企业规模
     */
    @ExcelProperty(value = "企业规模")
    private Integer enterpriseSize;

    /**
     * 职工总人数
     */
    @ExcelProperty(value = "职工总人数")
    private Integer employeeTotalNum;

    /**
     * 女职工人数
     */
    @ExcelProperty(value = "女职工人数")
    private Integer femaleEmployeeNum;

    /**
     * 生产工人数
     */
    @ExcelProperty(value = "生产工人数")
    private Integer productTotalNum;

    /**
     * 生产女职工人数
     */
    @ExcelProperty(value = "生产女职工人数")
    private Integer femaleProductNum;

    /**
     * 接害工人数
     */
    @ExcelProperty(value = "接害工人数")
    private Integer affectedTotalNum;

    /**
     * 接害女职工人数
     */
    @ExcelProperty(value = "接害女职工人数")
    private Integer femaleAffectedNum;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
