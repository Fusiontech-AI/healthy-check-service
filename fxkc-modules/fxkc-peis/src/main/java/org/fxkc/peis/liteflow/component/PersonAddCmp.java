package org.fxkc.peis.liteflow.component;


import cn.hutool.core.collection.CollUtil;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.domain.bo.AmountCalculationItemBo;
import org.fxkc.peis.liteflow.context.AmountCalculationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 个检项目新增组件
 */
@Slf4j
@Component("PersonAddCmp")
public class PersonAddCmp extends NodeComponent {


    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //拿到上下文信息
        AmountCalculationContext contextBean = this.getContextBean(AmountCalculationContext.class);
        List<AmountCalculationItemBo> finalAmountCalculationItemBos = new ArrayList<>();
        //个检项目新增时 组装最终项目list集合信息 和排列最终序号信息
        List<AmountCalculationItemBo> amountCalculationItemBos = requestData.getAmountCalculationItemBos();
        finalAmountCalculationItemBos.addAll(amountCalculationItemBos);
        List<AmountCalculationItemBo> haveAmountCalculationItemBos = requestData.getHaveAmountCalculationItemBos();
        if(CollUtil.isNotEmpty(haveAmountCalculationItemBos)){
            haveAmountCalculationItemBos.stream().forEach(m->m.setSort(m.getSort()+amountCalculationItemBos.size()));
            finalAmountCalculationItemBos.addAll(haveAmountCalculationItemBos);
        }
        contextBean.setFinalAmountCalculationItemBos(finalAmountCalculationItemBos);
    }
}
