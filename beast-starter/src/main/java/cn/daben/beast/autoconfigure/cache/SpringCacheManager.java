/**
 * Copyright (c) 2013-2021 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.daben.beast.autoconfigure.cache;

import cn.daben.beast.toolkit.RedisKit;
import lombok.Setter;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonCache;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A {@link CacheManager} implementation
 * backed by Redisson instance.
 * <p>
 * 修改 RedissonSpringCacheManager 源码
 * 重写 cacheName 处理方法 支持多参数
 *
 * @author Nikita Koksharov
 */
@Setter
public class SpringCacheManager implements CacheManager {

    private boolean dynamic = true;

    private boolean allowNullValues = true;

    private boolean transactionAware = true;

    Map<String, CacheConfig> configMap = new ConcurrentHashMap<>();
    ConcurrentMap<String, Cache> instanceMap = new ConcurrentHashMap<>();

    /**
     * Defines 'fixed' cache names.
     * A new cache instance will not be created in dynamic for non-defined names.
     * <p>
     * `null` parameter setups dynamic mode
     *
     * @param names of caches
     */
    public void setCacheNames(Collection<String> names) {
        if (names != null) {
            for (String name : names) {
                getCache(name);
            }
            dynamic = false;
        } else {
            dynamic = true;
        }
    }

    /**
     * Set cache config mapped by cache name
     *
     * @param config object
     */
    public void setConfig(Map<String, ? extends CacheConfig> config) {
        this.configMap = (Map<String, CacheConfig>) config;
    }

    protected CacheConfig createDefaultConfig() {
        return new CacheConfig();
    }

    @Override
    public Cache getCache(String name) {
        // 重写 cacheName 支持多参数
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];

        Cache cache = instanceMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic) {
            return cache;
        }

        CacheConfig config = configMap.get(name);
        if (config == null) {
            config = createDefaultConfig();
            configMap.put(name, config);
        }

        if (array.length > 1) {
            config.setTTL(DurationStyle.detectAndParse(array[1]).toMillis());
        }
        if (array.length > 2) {
            config.setMaxIdleTime(DurationStyle.detectAndParse(array[2]).toMillis());
        }
        if (array.length > 3) {
            config.setMaxSize(Integer.parseInt(array[3]));
        }

        if (config.getMaxIdleTime() == 0 && config.getTTL() == 0 && config.getMaxSize() == 0) {
            return createMap(name, config);
        }

        return createMapCache(name, config);
    }

    private Cache createMap(String name, CacheConfig config) {
        RMap<Object, Object> map = RedisKit.getClient().getMap(name);

        Cache cache = new CaffeineCacheDecorator(new RedissonCache(map, allowNullValues));
        if (transactionAware) {
            cache = new TransactionAwareCacheDecorator(cache);
        }
        Cache oldCache = instanceMap.putIfAbsent(name, cache);
        if (oldCache != null) {
            cache = oldCache;
        }
        return cache;
    }

    private Cache createMapCache(String name, CacheConfig config) {
        RMapCache<Object, Object> map = RedisKit.getClient().getMapCache(name);

        Cache cache = new CaffeineCacheDecorator(new RedissonCache(map, config, allowNullValues));
        if (transactionAware) {
            cache = new TransactionAwareCacheDecorator(cache);
        }
        Cache oldCache = instanceMap.putIfAbsent(name, cache);
        if (oldCache != null) {
            cache = oldCache;
        } else {
            map.setMaxSize(config.getMaxSize());
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(configMap.keySet());
    }


}
