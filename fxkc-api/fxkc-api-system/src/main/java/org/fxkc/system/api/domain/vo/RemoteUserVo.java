package org.fxkc.system.api.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 租户视图对象
 *
 * @author zhujie
 */
@Data
public class RemoteUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型（sys_user系统用户）
     */
    private String userType;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像
     */
    private Long avatar;

    /**
     * 备注
     */
    private String remark;

}
