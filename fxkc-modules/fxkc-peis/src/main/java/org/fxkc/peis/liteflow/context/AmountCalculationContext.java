package org.fxkc.peis.liteflow.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.domain.vo.AmountCalculationVo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountCalculationContext {

    /**
     * 最终项目list信息
     */
    private List<AmountCalculationItemBo> finalAmountCalculationItemBos;

    /**
     * 最终响应内容信息
     */
    private AmountCalculationVo amountCalculationVo;

}
