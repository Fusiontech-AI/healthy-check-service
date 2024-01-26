package org.fxkc.peis.register.insert;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterInsertHolder {

    private final Map<String, RegisterInsertService> registerHolder = new HashMap<>(4);

    public RegisterInsertService selectBuilder(String key) {
        return registerHolder.get(key);
    }

    public void putBuilder(String key, RegisterInsertService builder) {
        registerHolder.put(key, builder);
    }
}
