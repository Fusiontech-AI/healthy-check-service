package org.fxkc.peis.domain.vo;

import org.fxkc.peis.domain.TjTeamGroupItem;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 团检分组对应项目视图对象 tj_team_group_item
 *
 * @author JunBaiChen
 * @date 2024-01-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TjTeamGroupItem.class)
public class TjTeamGroupItemVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 分组id
     */
    @ExcelProperty(value = "分组id")
    private Long groupId;

    /**
     * 组合项目id
     */
    @ExcelProperty(value = "组合项目id")
    private Long itemId;

    /**
     * 组合项目名称
     */
    @ExcelProperty(value = "组合项目名称")
    private String itemName;

    /**
     * 标准价格
     */
    @ExcelProperty(value = "标准价格")
    private Long standardPrice;

    /**
     * 实际价格
     */
    @ExcelProperty(value = "实际价格")
    private Long actualPrice;

    /**
     * 折扣
     */
    @ExcelProperty(value = "折扣")
    private Long discount;

    /**
     * 是否套餐包含的项目1是2否
     */
    @ExcelProperty(value = "是否套餐包含的项目1是2否")
    private String include;

    /**
     * 是否必选(1:是0否)
     */
    @ExcelProperty(value = "是否必选(1:是0否)")
    private Long isRequired;


}
