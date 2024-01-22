package org.fxkc.peis.liteflow.component;


import com.yomahub.liteflow.core.NodeSwitchComponent;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.liteflow.enums.ChangeTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 初始化选择器，区分个检和团检
 */
@Slf4j
@Component("InitSelectCmp")
public class InitSelectCmp extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        if(Objects.equals(ChangeTypeEnum.删除.getCode(),requestData.getChangeType())){
            return "PublicDeleteCmp";
        }else if(Objects.equals(ChangeTypeEnum.新增.getCode(),requestData.getChangeType())){
            if(Objects.equals(requestData.getRegType(),"1")){
                return "PersonAddCmp";
            }
            return "TeamAddCmp";
        }else if(Objects.equals(ChangeTypeEnum.总计项.getCode(),requestData.getChangeType())){
            //这里是先走个检的总计修改 再走团检(有入口开关)
            return "t1";
        }else if(Objects.equals(ChangeTypeEnum.单项.getCode(),requestData.getChangeType())){
            if(Objects.equals(requestData.getRegType(),"1")){
                return "PersonSingleUpdateCmp";
            }
            return "TeamSingleUpdateCmp";
        }

        throw new RuntimeException("未找到相应选择节点，请检查参数是否合法!");
    }
}
