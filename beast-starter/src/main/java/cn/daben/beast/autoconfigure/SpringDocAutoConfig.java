/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.daben.beast.autoconfigure;

import cn.daben.beast.properties.ProjectProperties;
import cn.daben.beast.properties.SpringDocProperties;
import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * API 文档自动配置
 *
 */
@AutoConfiguration
@EnableConfigurationProperties(SpringDocProperties.class)
public class SpringDocAutoConfig extends AbstractAutoConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCacheControl(CacheControl.maxAge(5, TimeUnit.HOURS).cachePublic());
    }

    /**
     * Open API 配置
     */
    @Bean
    public OpenAPI openApi(ProjectProperties projectProperties, SpringDocProperties springDocProperties) {
        Info info = new Info().title("%s %s".formatted(projectProperties.getName(), "API 文档"))
                .version(projectProperties.getVersion())
                .description(projectProperties.getDescription());
        ProjectProperties.Contact contact = projectProperties.getContact();
        if (null != contact) {
            info.contact(new Contact().name(contact.getName()).email(contact.getEmail()).url(contact.getUrl()));
        }
        ProjectProperties.License license = projectProperties.getLicense();
        if (null != license) {
            info.license(new License().name(license.getName()).url(license.getUrl()));
        }
        OpenAPI openApi = new OpenAPI();
        openApi.info(info);
        Components components = springDocProperties.getComponents();
        if (null != components) {
            openApi.components(components);
            // 鉴权配置
            Map<String, SecurityScheme> securitySchemeMap = components.getSecuritySchemes();
            if (MapUtil.isNotEmpty(securitySchemeMap)) {
                SecurityRequirement securityRequirement = new SecurityRequirement();
                List<String> list = securitySchemeMap.values().stream().map(SecurityScheme::getName).toList();
                list.forEach(securityRequirement::addList);
                openApi.addSecurityItem(securityRequirement);
            }
        }
        return openApi;
    }

    /**
     * 全局自定义配置（全局添加鉴权参数）
     */
    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer(SpringDocProperties property) {
        return openApi -> {
            if (null == openApi.getPaths()) {
                return;
            }
            openApi.getPaths().forEach((s, pathItem) -> {
                // 为所有接口添加鉴权
                Components components = property.getComponents();

                if (null != components && MapUtil.isNotEmpty(components.getSecuritySchemes())) {
                    Map<String, SecurityScheme> securitySchemeMap = components.getSecuritySchemes();
                    pathItem.readOperations().forEach(operation -> {
                        SecurityRequirement securityRequirement = new SecurityRequirement();
                        List<String> list = securitySchemeMap.values()
                                .stream()
                                .map(SecurityScheme::getName)
                                .toList();
                        list.forEach(securityRequirement::addList);
                        operation.addSecurityItem(securityRequirement);
                    });
                }
            });
        };
    }


}
