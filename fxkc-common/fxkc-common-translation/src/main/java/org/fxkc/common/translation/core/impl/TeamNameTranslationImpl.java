package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjTeamService;
import org.fxkc.system.api.RemoteDeptService;

/**
 * 部门翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.TEAM_ID_TO_NAME)
public class TeamNameTranslationImpl implements TranslationInterface<String> {

    @DubboReference
    private RemoteTjTeamService remoteTjTeamService;

    @Override
    public String translation(Object key, String other) {
        return remoteTjTeamService.selectTeamNameById((Long) key);
    }
}
