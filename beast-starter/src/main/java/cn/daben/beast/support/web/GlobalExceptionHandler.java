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

package cn.daben.beast.support.web;

import cn.daben.beast.constant.StringConst;
import cn.daben.beast.exception.BadRequestException;
import cn.daben.beast.exception.BusinessException;
import cn.daben.beast.exception.GlobalException;
import cn.daben.beast.exception.ResultInfoInterface;
import cn.daben.beast.autoconfigure.base.I18nProperties;
import cn.daben.beast.base.Resp;
import cn.daben.beast.toolkit.MessageSourceUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;


/**
 * 全局异常处理器
 *
 * @author Charles7c
 * @since 1.1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String PARAM_FAILED = "请求地址 [{}]，参数验证失败。";
    @Resource
    private I18nProperties i18nProperties;

    /**
     * 拦截自定义验证异常-错误请求
     */
    @ExceptionHandler(BadRequestException.class)
    public Resp<Void> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        log.warn("请求地址 [{}]，自定义验证失败。", request.getRequestURI(), e);
        return Resp.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 拦截校验异常-违反约束异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Resp<Void> constraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.warn(PARAM_FAILED, request.getRequestURI(), e);
        String errorMsg = CollUtil.join(e
            .getConstraintViolations(), StringConst.CHINESE_COMMA, ConstraintViolation::getMessage);
        return Resp.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Resp<Void> handleBindException(BindException e, HttpServletRequest request) {
        log.warn(PARAM_FAILED, request.getRequestURI(), e);
        String errorMsg = CollUtil.join(e
            .getAllErrors(), StringConst.CHINESE_COMMA, DefaultMessageSourceResolvable::getDefaultMessage);
        return Resp.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-方法参数无效异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                            HttpServletRequest request) {
        log.warn(PARAM_FAILED, request.getRequestURI(), e);
        String errorMsg = CollUtil.join(e
            .getAllErrors(), StringConst.CHINESE_COMMA, DefaultMessageSourceResolvable::getDefaultMessage);
        return Resp.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-方法参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Resp<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                                HttpServletRequest request) {
        String errorMsg = CharSequenceUtil.format("参数名：[{}]，期望参数类型：[{}]", e.getName(), e.getParameter()
            .getParameterType());
        log.warn("请求地址 [{}]，参数转换失败，{}。", request.getRequestURI(), errorMsg, e);
        return Resp.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截文件上传异常-超过上传大小限制
     */
    @ExceptionHandler(MultipartException.class)
    public Resp<Void> handleRequestTooBigException(MultipartException e, HttpServletRequest request) {
        String msg = e.getMessage();
        Resp<Void> defaultFail = Resp.fail(HttpStatus.BAD_REQUEST.value(), msg);
        if (CharSequenceUtil.isBlank(msg)) {
            return defaultFail;
        }

        String sizeLimit;
        Throwable cause = e.getCause();
        if (null != cause) {
            msg = msg.concat(cause.getMessage().toLowerCase());
        }
        if (msg.contains("size") && msg.contains("exceed")) {
            sizeLimit = CharSequenceUtil.subBetween(msg, "maximum (", ")");
        } else if (msg.contains("larger than")) {
            sizeLimit = CharSequenceUtil.subAfter(msg, "larger than ", true);
        } else {
            return defaultFail;
        }

        String errorMsg = "请上传小于 %sMB 的文件".formatted(NumberUtil.parseLong(sizeLimit) / 1024 / 1024);
        log.warn("请求地址 [{}]，上传文件失败，文件大小超过限制。", request.getRequestURI(), e);
        return Resp.fail(HttpStatus.BAD_REQUEST.value(), errorMsg);
    }

    /**
     * 拦截校验异常-请求方式不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Resp<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        log.error("请求地址 [{}]，不支持 [{}] 请求。", request.getRequestURI(), e.getMethod());
        return Resp.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Resp<Void> handleServiceException(BusinessException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生业务异常。", request.getRequestURI(), e);
        return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * 拦截全局应用异常
     */
    @ExceptionHandler(GlobalException.class)
    public Resp<Void> handleGlobalException(GlobalException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生业务异常。", request.getRequestURI(), e);
        ResultInfoInterface resultInfo = e.getResultInfo();
        // 未开启，直接返回
        if (!i18nProperties.getEnabled()) {
            return Resp.fail(resultInfo.getCode(), resultInfo.getDefaultMessage());
        }
        // 以用户自定的messageKey优先，否则枚举当messageKey
        String messageKey = StrUtil.blankToDefault(resultInfo.getMessageKey(), resultInfo.toString());
        String message = MessageSourceUtils.getMessage(messageKey, resultInfo.getDefaultMessage());
        return Resp.fail(resultInfo.getCode(), message);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Resp<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生系统异常。", request.getRequestURI(), e);
        return Resp.fail(e.getMessage());
    }

    /**
     * 拦截未知的系统异常
     */
    @ExceptionHandler(Throwable.class)
    public Resp<Void> handleException(Throwable e, HttpServletRequest request) {
        log.error("请求地址 [{}]，发生未知异常。", request.getRequestURI(), e);
        return Resp.fail(e.getMessage());
    }


}
