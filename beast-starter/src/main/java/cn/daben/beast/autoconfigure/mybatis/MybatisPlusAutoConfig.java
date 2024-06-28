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

package cn.daben.beast.autoconfigure.mybatis;

import cn.daben.beast.autoconfigure.AbstractAutoConfig;
import cn.daben.beast.autoconfigure.PropertiesConst;
import cn.daben.beast.support.data.mybatis.MyBatisPlusMetaObjectHandler;
import cn.daben.beast.support.data.mybatis.plus.MyBatisPlusCosIdIdentifierGenerator;
import cn.daben.beast.support.data.mybatis.plus.datapermission.DataPermissionFilter;
import cn.daben.beast.support.data.mybatis.plus.datapermission.DataPermissionHandlerImpl;
import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.IdGenerator;

/**
 * MyBatis Plus 自动配置
 *
 * @author Charles7c
 * @since 1.0.0
 */
@Slf4j
@AutoConfiguration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableConfigurationProperties(MyBatisPlusExtensionProperties.class)
@ConditionalOnProperty(prefix = "mybatis-plus.extension", name = PropertiesConst.ENABLED, havingValue = "true")
public class MybatisPlusAutoConfig extends AbstractAutoConfig {

    /**
     * 元对象处理器配置（插入或修改时自动填充）
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyBatisPlusMetaObjectHandler();
    }

    /**
     * 自定义 ID 生成器-默认（雪花算法，使用网卡信息绑定雪花生成器，防止集群雪花 ID 重复）
     */
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    @ConditionalOnProperty(name = "mybatis-plus.extension.id-generator.type", havingValue = "default", matchIfMissing = true)
    public static class Default {
        static {
            log.info("[Beast Starter] - Auto Configuration 'MyBatis Plus-IdGenerator-Default' completed initialization.");
        }

        @Bean
        public IdentifierGenerator identifierGenerator() {
            return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
        }
    }

    /**
     * 自定义 ID 生成器-CosId
     */
    @ConditionalOnMissingBean(IdentifierGenerator.class)
    @ConditionalOnClass(IdGenerator.class)
    @ConditionalOnProperty(name = "mybatis-plus.extension.id-generator.type", havingValue = "cosid")
    public static class CosId {
        static {
            log.info("[Beast Starter] - Auto Configuration 'MyBatis Plus-IdGenerator-CosId' completed initialization.");
        }

        @Bean
        public IdentifierGenerator identifierGenerator() {
            return new MyBatisPlusCosIdIdentifierGenerator();
        }
    }

    /**
     * 自定义 ID 生成器
     */
    @ConditionalOnProperty(name = "mybatis-plus.extension.id-generator.type", havingValue = "custom")
    public static class Custom {
        @Bean
        @ConditionalOnMissingBean
        public IdentifierGenerator identifierGenerator() {
            if (log.isErrorEnabled()) {
                log.error("Consider defining a bean of type '{}' in your configuration.", ResolvableType
                        .forClass(IdentifierGenerator.class));
            }
            throw new NoSuchBeanDefinitionException(IdentifierGenerator.class);
        }
    }

    /**
     * MyBatis Plus 插件配置
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MyBatisPlusExtensionProperties properties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限插件
        MyBatisPlusExtensionProperties.DataPermission dataPermissionProperties = properties
                .getDataPermission();
        if (null != dataPermissionProperties && dataPermissionProperties.isEnabled()) {
            interceptor.addInnerInterceptor(new DataPermissionInterceptor(SpringUtil
                    .getBean(DataPermissionHandler.class)));
        }
        // 分页插件
        MyBatisPlusExtensionProperties.Pagination pagination = properties.getPagination();
        if (null != pagination && pagination.isEnabled()) {
            interceptor.addInnerInterceptor(this.paginationInnerInterceptor(pagination));
        }
        // 防全表更新与删除插件
        if (properties.isBlockAttackPluginEnabled()) {
            interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        }
        return interceptor;
    }

    /**
     * 数据权限处理器
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "mybatis-plus.extension.data-permission", name = PropertiesConst.ENABLED, havingValue = "true")
    public DataPermissionHandler dataPermissionHandler(DataPermissionFilter dataPermissionFilter) {
        return new DataPermissionHandlerImpl(dataPermissionFilter);
    }

    /**
     * 分页插件配置（<a href="https://baomidou.com/pages/97710a/#paginationinnerinterceptor">PaginationInnerInterceptor</a>）
     */
    private PaginationInnerInterceptor paginationInnerInterceptor(MyBatisPlusExtensionProperties.Pagination paginationProperties) {
        // 对于单一数据库类型来说，都建议配置该值，避免每次分页都去抓取数据库类型
        PaginationInnerInterceptor paginationInnerInterceptor = null != paginationProperties.getDbType()
                ? new PaginationInnerInterceptor(paginationProperties.getDbType())
                : new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(paginationProperties.isOverflow());
        paginationInnerInterceptor.setMaxLimit(paginationProperties.getMaxLimit());
        return paginationInnerInterceptor;
    }

}
