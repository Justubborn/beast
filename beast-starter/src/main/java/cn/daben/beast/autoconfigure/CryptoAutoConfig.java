package cn.daben.beast.autoconfigure;

import cn.daben.beast.constant.PropertiesConst;
import cn.daben.beast.support.data.mybatis.MyBatisDecryptInterceptor;
import cn.daben.beast.support.data.mybatis.MyBatisEncryptInterceptor;
import cn.daben.beast.properties.CryptoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 加密配置
 *
 * @author Justubborn
 * @since 2024/3/20
 */
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = PropertiesConst.CRYPTO, name = PropertiesConst.ENABLED, matchIfMissing = true)
@AutoConfiguration
@EnableConfigurationProperties(CryptoProperties.class)
public class CryptoAutoConfig extends AbstractAutoConfig {

    private final CryptoProperties properties;

    /**
     * MyBatis 加密拦截器配置
     */
    @Bean
    @ConditionalOnMissingBean(MyBatisEncryptInterceptor.class)
    public MyBatisEncryptInterceptor myBatisEncryptInterceptor() {
        return new MyBatisEncryptInterceptor(properties);
    }

    /**
     * MyBatis 解密拦截器配置
     */
    @Bean
    @ConditionalOnMissingBean(MyBatisDecryptInterceptor.class)
    public MyBatisDecryptInterceptor myBatisDecryptInterceptor() {
        return new MyBatisDecryptInterceptor(properties);
    }
}
