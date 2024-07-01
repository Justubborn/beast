package cn.daben.beast.autoconfigure.livekit;

import io.livekit.server.AccessToken;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Justubborn
 * @since 2024/7/1
 */
@ConditionalOnClass(AccessToken.class)
@AutoConfiguration
@EnableConfigurationProperties(LiveKitProperties.class)
public class LiveKitAutoConfig {
    
}
