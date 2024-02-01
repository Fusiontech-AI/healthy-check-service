package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjCombinationProjectService;

/**
 * 部门翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.COMBINATION_ID_TO_CODE)
public class CombinationCodeTranslationImpl implements TranslationInterface<String> {

    @DubboReference
    private RemoteTjCombinationProjectService remoteTjCombinationProjectService;

    @Override
    public String translation(Object key, String other) {
        return remoteTjCombinationProjectService.selectCombinationCodeById((Long) key);
    }
}
