package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjBasicProjectService;

/**
 * 套餐翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.BASIC_ID_TO_NAME)
public class BasicNameTranslationImpl implements TranslationInterface<String> {

    @DubboReference
    private RemoteTjBasicProjectService remoteTjBasicProjectService;

    @Override
    public String translation(Object key, String other) {
        return remoteTjBasicProjectService.selectBasicNameById((Long) key);
    }
}
