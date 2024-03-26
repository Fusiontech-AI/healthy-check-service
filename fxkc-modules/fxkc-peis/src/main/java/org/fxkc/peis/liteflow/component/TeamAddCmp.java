package org.fxkc.peis.liteflow.component;


import cn.hutool.core.collection.CollUtil;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalGroupBo;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.enums.GroupTypeEnum;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 团检项目新增组件
 * 相比个检项目新增 多了计算 支付方式和个费团费金额
 */
@Slf4j
@Component("TeamAddCmp")
@RequiredArgsConstructor
public class TeamAddCmp extends NodeComponent {

    private final ITjPackageService tjPackageService;

    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);
        AmountCalGroupBo amountCalGroupBo = requestData.getAmountCalGroupBo();
        List<AmountCalculationItemBo> finalAmountCalculationItemBos = new ArrayList<>();
        List<AmountCalculationItemBo> haveAmountCalculationItemBos = requestData.getHaveAmountCalculationItemBos();

        //个检项目新增时 组装最终项目list集合信息 和排列最终序号信息
        List<AmountCalculationItemBo> amountCalculationItemBos = requestData.getAmountCalculationItemBos();
        //需要区分当前团检的分组类型 分组折扣需要将子项折扣全部按照分组折扣计算
        if(Objects.equals(GroupTypeEnum.DISCOUNT.getCode(),amountCalGroupBo.getGroupType())){
            //将新增记录全部赋值为项目折扣
            amountCalculationItemBos.stream().forEach(m->{
                m.setDiscount(amountCalGroupBo.getItemDiscount());
                m.setReceivableAmount(tjPackageService.getReceivableAmountByDiscount(m.getStandardAmount(),m.getDiscount()));
                fillDiscountSingleAmount(m);
            });
        }else{
            //除了折扣分组特殊性之外 其他分组都包含了分组金额 分组折扣 分组外折扣 对应支付方式
            tjPackageService.calCulPayType(amountCalculationItemBos,haveAmountCalculationItemBos,amountCalGroupBo);
        }

        finalAmountCalculationItemBos.addAll(amountCalculationItemBos);
        if(CollUtil.isNotEmpty(haveAmountCalculationItemBos)){
            haveAmountCalculationItemBos.stream().forEach(m->m.setSort(m.getSort()+amountCalculationItemBos.size()));
            finalAmountCalculationItemBos.addAll(haveAmountCalculationItemBos);
        }

        contextBean.setFinalAmountCalculationItemBos(finalAmountCalculationItemBos);
    }


    /**
     * 处理折扣 个费 和 团费的金额 根据支付类型来处理
     * @param bo
     */
    public void fillDiscountSingleAmount(AmountCalculationItemBo bo){
        if(Objects.equals("0",bo.getPayType())){
            bo.setPersonAmount(bo.getReceivableAmount());
            bo.setTeamAmount(new BigDecimal("0"));
        }else if(Objects.equals("1",bo.getPayType())){
            bo.setTeamAmount(bo.getReceivableAmount());
            bo.setPersonAmount(new BigDecimal("0"));
        }
    }
}
