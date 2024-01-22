package org.fxkc.peis.liteflow.component;


import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 公用（个检和团检）项目删除
 */
@Slf4j
@Component("PublicDeleteCmp")
public class PublicDeleteCmp extends NodeComponent {


    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);
        //个检项目删除时 组装最终项目list集合信息 和排列最终序号信息
        List<AmountCalculationItemBo> amountCalculationItemBos = requestData.getAmountCalculationItemBos();
        List<Integer> sortList = amountCalculationItemBos.stream().map(m -> m.getSort()).collect(Collectors.toList());
        List<AmountCalculationItemBo> haveAmountCalculationItemBos = requestData.getHaveAmountCalculationItemBos();

        List<AmountCalculationItemBo> finalAmountCalculationItemBos = haveAmountCalculationItemBos.stream().filter(m -> !sortList.contains(m.getSort())).collect(Collectors.toList());
        //重新编号
        for (int i = 0; i < finalAmountCalculationItemBos.size(); i++) {
            finalAmountCalculationItemBos.get(i).setSort(i+1);
        }

        contextBean.setFinalAmountCalculationItemBos(finalAmountCalculationItemBos);
    }
}
