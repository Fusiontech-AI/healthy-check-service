package org.fxkc.common.log.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import domain.RemoteTjRecordLogBo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.common.core.constant.Constants;
import org.fxkc.common.core.utils.ServletUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.core.utils.ip.AddressUtils;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.peis.api.RemoteTjRecordLogService;
import org.fxkc.system.api.RemoteClientService;
import org.fxkc.system.api.RemoteLogService;
import org.fxkc.system.api.domain.bo.RemoteLogininforBo;
import org.fxkc.system.api.domain.bo.RemoteOperLogBo;
import org.fxkc.system.api.domain.vo.RemoteClientVo;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步调用日志服务
 *
 * @author fxkc
 */
@Component
@Slf4j
public class LogEventListener {

    @DubboReference
    private RemoteLogService remoteLogService;
    @DubboReference
    private RemoteClientService remoteClientService;
    @DubboReference
    private RemoteTjRecordLogService remoteTjRecordLogService;


    /**
     * 保存系统日志记录
     */
    @Async
    @EventListener
    public void saveLog(OperLogEvent operLogEvent) {
        RemoteOperLogBo sysOperLog = BeanUtil.toBean(operLogEvent, RemoteOperLogBo.class);
        remoteLogService.saveLog(sysOperLog);
    }

    @Async
    @EventListener
    public void saveLogininfor(LogininforEvent logininforEvent) {
        HttpServletRequest request = logininforEvent.getRequest();
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);
        // 客户端信息
        String clientid = request.getHeader(LoginHelper.CLIENT_KEY);
        RemoteClientVo clientVo = null;
        if (StringUtils.isNotBlank(clientid)) {
            clientVo = remoteClientService.queryByClientId(clientid);
        }

        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(logininforEvent.getUsername()));
        s.append(getBlock(logininforEvent.getStatus()));
        s.append(getBlock(logininforEvent.getMessage()));
        // 打印信息到日志
        log.info(s.toString(), logininforEvent.getArgs());
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        RemoteLogininforBo logininfor = new RemoteLogininforBo();
        logininfor.setTenantId(logininforEvent.getTenantId());
        logininfor.setUserName(logininforEvent.getUsername());
        if (ObjectUtil.isNotNull(clientVo)) {
            logininfor.setClientKey(clientVo.getClientKey());
            logininfor.setDeviceType(clientVo.getDeviceType());
        }
        logininfor.setIpaddr(ip);
        logininfor.setLoginLocation(address);
        logininfor.setBrowser(browser);
        logininfor.setOs(os);
        logininfor.setMsg(logininforEvent.getMessage());
        // 日志状态
        if (StringUtils.equalsAny(logininforEvent.getStatus(), Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(logininforEvent.getStatus())) {
            logininfor.setStatus(Constants.FAIL);
        }
        remoteLogService.saveLogininfor(logininfor);
    }

    /**
     * 保存体检操作记录日志
     */
    @Async
    @EventListener
    public void saveTjRecordLog(TjRecordLogEvent tjRecordLogEvent) {
        RemoteTjRecordLogBo recordLogBo = BeanUtil.toBean(tjRecordLogEvent, RemoteTjRecordLogBo.class);
        remoteTjRecordLogService.saveTjRecordLog(recordLogBo);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg + "]";
    }

}
