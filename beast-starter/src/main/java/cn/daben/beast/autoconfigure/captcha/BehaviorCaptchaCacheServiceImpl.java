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

package cn.daben.beast.autoconfigure.captcha;

import cn.daben.beast.toolkit.RedisKit;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.anji.captcha.service.CaptchaCacheService;

import java.time.Duration;

/**
 * 行为验证码 Redis 缓存实现
 *
 * @author Bull-BCLS
 * @since 1.1.0
 */
public class BehaviorCaptchaCacheServiceImpl implements CaptchaCacheService {
    @Override
    public void set(String key, String value, long expiresInSeconds) {
        if (NumberUtil.isNumber(value)) {
            RedisKit.set(key, Convert.toInt(value), Duration.ofSeconds(expiresInSeconds));
        } else {
            RedisKit.set(key, value, Duration.ofSeconds(expiresInSeconds));
        }
    }

    @Override
    public boolean exists(String key) {
        return RedisKit.exists(key);
    }

    @Override
    public void delete(String key) {
        RedisKit.delete(key);
    }

    @Override
    public String get(String key) {
        return Convert.toStr(RedisKit.get(key));
    }

    @Override
    public String type() {
        return StorageType.REDIS.name().toLowerCase();
    }

    @Override
    public Long increment(String key, long val) {
        return RedisKit.incr(key);
    }
}
