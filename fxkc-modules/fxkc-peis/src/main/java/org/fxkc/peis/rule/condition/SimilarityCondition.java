package org.fxkc.peis.rule.condition;


import org.fxkc.peis.rule.constants.ConditionRelationType;
import org.fxkc.peis.rule.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

import static org.fxkc.peis.rule.constants.ConditionRelationType.*;


@Component
public class SimilarityCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                STRING_ACCURACY,
                STRING_SECOND_ACCURACY
        };
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
        try {
            return String.format(format(conditionInstance), conditionInstance.getVariableName(), conditionInstance.getReferenceValue(), conditionInstance.getSplitSymbol()==null?"":conditionInstance.getSplitSymbol());
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}
