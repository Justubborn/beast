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

package cn.daben.beast.toolkit;


import cn.daben.beast.constant.StringConst;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * Redis 工具类
 *
 * @author Charles7c
 * @since 1.0.0
 */
public class CacheKit {

    public final static TimedCache<String, Object> CLIENT = CacheUtil.newTimedCache(999999999999999L);

    private CacheKit() {
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public static <T> void set(String key, T value) {
        CLIENT.put(key,value);
    }

    /**
     * 设置缓存
     *
     * @param key      键
     * @param value    值
     * @param duration 过期时间
     */
    public static <T> void set(String key, T value, Duration duration) {
        CLIENT.put(key,value,duration.getSeconds());
    }

    /**
     * 查询指定缓存
     *
     * @param key 键
     * @return 值
     */
    public static <T> T get(String key) {
        return (T) CLIENT.get(key);
    }

    /**
     * 设置缓存（List 集合）
     *
     * @param key   键
     * @param value 值
     * @since 2.1.1
     */
    public static <T> void setList(String key, List<T> value) {
        CLIENT.put(key,value);
    }

    /**
     * 设置缓存（List 集合）
     *
     * @param key      键
     * @param value    值
     * @param duration 过期时间
     * @since 2.1.1
     */
    public static <T> void setList(String key, List<T> value, Duration duration) {
        CLIENT.put(key,value,duration.getSeconds());
    }

    /**
     * 查询指定缓存（List 集合）
     *
     * @param key 键
     * @return 值
     * @since 2.1.1
     */
    public static <T> List<T> getList(String key) {
        return (List<T>) CLIENT.get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return true：设置成功；false：设置失败
     */
    public static boolean delete(String key) {
        CLIENT.put(key,null);
        return true;
    }

    /**
     * 删除缓存
     *
     * @param pattern 键模式
     */
    public static void deleteByPattern(String pattern) {
      
    }

    /**
     * 递增 1
     *
     * @param key 键
     * @return 当前值
     * @since 2.0.1
     */
    public static long incr(String key) {
        return 0;
    }

    /**
     * 递减 1
     *
     * @param key 键
     * @return 当前值
     * @since 2.0.1
     */
    public static long decr(String key) {
        return 0;
    }

    /**
     * 设置缓存过期时间
     *
     * @param key      键
     * @param duration 过期时间
     * @return true：设置成功；false：设置失败
     */
    public static boolean expire(String key, Duration duration) {
        return true;
    }

    /**
     * 查询缓存剩余过期时间
     *
     * @param key 键
     * @return 缓存剩余过期时间（单位：毫秒）
     */
//    public static long getTimeToLive(String key) {
//        return CLIENT.getBucket(key).remainTimeToLive();
//    }

    /**
     * 是否存在指定缓存
     *
     * @param key 键
     * @return true：存在；false：不存在
     */
    public static boolean exists(String key) {
        return !ObjectUtils.isEmpty(key);
    }

    /**
     * 查询缓存列表
     *
     * @param pattern 键模式
     * @return 缓存列表
     */
    public static Collection<String> keys(String pattern) {
        return null;
    }

//    /**
//     * 限流
//     *
//     * @param key          键
//     * @param rateType     限流类型（OVERALL：全局限流；PER_CLIENT：单机限流）
//     * @param rate         速率（指定时间间隔产生的令牌数）
//     * @param rateInterval 速率间隔（时间间隔，单位：秒）
//     * @return true：成功；false：失败
//     */
//    public static boolean rateLimit(String key, RateType rateType, int rate, int rateInterval) {
//        RRateLimiter rateLimiter = CLIENT.getRateLimiter(key);
//        rateLimiter.trySetRate(rateType, rate, rateInterval, RateIntervalUnit.SECONDS);
//        return rateLimiter.tryAcquire(1);
//    }

    /**
     * 格式化键，将各子键用 : 拼接起来
     *
     * @param subKeys 子键列表
     * @return 键
     */
    public static String formatKey(String... subKeys) {
        return String.join(StringConst.COLON, subKeys);
    }
}
