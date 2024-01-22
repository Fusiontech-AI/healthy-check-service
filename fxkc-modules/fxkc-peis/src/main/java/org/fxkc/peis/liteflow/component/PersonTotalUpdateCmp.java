package org.fxkc.peis.liteflow.component;


import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.fxkc.peis.liteflow.enums.InputTypeEnum;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 个检项目总计信息修改
 */
@Slf4j
@Component("PersonTotalUpdateCmp")
@RequiredArgsConstructor
public class PersonTotalUpdateCmp extends NodeComponent {

    private final ITjPackageService tjPackageService;

    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);

        //判断当前操作项是应收额 还是 折扣
        String inputType = requestData.getInputType();
        BigDecimal discount = new BigDecimal("0");
        if(Objects.equals(inputType, InputTypeEnum.折扣.getCode())){
            discount = requestData.getDiscount();//直接取总计折扣 作为单项折扣进行后续计算
        }else if(Objects.equals(inputType, InputTypeEnum.应收金额.getCode())){
            discount = tjPackageService.getDiscountByReceivableAmount(requestData.getStandardAmount(),requestData.getReceivableAmount());
        }
        List<AmountCalculationItemBo> haveAmountCalculationItemBos = requestData.getHaveAmountCalculationItemBos();

        //根据合计的折扣 去计算除了标准金额最高的单项信息
        List<AmountCalculationItemBo> collect = haveAmountCalculationItemBos.stream().sorted(Comparator.comparing(AmountCalculationItemBo::getStandardAmount)).collect(Collectors.toList());
        BigDecimal addAmount = new BigDecimal("0");
        for (int i = 0; i <collect.size() ; i++) {
            if(i==collect.size()-1){
                //最后一项时 通过合计的应收金额 减去前面的应收金额和
                collect.get(i).setReceivableAmount(tjPackageService.getReceivableAmountByDiscount(requestData.getStandardAmount(),discount).subtract(addAmount));
                collect.get(i).setDiscount(tjPackageService.getDiscountByReceivableAmount(collect.get(i).getStandardAmount(),collect.get(i).getReceivableAmount()));
            }else{
                //计算单项的折扣
                collect.get(i).setDiscount(discount);
                collect.get(i).setReceivableAmount(tjPackageService.getReceivableAmountByDiscount(collect.get(i).getStandardAmount(),discount));
                addAmount = addAmount.add(collect.get(i).getReceivableAmount());
            }
        }

        contextBean.setFinalAmountCalculationItemBos(collect.stream().sorted(Comparator.comparingInt(AmountCalculationItemBo::getSort)).collect(Collectors.toList()));
    }
}
