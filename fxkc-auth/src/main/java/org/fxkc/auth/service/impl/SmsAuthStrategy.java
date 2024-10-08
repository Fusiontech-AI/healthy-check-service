package org.fxkc.auth.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.fxkc.auth.domain.vo.LoginVo;
import org.fxkc.auth.form.SmsLoginBody;
import org.fxkc.auth.service.IAuthStrategy;
import org.fxkc.auth.service.SysLoginService;
import org.fxkc.common.core.constant.Constants;
import org.fxkc.common.core.constant.GlobalConstants;
import org.fxkc.common.core.enums.LoginType;
import org.fxkc.common.core.exception.user.CaptchaExpireException;
import org.fxkc.common.core.utils.MessageUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.core.utils.ValidatorUtils;
import org.fxkc.common.json.utils.JsonUtils;
import org.fxkc.common.redis.utils.RedisUtils;
import org.fxkc.common.satoken.utils.LoginHelper;
import org.fxkc.system.api.RemoteUserService;
import org.fxkc.system.api.domain.vo.RemoteClientVo;
import org.fxkc.system.api.model.LoginUser;
import org.springframework.stereotype.Service;

/**
 * 短信认证策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service("sms" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SmsAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;

    @DubboReference
    private RemoteUserService remoteUserService;

    @Override
    public LoginVo login(String body, RemoteClientVo client) {
        SmsLoginBody loginBody = JsonUtils.parseObject(body, SmsLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String tenantId = loginBody.getTenantId();
        String phonenumber = loginBody.getPhonenumber();
        String smsCode = loginBody.getSmsCode();

        // 通过手机号查找用户
        LoginUser loginUser = remoteUserService.getUserInfoByPhonenumber(phonenumber, tenantId);
        loginService.checkLogin(LoginType.SMS, tenantId, loginUser.getUsername(), () -> !validateSmsCode(tenantId, phonenumber, smsCode));
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        return loginVo;
    }

    /**
     * 校验短信验证码
     */
    private boolean validateSmsCode(String tenantId, String phonenumber, String smsCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + phonenumber);
        if (StringUtils.isBlank(code)) {
            loginService.recordLogininfor(tenantId, phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }

}
