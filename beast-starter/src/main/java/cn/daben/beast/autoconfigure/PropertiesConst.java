package cn.daben.beast.autoconfigure;

import cn.daben.beast.constant.StringConst;

/**
 * 配置属性相关常量
 *
 * @author Charles7c
 * @since 1.1.1
 */
public interface PropertiesConst {

    /**
     * ContiNew Starter
     */
    String BEAST = "beast";

    /**
     * 启用配置
     */
    String ENABLED = "enabled";

    /**
     * 线程池配置
     */
    String THREAD_POOL = BEAST + StringConst.DOT + "thread-pool";

    /**
     * Spring Doc 配置
     */
    String SPRINGDOC = "springdoc";

    /**
     * Spring Doc Swagger UI 配置
     */
    String SPRINGDOC_SWAGGER_UI = SPRINGDOC + StringConst.DOT + "swagger-ui";

    /**
     * 安全配置
     */
    String SECURITY = BEAST + StringConst.DOT + "security";

    /**
     * 密码编解码配置
     */
    String PASSWORD = SECURITY + StringConst.DOT + "password";

    /**
     * 加/解密配置
     */
    String CRYPTO = SECURITY + StringConst.DOT + "crypto";

    /**
     * Web 配置
     */
    String WEB = BEAST + StringConst.DOT + "web";

    /**
     * 跨域配置
     */
    String CORS = WEB + StringConst.DOT + "cors";

    /**
     * 链路配置
     */
    String TRACE = WEB + StringConst.DOT + "trace";

    /**
     * XSS 配置
     */
    String XSS = WEB + StringConst.DOT + "xss";

    /**
     * 日志配置
     */
    String LOG = BEAST + StringConst.DOT + "log";

    /**
     * 存储配置
     */
    String STORAGE = BEAST + StringConst.DOT + "storage";

    /**
     * 本地存储配置
     */
    String STORAGE_LOCAL = STORAGE + StringConst.DOT + "local";

    /**
     * 验证码配置
     */
    String CAPTCHA = BEAST + StringConst.DOT + "captcha";

    /**
     * 图形验证码配置
     */
    String CAPTCHA_GRAPHIC = CAPTCHA + StringConst.DOT + "graphic";

    /**
     * 行为验证码配置
     */
    String CAPTCHA_BEHAVIOR = CAPTCHA + StringConst.DOT + "behavior";

    /**
     * 消息配置
     */
    String MESSAGING = BEAST + StringConst.DOT + "messaging";

    /**
     * WebSocket 配置
     */
    String MESSAGING_WEBSOCKET = MESSAGING + StringConst.DOT + "websocket";

    /**
     * 国际化配置
     */
    public static final String I18N = WEB + StringConst.DOT + "i18n";
}
