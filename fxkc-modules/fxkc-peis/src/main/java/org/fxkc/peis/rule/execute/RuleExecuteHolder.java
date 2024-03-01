package org.fxkc.peis.rule.execute;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RuleExecuteHolder {

    private final Map<String, RuleExecuteService> registerHolder = new HashMap<>(4);

    public RuleExecuteService selectBuilder(String key) {
        return registerHolder.get(key);
    }

    public void putBuilder(String key, RuleExecuteService builder) {
        registerHolder.put(key, builder);
    }
}
