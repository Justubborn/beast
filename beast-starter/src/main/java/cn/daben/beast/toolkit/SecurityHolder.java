package cn.daben.beast.toolkit;

import cn.daben.beast.base.SessionMeta;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取当前登录的用户
 */
@Slf4j
public class SecurityHolder {
    public static final String META = "meta";
    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public static SessionMeta getSessionMeta() {
        return StpUtil.getSession().get(META, () -> {
            SessionMeta meta = new SessionMeta();
            meta.setUserId(-1L);
            meta.setUserName("anonymous");
            return meta;
        });
    }


    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        SessionMeta meta = getSessionMeta();
        return meta.getUserName();
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        SessionMeta meta = getSessionMeta();
        return meta.getUserId();
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    public static List<Long> getCurrentUserDataScope() {
//        UserDetails userDetails = getCurrentUser();
//        return JsonKit.toList((String) JsonKit.toMap(userDetails).get("dataScopes"),Long.class);
        return new ArrayList<>();
    }

    public static String getCurrentUserOrgId() {
//        UserDetails userDetails = getCurrentUser();
//
//        return (String) JsonKit.toMap(JsonKit.toMap(userDetails).get("user")).get("org_id");
        return null;
    }

}
