package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjBasicProjectService;
import org.fxkc.peis.domain.TjBasicProject;
import org.fxkc.peis.mapper.TjBasicProjectMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjBasicProjectServiceImpl implements RemoteTjBasicProjectService {

    private final TjBasicProjectMapper tjBasicProjectMapper;

    @Override
    public String selectBasicNameById(Long id) {
        TjBasicProject tjBasicProject = tjBasicProjectMapper.selectById(id);
        return Objects.isNull(tjBasicProject) ? null:tjBasicProject.getBasicProjectName();
    }
}
