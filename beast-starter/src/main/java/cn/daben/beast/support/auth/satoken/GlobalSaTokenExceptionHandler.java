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

package cn.daben.beast.support.auth.satoken;

import cn.daben.beast.support.web.model.RespEntity;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局 SaToken 异常处理器
 *
 * @author Charles7c
 * @since 1.2.0
 */
@Slf4j
@ConditionalOnClass(SaTokenDaoConfig.class)
@RestControllerAdvice
public class GlobalSaTokenExceptionHandler {

    /**
     * 认证异常-登录认证
     */
    @ExceptionHandler(NotLoginException.class)
    public RespEntity<Void> handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，认证失败，无法访问系统资源。", request.getRequestURI(), e);
        String errorMsg = switch (e.getType()) {
            case NotLoginException.KICK_OUT -> "您已被踢下线。";
            case NotLoginException.BE_REPLACED_MESSAGE -> "您已被顶下线。";
            default -> "您的登录状态已过期，请重新登录。";
        };
        return RespEntity.fail(HttpStatus.UNAUTHORIZED.value(), errorMsg);
    }

    /**
     * 认证异常-权限认证
     */
    @ExceptionHandler(NotPermissionException.class)
    public RespEntity<Void> handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，权限码校验失败。", request.getRequestURI(), e);
        return RespEntity.fail(HttpStatus.FORBIDDEN.value(), "没有访问权限，请联系管理员授权");
    }

    /**
     * 认证异常-角色认证
     */
    @ExceptionHandler(NotRoleException.class)
    public RespEntity<Void> handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        log.error("请求地址 [{}]，角色权限校验失败。", request.getRequestURI(), e);
        return RespEntity.fail(HttpStatus.FORBIDDEN.value(), "没有访问权限，请联系管理员授权");
    }
}
