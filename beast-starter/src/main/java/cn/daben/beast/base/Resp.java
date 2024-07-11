/*********************************************************************************
 *                                                                               *
 * The MIT License (MIT)                                                         *
 *                                                                               *
 * Copyright (c) 2015-2022 aoju.org and other contributors.                      *
 *                                                                               *
 * Permission is hereby granted, free of charge, to any person obtaining a copy  *
 * of this software and associated documentation files (the "Software"), to deal *
 * in the Software without restriction, including without limitation the rights  *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell     *
 * copies of the Software, and to permit persons to whom the Software is         *
 * furnished to do so, subject to the following conditions:                      *
 *                                                                               *
 * The above copyright notice and this permission notice shall be included in    *
 * all copies or substantial portions of the Software.                           *
 *                                                                               *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR    *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,      *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE   *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER        *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN     *
 * THE SOFTWARE.                                                                 *
 *                                                                               *
 ********************************************************************************/
package cn.daben.beast.base;

import cn.hutool.core.date.DateUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 返回值公用类
 *
 * @author Kimi Liu
 * @since Java 17+
 */

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Resp<T> {
    
    private static final int SUCCESS_CODE = HttpStatus.OK.value();
    private static final int FAIL_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 业务状态码
     */
    private int code;

    /**
     * 业务状态信息
     */
    private String msg;
    /**
     * 时间戳
     */
    private long timestamp = DateUtil.current();
    
    /**
     * 响应数据
     */
    private T data;

    private Resp(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 操作成功
     *
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> Resp<T> ok() {
        return new Resp<>(true, SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 操作成功
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> Resp<T> ok(T data) {
        return new Resp<>(true, SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 操作成功
     *
     * @param msg 业务状态信息
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> Resp<T> ok(String msg) {
        return new Resp<>(true, SUCCESS_CODE, msg, null);
    }

    /**
     * 操作成功
     *
     * @param msg  业务状态信息
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> Resp<T> ok(String msg, T data) {
        return new Resp<>(true, SUCCESS_CODE, msg, data);
    }

    /**
     * 操作失败
     *
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> Resp<T> fail() {
        return new Resp<>(false, FAIL_CODE, "操作失败", null);
    }

    /**
     * 操作失败
     *
     * @param msg 业务状态信息
     * @param <T> 响应数据类型
     * @return R /
     */
    public static <T> Resp<T> fail(String msg) {
        return new Resp<>(false, FAIL_CODE, msg, null);
    }

    /**
     * 操作失败
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> Resp<T> fail(T data) {
        return new Resp<>(false, FAIL_CODE, "操作失败", data);
    }

    /**
     * 操作失败
     *
     * @param msg  业务状态信息
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> Resp<T> fail(String msg, T data) {
        return new Resp<>(false, FAIL_CODE, msg, data);
    }

    /**
     * 操作失败
     *
     * @param code 业务状态码
     * @param msg  业务状态信息
     * @param <T>  响应数据类型
     * @return R /
     */
    public static <T> Resp<T> fail(int code, String msg) {
        return new Resp<>(false, code, msg, null);
    }

}
