package org.fxkc.peis.dubbo;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjPackageService;
import org.fxkc.peis.domain.TjPackage;
import org.fxkc.peis.mapper.TjPackageMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjPackageServiceImpl implements RemoteTjPackageService {

    private final TjPackageMapper tjPackageMapper;


    @Override
    public String selectPackageNameById(Long id) {
        TjPackage tjPackage = tjPackageMapper.selectById(id);
        return Objects.isNull(tjPackage) ? null:tjPackage.getPackageName();    }
}
