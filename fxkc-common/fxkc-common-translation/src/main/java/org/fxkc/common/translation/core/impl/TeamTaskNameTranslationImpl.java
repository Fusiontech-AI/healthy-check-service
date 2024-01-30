package org.fxkc.common.translation.core.impl;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.translation.annotation.TranslationType;
import org.fxkc.common.translation.constant.TransConstant;
import org.fxkc.common.translation.core.TranslationInterface;
import org.fxkc.peis.api.RemoteTjTeamTaskService;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-25 11:50
 **/
@AllArgsConstructor
@TranslationType(type = TransConstant.TEAM_TASK_ID_TO_NAME)
public class TeamTaskNameTranslationImpl implements TranslationInterface<String> {

    @DubboReference
    private final RemoteTjTeamTaskService remoteTjTeamTaskService;

    @Override
    public String translation(Object key, String other) {
        if(key instanceof Long id){
            return remoteTjTeamTaskService.selectTeamTaskNameById(id);
        }

        return null;
    }
}
