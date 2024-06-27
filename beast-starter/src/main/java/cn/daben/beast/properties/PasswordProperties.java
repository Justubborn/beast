package cn.daben.beast.properties;

import cn.daben.beast.constant.PropertiesConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Justubborn
 * @since 2024/6/26
 */
@Data
@ConfigurationProperties(PropertiesConst.PASSWORD)
public class PasswordProperties {
    /**
     * 是否启用密码编解码配置
     */
    private boolean enabled = true;

    /**
     * 默认启用的编码器 ID（默认：BCryptPasswordEncoder）
     */
    private String encodingId = "bcrypt";

}
