package org.fxkc.peis.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.fxkc.peis.domain.TjTeamGroupItem;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.fxkc.common.excel.annotation.ExcelDictFormat;
import org.fxkc.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


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
     * 组合项目id
     */
    private Long itemId;

    /**
     * 组合项目名称
     */
    private String itemName;

    /**
     * 标准价格
     */
    private BigDecimal standardPrice;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 是否套餐包含的项目1是2否
     */
    private String include;

    /**
     * 是否必选(1:是0否)
     */
    private Boolean isRequired;


}
