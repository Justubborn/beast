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

import cn.daben.beast.constant.PropertiesConst;
import cn.daben.beast.constant.StringConst;
import cn.daben.beast.properties.CorsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域自动配置
 *
 * @author Charles7c
 * @since 1.0.0
 */
@Slf4j
@Lazy
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = PropertiesConst.CORS, name = PropertiesConst.ENABLED, havingValue = "true")
@EnableConfigurationProperties(CorsProperties.class)
public class CorsAutoConfiguration extends AbstractAutoConfig {

    /**
     * 跨域过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter(CorsProperties properties) {
        CorsConfiguration config = new CorsConfiguration();
        // 设置跨域允许时间
        config.setMaxAge(1800L);
        // 配置允许跨域的域名
        if (properties.getAllowedOrigins().contains(StringConst.ASTERISK)) {
            config.addAllowedOriginPattern(StringConst.ASTERISK);
        } else {
            // 配置为 true 后则必须配置允许跨域的域名，且不允许配置为 *
            config.setAllowCredentials(true);
            properties.getAllowedOrigins().forEach(config::addAllowedOrigin);
        }
        // 配置允许跨域的请求方式
        properties.getAllowedMethods().forEach(config::addAllowedMethod);
        // 配置允许跨域的请求头
        properties.getAllowedHeaders().forEach(config::addAllowedHeader);
        // 配置允许跨域的响应头
        properties.getExposedHeaders().forEach(config::addExposedHeader);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(StringConst.PATH_PATTERN, config);
        return new CorsFilter(source);
    }
}
