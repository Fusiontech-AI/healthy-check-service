package org.fxkc.peis.liteflow.component;


import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalGroupBo;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 团检项目加项折扣和分组折扣信息修改
 */
@Slf4j
@Component("TeamTotalAddUpdateCmp")
@RequiredArgsConstructor
public class TeamTotalAddUpdateCmp extends NodeComponent {

    private final ITjPackageService tjPackageService;

    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);

        List<AmountCalculationItemBo> haveAmountCalculationItemBos = requestData.getHaveAmountCalculationItemBos();

        AmountCalGroupBo amountCalGroupBo = requestData.getAmountCalGroupBo();
        BigDecimal price = amountCalGroupBo.getPrice();//项目金额
        BigDecimal itemDiscount = amountCalGroupBo.getItemDiscount();//项目分组折扣
        BigDecimal addDiscount = amountCalGroupBo.getAddDiscount();//项目加项折扣
        List<AmountCalculationItemBo> collect = haveAmountCalculationItemBos.stream().sorted(Comparator.comparing(AmountCalculationItemBo::getStandardAmount)).collect(Collectors.toList());
        BigDecimal addAmount = new BigDecimal("0");
        for (int i = 0; i <collect.size() ; i++) {
            collect.get(i).setReceivableAmount(tjPackageService.getReceivableAmountByDiscount(collect.get(i).getStandardAmount(),itemDiscount));
            collect.get(i).setDiscount(itemDiscount);//默认给项目折扣
            addAmount = addAmount.add(collect.get(i).getReceivableAmount());
            //超过了分组金额
            if(addAmount.compareTo(price)>=0){
                collect.get(i).setDiscount(addDiscount);//超过给加项折扣
                collect.get(i).setReceivableAmount(tjPackageService.getReceivableAmountByDiscount(collect.get(i).getStandardAmount(),addDiscount));
            }
        }

        contextBean.setFinalAmountCalculationItemBos(collect.stream().sorted(Comparator.comparingInt(AmountCalculationItemBo::getSort)).collect(Collectors.toList()));
    }
}
