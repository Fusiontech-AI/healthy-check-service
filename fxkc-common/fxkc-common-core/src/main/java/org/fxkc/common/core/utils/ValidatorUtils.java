package org.fxkc.common.core.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator 校验框架工具
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtils {

    private static final Validator VALID = SpringUtils.getBean(Validator.class);

    public static <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = VALID.validate(object, groups);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException("参数校验异常", validate);
        }
    }

    public static <T> String validateMsg(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = VALID.validate(object, groups);
        if (!validate.isEmpty()) {
            return validate.stream().filter(Objects::nonNull).map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(StrUtil.COMMA));
        }
        return null;
    }

}
