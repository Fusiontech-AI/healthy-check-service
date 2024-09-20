package org.fxkc.peis.dubbo;

import cn.hutool.core.bean.BeanUtil;
import domain.RemoteTjRecordLogBo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.fxkc.peis.api.RemoteTjRecordLogService;
import org.fxkc.peis.domain.TjRecordLog;
import org.fxkc.peis.mapper.TjRecordLogMapper;
import org.springframework.stereotype.Service;

@Service
@DubboService
@RequiredArgsConstructor
public class RemoteTjRecordLogServiceImpl implements RemoteTjRecordLogService {

    private final TjRecordLogMapper tjRecordLogMapper;


    @Override
    public void saveTjRecordLog(RemoteTjRecordLogBo tjRecordLogBo) {
        TjRecordLog tjRecordLog = BeanUtil.toBean(tjRecordLogBo, TjRecordLog.class);
        tjRecordLogMapper.insert(tjRecordLog);
    }
}
