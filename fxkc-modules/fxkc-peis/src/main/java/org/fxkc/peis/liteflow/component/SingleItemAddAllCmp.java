package org.fxkc.peis.liteflow.component;


import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.utils.MapstructUtils;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.domain.vo.AmountCalculationItemVo;
import org.fxkc.peis.domain.vo.AmountCalculationVo;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 根据单项的项目list信息，累加各个单项计算合计的标准金额，应收额 和折扣
 */
@Slf4j
@Component("SingleItemAddAllCmp")
@RequiredArgsConstructor
public class SingleItemAddAllCmp extends NodeComponent {

    private final ITjPackageService tjPackageService;

    @Override
    public void process() {
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);
        List<AmountCalculationItemBo> finalAmountCalculationItemBos = contextBean.getFinalAmountCalculationItemBos();
        //最终响应前端的Vo信息
        AmountCalculationVo amountCalculationVo = new AmountCalculationVo();
        //根据标准价格 乘以对应折扣 得到实际应收额
        BigDecimal totalStandardAmount = new BigDecimal("0");
        BigDecimal totalReceivableAmount = new BigDecimal("0");
        for (int i = 0; i < finalAmountCalculationItemBos.size(); i++) {
            AmountCalculationItemBo bo = finalAmountCalculationItemBos.get(i);
            BigDecimal receivableAmountByDiscount = tjPackageService.getReceivableAmountByDiscount(bo.getStandardAmount(), bo.getDiscount());
            finalAmountCalculationItemBos.get(i).setReceivableAmount(receivableAmountByDiscount);
            totalStandardAmount = totalStandardAmount.add(bo.getStandardAmount());
            totalReceivableAmount = totalReceivableAmount.add(receivableAmountByDiscount);
        }

        //如果标准总金额为空，则直接总折扣给0 防止除以为0的情况
        amountCalculationVo.setDiscount(tjPackageService.getDiscountByReceivableAmount(totalStandardAmount,totalReceivableAmount));
        amountCalculationVo.setStandardAmount(totalStandardAmount);
        amountCalculationVo.setReceivableAmount(totalReceivableAmount);
        amountCalculationVo.setAmountCalculationItemVos(MapstructUtils.convert(finalAmountCalculationItemBos, AmountCalculationItemVo.class));
        contextBean.setAmountCalculationVo(amountCalculationVo);
    }



}
