package org.fxkc.peis.rule.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Condition logic operator type
 */
@Getter
@AllArgsConstructor
public enum ParamType {

    /**
     * if current condition evaluates to true, continue to execute backwards,
     * otherwise skip the following condition and return false directly
     */
    xmValue("体检项目值", "xmValue"),

    age("年龄", "age"),

    isPositive("阳性", "isPositive"),
    isAbnormal("范围", "isAbnormal"),
    /**
     * If current condition evaluates to true, skip the following condition and return true directly,
     * otherwise continue to execute backward.
     */
    sex("性别", "sex");

    private final String desc;
    private final String value;

    public static ParamType getEnum(String name) {
        return Arrays.stream(values()).filter(t -> t.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Map<String, String> getOptions() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(Enum::name, e -> e.desc, (v1, v2) -> v2, LinkedHashMap::new));
    }
}
