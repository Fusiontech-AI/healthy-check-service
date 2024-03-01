package org.fxkc.peis.rule.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RulesetMode {
    BUILDING(0),

    BUILT(1);

    private final Integer code;
}
