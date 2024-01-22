package org.fxkc.peis.liteflow.component;


import cn.hutool.core.collection.CollUtil;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fxkc.peis.domain.bo.AmountCalculationBo;
import org.fxkc.peis.liteflow.enums.ChangeTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * 前置参数检查
 */
@Slf4j
@Component("PreCheckCmp")
public class PreCheckCmp extends NodeComponent {


    @Override
    public void process() {
        //拿到请求参数
        AmountCalculationBo requestData = this.getSlot().getRequestData();
        //当前变更类型为单项 或 总计项 则输入类型就不能为空
        if(Arrays.asList(ChangeTypeEnum.单项.getCode(),ChangeTypeEnum.总计项.getCode()).contains(requestData.getChangeType())){
            if(StringUtils.isEmpty(requestData.getInputType())){
                throw new RuntimeException("当前变更单项或合计信息时，需要明确变更具体输入项！");
            }

            if(CollUtil.isEmpty(requestData.getHaveAmountCalculationItemBos())){
                throw new RuntimeException("当前变更单项或合计信息时，子项目列表信息不能为空！");
            }
        }

        if(Objects.equals(ChangeTypeEnum.单项.getCode(),requestData.getChangeType())
            && requestData.getSort()==null){
            throw new RuntimeException("当前变更单项信息时，具体的单项序号不能为空！");
        }

        if(Objects.equals(ChangeTypeEnum.总计项.getCode(),requestData.getChangeType())){
            if(requestData.getStandardAmount()==null){
                throw new RuntimeException("当前变更合计信息时，合计的标准金额不能为空！");
            }

            if(requestData.getDiscount()==null){
                throw new RuntimeException("当前变更合计信息时，合计的折扣不能为空！");
            }

            if(requestData.getReceivableAmount()==null){
                throw new RuntimeException("当前变更合计信息时，合计的应收金额不能为空！");
            }
        }

        if(Arrays.asList(ChangeTypeEnum.新增.getCode(),ChangeTypeEnum.删除.getCode()).contains(requestData.getChangeType())
            && CollUtil.isEmpty(requestData.getAmountCalculationItemBos())) {
            throw new RuntimeException("当前变更新增或删除信息时，对应子项目列表信息不能为空！");
        }

        //团检时校验 分组类型不能为空
        if(Objects.equals(requestData.getRegType(),"2") && requestData.getGroupId()==null){
            throw new RuntimeException("团检计算金额信息时，对应分组id不能为空！");
        }

        }
}
