package cn.daben.beast.properties;

import cn.daben.beast.support.data.mybatis.plus.ExtensionConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MyBatis Plus 扩展配置属性
 *
 * @author Charles7c
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "mybatis-plus.extension")
public class MyBatisPlusExtensionProperties extends ExtensionConfig {


}
