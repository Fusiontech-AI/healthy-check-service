package org.fxkc.peis.rule.condition;


import org.fxkc.peis.rule.constants.ConditionRelationType;
import org.fxkc.peis.rule.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;

import static org.fxkc.peis.rule.constants.ConditionRelationType.*;


@Component
public class RegexCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{REGEX};
    }

    @Override
    public String build(ConditionInstance conditionInstance) {
        try {
            String referenceValue = conditionInstance.getReferenceValue();
            if (!referenceValue.startsWith("/")) {
                referenceValue = "/" + referenceValue;
            }
            if (!referenceValue.endsWith("/")) {
                referenceValue = referenceValue + "/";
            }
            return String.format(format(conditionInstance), conditionInstance.getVariableName(), referenceValue);
        } catch (Exception e) {
            throw new RuleRunTimeException(e);
        }
    }
}
