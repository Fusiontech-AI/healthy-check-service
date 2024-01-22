package org.fxkc.peis.liteflow.component;


import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.fxkc.peis.service.ITjPackageService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 团检项目总计信息修改  在个检基础上多了对子项的个费 和 团费进行计算过程
 */
@Slf4j
@Component("TeamTotalUpdateCmp")
@RequiredArgsConstructor
public class TeamTotalUpdateCmp extends NodeComponent {

    private final ITjPackageService tjPackageService;

    @Override
    public void process() {
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);
        List<AmountCalculationItemBo> finalAmountCalculationItemBos = contextBean.getFinalAmountCalculationItemBos();
        finalAmountCalculationItemBos.stream().forEach(m->{
            tjPackageService.fillSingle(m);
        });
        contextBean.setFinalAmountCalculationItemBos(finalAmountCalculationItemBos);
    }

    @Override
    public boolean isAccess() {
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //团检才走这一步
        if(Objects.equals("2",requestData.getRegType())){
            return true;
        }else{
            return false;
        }

    }
}
