package cn.daben.beast.autoconfigure.livekit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Justubborn
 * @since 2024/7/1
 */
@Data
@ConfigurationProperties(prefix = "livekit")
public class LiveKitProperties {
    /**
     * 主机
     */
    private String host = "127.0.0.1：7880";
    private String appKey;
    
    private String appSecret;
}
