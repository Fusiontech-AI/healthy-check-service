package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjPackageService;

/**
 * 套餐翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.PACKAGE_ID_TO_NAME)
public class PackageNameTranslationImpl implements TranslationInterface<String> {

    @DubboReference
    private RemoteTjPackageService remoteTjPackageService;

    @Override
    public String translation(Object key, String other) {
        return remoteTjPackageService.selectPackageNameById((Long) key);
    }
}
