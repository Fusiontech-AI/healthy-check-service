package org.fxkc.peis.rule.condition;


import org.fxkc.peis.rule.constants.ConditionRelationType;

public interface Condition {

    default String format(ConditionInstance conditionInstance) {
        return conditionInstance.getRelationType().getValue();
    }

    ConditionRelationType[] relationTypes();

    String build(ConditionInstance conditionInstance);
}
