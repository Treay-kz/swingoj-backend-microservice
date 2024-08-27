package com.treay.swingojbackendcommon.constant;

/**
 * 用户常量
 *
 * @author Treay
 */
public interface UserConstant {

    /**
     * 盐值，混淆密码
     */
    String SALT = "treay";

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";


    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    /**
     * 默认头像
     */
    String USER_DEFAULT_AVATAR = "https://swingimages.oss-cn-shenzhen.aliyuncs.com/TyporaImages/46.jpg";
}
