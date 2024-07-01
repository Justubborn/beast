package cn.daben.beast.autoconfigure.cache;

import cn.daben.beast.toolkit.StringKit;
import org.redisson.api.NameMapper;

/**
 * redis缓存key前缀处理
 *
 * @author ye
 * @date 2022/7/14 17:44
 * @since 4.3.0
 */
public class KeyPrefixHandler implements NameMapper {

    private final String keyPrefix;

    public KeyPrefixHandler(String keyPrefix) {
        //前缀为空 则返回空前缀
        this.keyPrefix = StringKit.isBlank(keyPrefix) ? "" : keyPrefix + ":";
    }

    /**
     * 增加前缀
     */
    @Override
    public String map(String name) {
        if (StringKit.isBlank(name)) {
            return null;
        }
        if (StringKit.isNotBlank(keyPrefix) && !name.startsWith(keyPrefix)) {
            return keyPrefix + name;
        }
        return name;
    }

    /**
     * 去除前缀
     */
    @Override
    public String unmap(String name) {
        if (StringKit.isBlank(name)) {
            return null;
        }
        if (StringKit.isNotBlank(keyPrefix) && name.startsWith(keyPrefix)) {
            return name.substring(keyPrefix.length());
        }
        return name;
    }

}
