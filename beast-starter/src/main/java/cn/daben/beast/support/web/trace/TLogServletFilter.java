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

package cn.daben.beast.support.web.trace;

import cn.daben.beast.autoconfigure.web.TraceProperties;
import cn.hutool.core.text.CharSequenceUtil;
import com.yomahub.tlog.context.TLogContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * TLog 过滤器
 *
 * <p>
 * 重写 TLog 配置以适配 Spring Boot 3.x
 * </p>
 *
 * @author Bryan.Zhang
 * @author Jasmine
 * @see com.yomahub.tlog.web.filter.TLogServletFilter
 * @since 1.3.0
 */
@RequiredArgsConstructor
public class TLogServletFilter implements Filter {

    private final TraceProperties traceProperties;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest && response instanceof HttpServletResponse httpServletResponse) {
            try {
                TLogWebCommon.loadInstance().preHandle(httpServletRequest);
                // 把 traceId 放入 response 的 header，为了方便有些人有这样的需求，从前端拿整条链路的 traceId
                String headerName = traceProperties.getHeaderName();
                if (CharSequenceUtil.isNotBlank(headerName)) {
                    httpServletResponse.addHeader(headerName, TLogContext.getTraceId());
                }
                chain.doFilter(request, response);
            } finally {
                TLogWebCommon.loadInstance().afterCompletion();
            }
            return;
        }
        chain.doFilter(request, response);
    }
}
