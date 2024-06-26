package cn.daben.beast.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Justubborn
 * @since 2023/11/6
 */
public interface ErrorManger {

    Map<Integer, String> ERROR_CACHE = new ConcurrentHashMap<>();

    static void register(ErrorCode errorCode) {
        if (ERROR_CACHE.containsKey(errorCode.getValue())) {
            throw new RuntimeException("重复注册同名称的错误码：" + errorCode.getValue());
        }
        ERROR_CACHE.putIfAbsent(errorCode.getValue(), errorCode.getDescription());
    }
}
