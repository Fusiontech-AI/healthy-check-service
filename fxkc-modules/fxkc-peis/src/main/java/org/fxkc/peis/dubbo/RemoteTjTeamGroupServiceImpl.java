package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjTeamGroupService;
import org.fxkc.peis.api.RemoteTjTeamService;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.mapper.TjTeamGroupMapper;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zry
 * @date: 2024-01-25 11:45
 **/
@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjTeamGroupServiceImpl implements RemoteTjTeamGroupService {

    private final TjTeamGroupMapper teamGroupMapper;

    @Override
    public String selectTeamGroupNameById(Long id) {
        TjTeamGroup tjTeamGroup = teamGroupMapper.selectById(id);
        return tjTeamGroup == null ? null:tjTeamGroup.getGroupName();
    }
}
