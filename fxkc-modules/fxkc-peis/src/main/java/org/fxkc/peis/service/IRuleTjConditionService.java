package org.fxkc.peis.service;

import org.fxkc.peis.domain.RuleTjCondition;
import org.fxkc.peis.domain.bo.RuleTjConditionBo;
import org.fxkc.peis.domain.bo.RuleTjConditionQueryBo;

import java.util.List;

/**
 * 体检项目规则条件Service接口
 *
 * @author JunBaiChen
 * @date 2024-02-29
 */
public interface IRuleTjConditionService {


    List<RuleTjCondition> queryRuleTjConditionList(RuleTjConditionQueryBo dto);

    Long addRuleTjCondition(RuleTjConditionBo dto);

    Boolean updateRuleTjCondition(RuleTjConditionBo dto);

    Boolean deleteRuleTjCondition(RuleTjCondition dto);
}
