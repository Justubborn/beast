package cn.daben.beast.autoconfigure;

import cn.daben.beast.constant.StringConst;
import cn.daben.beast.properties.SaTokenExtensionProperties;
import cn.daben.beast.support.auth.satoken.SaTokenDaoConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.spring.SaTokenContextRegister;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 自动配置
 *
 * @author Charles7c
 * @since 1.0.0
 */
@ConditionalOnClass(SaTokenContextRegister.class)
@AutoConfiguration
@EnableConfigurationProperties(SaTokenExtensionProperties.class)
public class SaTokenAutoConfig extends AbstractAutoConfig implements WebMvcConfigurer {

    private final SaTokenExtensionProperties properties;

    public SaTokenAutoConfig(SaTokenExtensionProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SpringUtil.getBean(SaInterceptor.class)).addPathPatterns(StringConst.PATH_PATTERN);
    }

    /**
     * SaToken 拦截器配置
     */
    @Bean
    @ConditionalOnMissingBean
    public SaInterceptor saInterceptor() {
        return new SaInterceptor(handle -> SaRouter.match(StringConst.PATH_PATTERN)
            .notMatch(properties.getSecurity().getExcludes())
            .check(r -> StpUtil.checkLogin()));
    }

    /**
     * 持久层配置
     */
    @Configuration
    @Import({SaTokenDaoConfig.Default.class, SaTokenDaoConfig.Custom.class})
    protected static class SaTokenDaoAutoConfiguration {
    }

    /**
     * 整合 JWT（简单模式）
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "sa-token.extension", name = "enableJwt", havingValue = "true")
    public StpLogic stpLogic() {
        return new StpLogicJwtForSimple();
    }
}
