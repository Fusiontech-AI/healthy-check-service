package org.fxkc.peis.rule.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Condition relation operator type
 */
@Getter
@AllArgsConstructor
public enum ConditionRelationType {

    EQUAL("等于", "decimal(%s) == decimal(%s)","1"),

    NOT_EQUAL("不等于", "decimal(%s) != decimal(%s)","1"),

    LESS_EQUAL("小于等于", "decimal(%s) <= decimal(%s)","1"),

    GREATER_EQUAL("大于等于", "decimal(%s) >= decimal(%s)","1"),

    LESS("小于", "decimal(%s) < decimal(%s)","1"),

    GREATER("大于", "decimal(%s) > decimal(%s)","1"),

    /**
     * a string type element is included in a list
     */
    INCLUDE_IN_LIST("列表包含指定字符串", "include(seq.set(%s), str(%s))","2"),

    /**
     * a string type element is not included in a list
     */
    NOT_INCLUDE_IN_LIST("列表不包含指定字符串", "!include(seq.set(%s), str(%s))","2"),

    /**
     * some characters in a string are contained in a list
     */
    SOME_CONTAINS_IN_LIST("列表包含指定字符串的某一部分", "nil != seq.some(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)","2"),

    /**
     * none characters of a string are contained in a list
     */
    NONE_CONTAINS_IN_LIST("列表不包含指定字符串的任何部分", "seq.not_any(seq.set(%s), lambda(x) -> string.indexOf(str(%s), x) > -1 end)","2"),


    STRING_EQUAL("等于", "str(%s) == str(%s)","2"),

    STRING_NOT_EQUAL("不等于", "str(%s) != str(%s)","2"),

    STRING_ACCURACY("字符精度百分85", "similarity.contains(str(%s),str(%s),'%s')","2"),

    STRING_SECOND_ACCURACY("字符精度百分65", "secondSimilarity.contains(str(%s),str(%s),'%s')","2"),

    /**
     * contain specified characters
     */
    STRING_CONTAINS("包含指定字符", "string.contains(str(%s),str(%s))","2"),

    /**
     * not contain specified characters
     */
    STRING_NOT_CONTAINS("不包含指定字符", "!string.contains(str(%s),str(%s))","2"),

    /**
     * start with specified character
     */
    STRING_STARTSWITH("以指定字符开始", "string.startsWith(str(%s),str(%s))","2"),

    /**
     * not start with specified character
     */
    STRING_NOT_STARTSWITH("不以指定字符开始", "!string.startsWith(str(%s),str(%s))","2"),

    /**
     * ends with specified character
     */
    STRING_ENDSWITH("以指定字符结束", "!string.endsWith(str(%s),str(%s))","2"),

    /**
     * not end with specified character
     */
    STRING_NOT_ENDSWITH("不以指定字符结束", "!string.endsWith(str(%s),str(%s))","2"),

    /**
     * number interval
     * e.g. [1,10], (1,10), [1,10), (1,10]
     */
    INTERVAL_NUMBER("数值区间", "(%1$s %2$s %3$s && %1$s %4$s %5$s)","3"),

    /**
     * character length interval
     */
    INTERVAL_STRING_LENGTH("字符长度区间", "(string.length(str(%1$s)) %2$s %3$s && string.length(str(%1$s)) %4$s %5$s)","3"),

    /**
     * match regular expression
     */
    REGEX("正则", "str(%s) =~ %s","2");

    private final String desc;

    /**
     * The aviator expression corresponding to the condition relation operator
     */
    private final String value;


    private final String type;


    public static ConditionRelationType getEnum(String name) {
        return Arrays.stream(values()).filter(e -> e.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Map<String, String> getOptions(String type) {
        if(Objects.equals("0",type)){
            return Arrays.stream(values())
                    .collect(Collectors.toMap(Enum::name, e -> e.desc, (v1, v2) -> v2, LinkedHashMap::new));
        }
        return Arrays.stream(values()).filter(m-> Objects.equals(m.type,type))
                .collect(Collectors.toMap(Enum::name, e -> e.desc, (v1, v2) -> v2, LinkedHashMap::new));
    }
}
