package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjTeamDeptService;
import org.fxkc.peis.api.RemoteTjTeamGroupService;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-25 11:50
 **/
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.TEAM_DEPT_ID_TO_NAME)
public class TeamDeptNameTranslationImpl implements TranslationInterface<String> {

    private final RemoteTjTeamDeptService remoteTjTeamDeptService;

    @Override
    public String translation(Object key, String other) {
        if(key instanceof Long id){
            return remoteTjTeamDeptService.selectTeamDeptNameById(id);
        }

        return null;
    }
}
