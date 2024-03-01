package org.fxkc.peis.rule.expression;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fxkc.peis.domain.RuleTjCondition;
import org.fxkc.peis.domain.RuleTjInfo;
import org.fxkc.peis.domain.RuleTjSet;
import org.fxkc.peis.mapper.RuleTjConditionMapper;
import org.fxkc.peis.mapper.RuleTjInfoMapper;
import org.fxkc.peis.mapper.RuleTjSetMapper;
import org.fxkc.peis.rule.condition.*;
import org.fxkc.peis.rule.constants.ConditionLogicType;
import org.fxkc.peis.rule.constants.ConditionRelationType;
import org.fxkc.peis.rule.constants.RuleLogicType;
import org.fxkc.peis.rule.exception.RuleRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Component
public class ExpressionBuildService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SequenceCondition sequenceCondition;

    @Autowired
    private IntervalCondition intervalCondition;

    @Autowired
    private RegexCondition regexCondition;

    @Autowired
    private List<Condition> conditionList;

    @Autowired
    private RuleTjSetMapper tjSetMapper;

    @Autowired
    private RuleTjInfoMapper ruleInfoRepository;

    @Autowired
    private RuleTjConditionMapper conditionInfoRepository;

    private Condition selectCondition(ConditionRelationType relationType) {
        if (CollectionUtils.isEmpty(conditionList)) {
            throw new RuleRunTimeException("No bean of type Condition was found");
        }

        return conditionList.stream()
                .filter(b -> Arrays.asList(b.relationTypes()).contains(relationType)).findFirst()
                .orElseThrow(() -> new RuleRunTimeException("No bean of type Condition found for " + relationType.name()));
    }

    /**
     * build ruleset expressions by combining multiple rules
     *
     * @return
     */
    public String buildRulesetExpression(Long rulesetId) {
        if (rulesetId==null) {
            throw new RuntimeException("invalid parameter(rulesetId)");
        }
        //查询规则集对应的规则类型
        RuleTjSet ruleTjSet = tjSetMapper.selectById(rulesetId);

        List<RuleTjInfo> ruleInfos = ruleInfoRepository.selectList(new LambdaQueryWrapper<RuleTjInfo>()
                .eq(RuleTjInfo::getRulesetId, rulesetId)
        );
        if (CollectionUtils.isEmpty(ruleInfos)) {
            throw new RuleRunTimeException("no ruleinfo under the ruleset");
        }

       /* if (ruleInfos.stream().map(RuleTjInfo::getReturnValues).anyMatch(StringUtils::isEmpty)) {
            throw new RuleRunTimeException("all rules in the ruleset must set the return value");
        }*/

        StringBuilder rulesetExpression = new StringBuilder("let rmap = ''");
        //如果是功能检查类型的 则需要构造list的条件表达式 需要进入每一个规则项 并记录对应的id
       /*if(Objects.equals("1",ruleTjSet.getXmRuleType())){
           rulesetExpression = new StringBuilder("let rmap = seq.list()");
       }*/

        rulesetExpression = new StringBuilder("let rmap = seq.list()");

        rulesetExpression.append(";\n");

        // sort by priority in desc order
        ruleInfos.sort(Comparator.comparingInt(RuleTjInfo::getPriority).reversed());

        Iterator<RuleTjInfo> ruleInfoIterator = ruleInfos.iterator();
        RuleLogicType lastRuleLogicType = null;
        while (ruleInfoIterator.hasNext()) {
            RuleTjInfo ruleInfo = ruleInfoIterator.next();
            List<RuleTjCondition> conditionInfos = conditionInfoRepository.selectList(new LambdaQueryWrapper<RuleTjCondition>()
                    .eq(RuleTjCondition::getRuleId, ruleInfo.getId())
            );
            if (CollectionUtils.isEmpty(conditionInfos)) {
                log.warn("no conditions found under the rule({}).", ruleInfo.getId());
                continue;
            }

            RuleLogicType currentRuleLogicType = RuleLogicType.getEnum(ruleInfo.getLogicType());
            String ruleExpression = buildRuleExpression(conditionInfos,ruleTjSet.getSplitSymbol());

            // first rule
            if (null == lastRuleLogicType) {
                rulesetExpression.append("if(");
                if (ruleInfoIterator.hasNext() && RuleLogicType.AND.equals(currentRuleLogicType)) {
                    rulesetExpression.append("(")
                            .append(ruleExpression)
                            .append(") ")
                            .append(currentRuleLogicType.getValue())
                            .append(" ");
                }
                else if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                    rulesetExpression.append(ruleExpression).append("){\n");
                }
            }
            else {
                if (RuleLogicType.AND.equals(lastRuleLogicType)) {
                    rulesetExpression.append("(").append(ruleExpression).append(")");
                    if (ruleInfoIterator.hasNext() && RuleLogicType.AND.equals(currentRuleLogicType)) {
                        rulesetExpression.append(" ").append(currentRuleLogicType.getValue()).append(" ");
                    }
                    else if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                        rulesetExpression.append("){\n");
                    }
                }
                else if (RuleLogicType.XOR.equals(lastRuleLogicType)) {
                    if(Objects.equals("1",ruleTjSet.getXmRuleType())){
                        rulesetExpression.append("if(");
                    }else{
                        rulesetExpression.append("elsif(");
                    }
                    if (ruleInfoIterator.hasNext() && RuleLogicType.AND.equals(currentRuleLogicType)) {
                        rulesetExpression.append("(")
                                .append(ruleExpression)
                                .append(") ")
                                .append(currentRuleLogicType.getValue())
                                .append(" ");
                    }
                    else if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                        rulesetExpression.append(ruleExpression).append("){\n");
                    }
                }
            }

            if (!ruleInfoIterator.hasNext() || RuleLogicType.XOR.equals(currentRuleLogicType)) {
                /*if(Objects.equals("1",ruleTjSet.getXmRuleType())){
                    rulesetExpression.append("seq.add(rmap, '").append(ruleInfo.getId()).append("'")
                            .append(");\n");
                }else{
                    rulesetExpression.append("return '"+ruleInfo.getId()+"'")
                            .append(";\n");
                }*/
                rulesetExpression.append("seq.add(rmap, '").append(ruleInfo.getId()).append("'")
                        .append(");\n");

               /* try {
                    Map<String, Object> returnValueMap = objectMapper.readValue(ruleInfo.getReturnValues(), new TypeReference<Map<String, Object>>() {
                    });
                    returnValueMap.entrySet().forEach(e -> rulesetExpression.append("return ")
                            //.append(buildReturnMapValue(e))
                            .append(");\n"));
                } catch (Exception e) {
                    throw new RuleRunTimeException("The return values of the rule(" + ruleInfo.getId() + ") is not in valid json object string format:" + ruleInfo.getReturnValues(), e);

                }*/
                rulesetExpression.append("}\n");
            }

            lastRuleLogicType = currentRuleLogicType;
        }

        rulesetExpression.append("return rmap;");
        return rulesetExpression.toString();
    }

    /**
     * build rule expressions by combining multiple conditions
     *
     * @param conditionInfos condition list under a single rule
     * @return The return value only represents the combination of multiple conditions under a single rule,
     * and does not include the return value
     */
    private String buildRuleExpression(List<RuleTjCondition> conditionInfos,String splitSymbol) {
        if (CollectionUtils.isEmpty(conditionInfos)) {
            throw new RuleRunTimeException("condition list is empty");
        }

        List<ConditionInstance> conditionInstances = new ArrayList<>();
        for (RuleTjCondition conditionInfo : conditionInfos) {
            ConditionRelationType relationType = Optional.ofNullable(ConditionRelationType.getEnum(conditionInfo.getRelationType()))
                    .orElseThrow(() -> new RuleRunTimeException("invalid parameter(relationType):" + conditionInfo.getRelationType()));
            ConditionLogicType logicType = Optional.ofNullable(ConditionLogicType.getEnum(conditionInfo.getLogicType()))
                    .orElseThrow(() -> new RuleRunTimeException("invalid parameter(logicType):" + conditionInfo.getLogicType()));

            // When the condition relation type is regex or sequence or interval, not convert it to a string by adding single quotes.
            String referenceValue = conditionInfo.getReferenceValue();
            if (Arrays.binarySearch(regexCondition.relationTypes(), relationType) < 0
                && Arrays.binarySearch(intervalCondition.relationTypes(), relationType) < 0
                && Arrays.binarySearch(sequenceCondition.relationTypes(), relationType) < 0) {
                try {
                    referenceValue = stringValueEscape(referenceValue);
                } catch (Exception e) {
                    throw new RuleRunTimeException("Value cannot be converted to string format:" + referenceValue, e);
                }
            }

            ConditionInstance conditionInstance = ConditionInstance.builder()
                    .variableName(conditionInfo.getVariableName())
                    .relationType(relationType)
                    .referenceValue(referenceValue)
                    .priority(conditionInfo.getPriority())
                    .logicType(logicType)
                    .splitSymbol(splitSymbol)
                    .build();
            conditionInstances.add(conditionInstance);
        }

        log.info("## build condition expression start");

        // sort by priority in desc order
        conditionInstances.sort(Comparator.comparingInt(ConditionInstance::getPriority).reversed());

        StringBuilder finalExpression = new StringBuilder();
        for (int i = 0, conditionInstancesSize = conditionInstances.size(); i < conditionInstancesSize; i++) {
            ConditionInstance conditionInstance = conditionInstances.get(i);
            Condition condition = selectCondition(conditionInstance.getRelationType());
            finalExpression.append(condition.build(conditionInstance));

            if (i < conditionInstancesSize - 1) {
                if (i > 0) {
                    // Add parentheses to conditions to ensure priority
                    finalExpression.insert(0, "(");
                    finalExpression.append(")");
                }

                // Ignore the conditional logic operator with the lowest priority
                finalExpression.append(" ").append(conditionInstance.getLogicType().getValue()).append(" ");
            }
        }

        log.info("## build condition expression success => {}", finalExpression);
        return finalExpression.toString();
    }

    /*private String buildReturnMapValue(Map.Entry<String, Object> entry) {
        try {
            return "'" + entry.getKey() + "', " + stringValueEscape(entry.getValue());
        } catch (Exception e) {
            throw new RuleRunTimeException("Value cannot be converted to string format:" + entry.getValue(), e);
        }
    }*/

    /**
     * wrap non-boolean and non-numeric string value with single quote
     *
     * @param value
     * @return
     * @throws Exception
     */
    private String stringValueEscape(Object value) throws Exception {
        Assert.notNull(value, "Value cannot be null");
        String stringValue = value instanceof String ? value.toString() : objectMapper.writeValueAsString(value);

        if (Boolean.TRUE.toString().equals(stringValue) || Boolean.FALSE.toString()
                .equals(stringValue) || stringValue.matches("^(\\-)?\\d+(\\.\\d+)?$")) {
            return stringValue;
        }

        if (stringValue.startsWith("'") && stringValue.endsWith("'")) {
            return stringValue;
        }

        return "'" + stringValue.replace("'", "\\'") + "'";
    }
}
