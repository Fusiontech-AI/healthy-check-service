package org.fxkc.peis.rule.condition;


import org.fxkc.peis.rule.constants.ConditionRelationType;
import org.fxkc.peis.rule.exception.RuleRunTimeException;
import org.springframework.stereotype.Component;
import static org.fxkc.peis.rule.constants.ConditionRelationType.*;


@Component
public class StringCondition implements Condition {

    @Override
    public ConditionRelationType[] relationTypes() {
        return new ConditionRelationType[]{
                STRING_CONTAINS,
                STRING_NOT_CONTAINS,
                STRING_STARTSWITH,
                STRING_NOT_STARTSWITH,
                STRING_ENDSWITH,
                STRING_NOT_ENDSWITH,
                STRING_EQUAL,
                STRING_NOT_EQUAL
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
