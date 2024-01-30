package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjTeamTaskService;
import org.fxkc.peis.domain.TjTeamTask;
import org.fxkc.peis.mapper.TjTeamTaskMapper;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-25 11:45
 **/
@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjTeamTaskServiceImpl implements RemoteTjTeamTaskService {

    private final TjTeamTaskMapper teamTaskMapper;

    @Override
    public String selectTeamTaskNameById(Long id) {
        TjTeamTask tjTeamTask = teamTaskMapper.selectById(id);
        return tjTeamTask == null ? null:tjTeamTask.getTaskName();
    }
}
