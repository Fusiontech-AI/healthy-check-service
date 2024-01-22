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
import java.util.List;
import java.util.Objects;

/**
 * 个检项目单项修改
 */
@Slf4j
@Component("PersonSingleUpdateCmp")
@RequiredArgsConstructor
public class PersonSingleUpdateCmp extends NodeComponent {

    private final ITjPackageService tjPackageService;

    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);

        List<AmountCalculationItemBo> haveAmountCalculationItemBos = requestData.getHaveAmountCalculationItemBos();

        haveAmountCalculationItemBos.stream().forEach(m -> {
             //当前序号为编辑的序号记录时，需要重新计算当前记录的价格信息
             if(Objects.equals(m.getSort(),requestData.getSort())){
                 //判断当前操作项是应收额 还是 折扣
                 String inputType = requestData.getInputType();
                 if(Objects.equals(inputType, InputTypeEnum.折扣.getCode())){
                     m.setReceivableAmount(tjPackageService.getReceivableAmountByDiscount(m.getStandardAmount(),m.getDiscount()));
                 }else if(Objects.equals(inputType, InputTypeEnum.应收金额.getCode())){
                     m.setDiscount(tjPackageService.getDiscountByReceivableAmount(m.getStandardAmount(),m.getReceivableAmount()));
                     m.setPersonAmount(m.getReceivableAmount());
                 }
             }
            m.setPersonAmount(m.getReceivableAmount());
            m.setTeamAmount(new BigDecimal("0"));
        });

        contextBean.setFinalAmountCalculationItemBos(haveAmountCalculationItemBos);
    }
}
