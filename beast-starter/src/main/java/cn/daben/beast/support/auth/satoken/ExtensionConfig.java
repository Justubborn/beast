package cn.daben.beast.support.auth.satoken;

import lombok.Data;

/**
 * @author Justubborn
 * @since 2024/6/26
 */
@Data
public class ExtensionConfig {
    /**
     * 是否启用扩展
     */
    private boolean enabled = false;

    /**
     * 启用 JWT
     */
    private boolean enableJwt = false;

    private final Dao dao = new Dao();
    
    private final Security security = new Security();

    @Data
    public static class Dao {
        private SaTokenDaoType type = SaTokenDaoType.DEFAULT;
    }

    @Data
    public static class Security {
        private String[] excludes = new String[0];
    }


}
