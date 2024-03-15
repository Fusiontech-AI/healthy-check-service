package org.fxkc.peis.api;

import domain.RemoteTjRecordLogBo;

/**
 * 体检操作记录日志服务
 *
 * @author Lion Li
 */
public interface RemoteTjRecordLogService {

    /**
     * 保存体检操作记录日志
     */
    void saveTjRecordLog(RemoteTjRecordLogBo tjRecordLogBo);

}
