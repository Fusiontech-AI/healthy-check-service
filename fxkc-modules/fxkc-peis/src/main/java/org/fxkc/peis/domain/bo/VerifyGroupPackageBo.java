package org.fxkc.peis.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VerifyGroupPackageBo {

    /**
     * 主键id
     */
    @NotBlank(message = "主键id不能为空")
    private Long id;

    /**
     * 分组名称
     */
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    /**
     * 项目折扣
     */
    @NotNull(message = "项目折扣不能为空")
    private BigDecimal itemDiscount;

    /**
     * 加项折扣
     */
    @NotNull(message = "加项折扣不能为空")
    private BigDecimal addDiscount;

    /**
     * 是否同步项目(1:是0:否)
     */
    @NotBlank(message = "是否同步项目不能为空")
    private String isSyncProject;

    /**
     * 项目id集合
     */
    @NotEmpty(message = "项目id集合不能为空")
    private List<String> itemList;


    /**
     * 实际价格
     */
    @NotNull(message = "实际价格不能为空")
    private BigDecimal actualPrice;

}
