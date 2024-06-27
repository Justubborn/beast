package cn.daben.beast.properties;

import cn.daben.beast.constant.PropertiesConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Justubborn
 * @since 2024/3/20
 */
@Data
@ConfigurationProperties(PropertiesConst.CRYPTO)
public class CryptoProperties {
    private boolean enabled = true;
    private String password;
    private String publicKey;
    private String privateKey;

}
