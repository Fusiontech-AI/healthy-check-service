package org.fxkc.peis.rule.condition;


import org.fxkc.peis.rule.constants.ConditionRelationType;
import org.fxkc.peis.rule.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

import static org.fxkc.peis.rule.constants.ConditionRelationType.*;

@Component
public class RelationCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                EQUAL,
                NOT_EQUAL,
                LESS_EQUAL,
                GREATER_EQUAL,
                LESS,
                GREATER
        };
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
        try {
            return String.format(format(conditionInstance), conditionInstance.getVariableName(), conditionInstance.getReferenceValue());
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}
