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

package cn.daben.beast.autoconfigure.redis;

import lombok.Data;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson 配置属性
 *
 * @author gengwei.zheng（<a href="https://gitee.com/herodotus/dante-engine">Dante Engine</a>）
 * @author Charles7c
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "spring.data.redisson")
public class RedissonProperties {

    /**
     * Redis 模式
     */
    private Mode mode = Mode.SINGLE;

    /**
     * 单机服务配置
     */
    private SingleServerConfig singleServerConfig;

    /**
     * 集群服务配置
     */
    private ClusterServersConfig clusterServersConfig;

    /**
     * 哨兵服务配置
     */
    private SentinelServersConfig sentinelServersConfig;

    /**
     * Redis 模式
     */
    public enum Mode {
        /**
         * 单机
         */
        SINGLE,

        /**
         * 集群
         */
        CLUSTER,

        /**
         * 哨兵
         */
        SENTINEL
    }
}
