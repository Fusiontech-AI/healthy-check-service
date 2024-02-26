package org.fxkc.peis.register.change;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterChangeHolder {

    private final Map<String, RegisterChangeService> registerHolder = new HashMap<>(4);

    public RegisterChangeService selectBuilder(String key) {
        return registerHolder.get(key);
    }

    public void putBuilder(String key, RegisterChangeService builder) {
        registerHolder.put(key, builder);
    }
}
