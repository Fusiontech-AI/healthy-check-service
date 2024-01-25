package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjTeamGroupService;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-25 11:40
 **/
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.TEAM_GROUP_ID_TO_NAME)
public class TeamGroupNameTranslationImpl implements TranslationInterface<String> {

    private final RemoteTjTeamGroupService remoteTjTeamGroupService;

    @Override
    public String translation(Object key, String other) {
        if(key instanceof Long id){
            return remoteTjTeamGroupService.selectTeamGroupNameById(id);
        }

        return null;
    }
}
