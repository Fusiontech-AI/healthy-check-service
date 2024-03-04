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
 * 团检检项目单项修改
 */
@Slf4j
@Component("TeamSingleUpdateCmp")
@RequiredArgsConstructor
public class TeamSingleUpdateCmp extends NodeComponent {

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
                     //在修改单项折扣时，默认将费用给对应支付方式的那一边，混合支付就全部给单位费用那一边 自行修改
                     fillSingle(m,inputType);
                 }else if(Objects.equals(inputType, InputTypeEnum.应收金额.getCode())){
                     //目前界面没有直接改应收金额的入口，都是通过修改单项个费或团费
                     m.setDiscount(tjPackageService.getDiscountByReceivableAmount(m.getStandardAmount(),m.getReceivableAmount()));
                 }else if(Objects.equals(inputType, InputTypeEnum.收费方式.getCode())){
                     //修改支付方式时 如果改为个人支付，需要将应收额全部赋值给个费 反之赋值给团费，混合支付也默认给团费
                     fillSingle(m,inputType);
                 }else if(Objects.equals(inputType, InputTypeEnum.个人应收额.getCode())){
                     m.setTeamAmount(m.getReceivableAmount().subtract(m.getPersonAmount()));
                 }else if(Objects.equals(inputType, InputTypeEnum.单位应收额.getCode())){
                     m.setPersonAmount(m.getReceivableAmount().subtract(m.getTeamAmount()));
                 }

             }
         });

        contextBean.setFinalAmountCalculationItemBos(haveAmountCalculationItemBos);
    }


    /**
     * 处理个费 和 团费的金额
     * @param bo
     * @param inputType
     */
    public void fillSingle(AmountCalculationItemBo bo,String inputType){
        if(Objects.equals("0",bo.getPayType())){
            bo.setPersonAmount(bo.getReceivableAmount());
            bo.setTeamAmount(new BigDecimal("0"));
        }else{
            bo.setTeamAmount(bo.getReceivableAmount());
            bo.setPersonAmount(new BigDecimal("0"));
        }

        //混合支付 且调整单笔折扣时 默认全部给到团检这边 用户自行分配
        /*if(Objects.equals(inputType,InputTypeEnum.折扣.getCode()) && Objects.equals("2",bo.getPayType()) ){
            bo.setTeamAmount(bo.getReceivableAmount());
            bo.setPersonAmount(new BigDecimal("0"));
        }*/
    }
}
