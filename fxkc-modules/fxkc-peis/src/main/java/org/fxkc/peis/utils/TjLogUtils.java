package org.fxkc.peis.utils;

import lombok.extern.slf4j.Slf4j;
import org.fxkc.common.core.utils.SpringUtils;
import org.fxkc.common.log.event.TjRecordLogEvent;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.system.api.model.LoginUser;
import org.springframework.stereotype.Component;

/**
 * 所有的日志都存在
 *
 * @author 3y
 */
@Slf4j
@Component
public class TjLogUtils  {

    /**
     * 记录打点信息
     */
    public void print(TjRecordLogEvent tjRecordLogEvent) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        tjRecordLogEvent.setCreateName(loginUser.getUsername());
        // 发布事件保存数据库
        SpringUtils.context().publishEvent(tjRecordLogEvent);
    }

}
