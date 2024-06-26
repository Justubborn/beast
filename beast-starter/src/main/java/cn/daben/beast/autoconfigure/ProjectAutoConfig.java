package cn.daben.beast.autoconfigure;

import cn.daben.beast.base.ApiRestController;
import cn.daben.beast.properties.ProjectProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Justubborn
 * @since 2024/6/25
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(ProjectProperties.class)
public class ProjectAutoConfig extends AbstractAutoConfig {
    @Bean
    WebMvcConfigurer enableMessageConverters() {
        return new WebMvcConfigurer() {

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(ApiRestController.class));
            }
        };
    }

}
