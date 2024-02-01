package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjCombinationProjectService;
import org.fxkc.peis.service.ITjCombinationProjectService;
import org.springframework.stereotype.Service;

@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjCombinationProjectServiceImpl implements RemoteTjCombinationProjectService {

    private final ITjCombinationProjectService tjCombinationProjectService;

    @Override
    public String selectCombinationNameById(Long id) {
        return tjCombinationProjectService.selectCombinationNameById(id);
    }

    @Override
    public String selectCombinationCodeById(Long id) {
        return tjCombinationProjectService.selectCombinationCodeById(id);
    }
}
