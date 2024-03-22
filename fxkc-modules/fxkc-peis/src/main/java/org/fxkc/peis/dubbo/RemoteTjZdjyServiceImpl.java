package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjZdjyService;
import org.fxkc.peis.domain.TjZdjywh;
import org.fxkc.peis.mapper.TjZdjywhMapper;
import org.springframework.stereotype.Service;

@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjZdjyServiceImpl implements RemoteTjZdjyService {

    private final TjZdjywhMapper zdjywhMapper;


    @Override
    public String selectZdjyNameById(Long id) {
        TjZdjywh tjZdjywh = zdjywhMapper.selectById(id);
        return tjZdjywh == null ? null:tjZdjywh.getJymc();
    }
}
