package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjTeamDeptService;
import org.fxkc.peis.api.RemoteTjTeamService;
import org.fxkc.peis.domain.TjTeamDept;
import org.fxkc.peis.domain.TjTeamGroup;
import org.fxkc.peis.mapper.TjTeamDeptMapper;
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
public class RemoteTjTeamDeptServiceImpl implements RemoteTjTeamDeptService {

    private final TjTeamDeptMapper teamDeptMapper;

    @Override
    public String selectTeamDeptNameById(Long id) {
        TjTeamDept teamDept = teamDeptMapper.selectById(id);
        return teamDept == null ? null:teamDept.getDeptName();
    }
}
