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

package cn.daben.beast.properties;

import cn.daben.beast.constant.PropertiesConst;
import cn.daben.beast.constant.StringConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 跨域配置属性
 *
 * @author Charles7c
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(PropertiesConst.CORS)
public class CorsProperties {

    private static final List<String> ALL = Collections.singletonList(StringConst.ASTERISK);

    /**
     * 是否启用跨域配置
     */
    private boolean enabled = false;

    /**
     * 允许跨域的域名
     */
    private List<String> allowedOrigins = new ArrayList<>(ALL);

    /**
     * 允许跨域的请求方式
     */
    private List<String> allowedMethods = new ArrayList<>(ALL);

    /**
     * 允许跨域的请求头
     */
    private List<String> allowedHeaders = new ArrayList<>(ALL);

    /**
     * 允许跨域的响应头
     */
    private List<String> exposedHeaders = new ArrayList<>();
}
