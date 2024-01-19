package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjTeamService;
import org.fxkc.peis.service.ITjTeamInfoService;
import org.springframework.stereotype.Service;

@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjTeamServiceImpl implements RemoteTjTeamService {

    private final ITjTeamInfoService iTjTeamInfoService;

    @Override
    public String selectTeamNameById(Long id) {
        return iTjTeamInfoService.selectTeamNameById(id);
    }
}
