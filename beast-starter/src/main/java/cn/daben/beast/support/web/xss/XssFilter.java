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

package cn.daben.beast.support.web.xss;

import cn.daben.beast.autoconfigure.web.XssProperties;
import cn.hutool.core.collection.CollectionUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.List;

/**
 * XSS 过滤器
 *
 * @author whhya
 * @since 2.0.0
 */
public class XssFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XssFilter.class);

    private final XssProperties xssProperties;

    public XssFilter(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("[ContiNew Starter] - Auto Configuration 'Web-XssFilter' completed initialization.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 未开启 XSS 过滤，则直接跳过
        if (servletRequest instanceof HttpServletRequest request && xssProperties.isEnabled()) {
            // 放行路由：忽略 XSS 过滤
            List<String> excludePatterns = xssProperties.getExcludePatterns();
            if (CollectionUtil.isNotEmpty(excludePatterns) && isMatchPath(request.getServletPath(), excludePatterns)) {
                filterChain.doFilter(request, servletResponse);
                return;
            }
            // 拦截路由：执行 XSS 过滤
            List<String> includePatterns = xssProperties.getIncludePatterns();
            if (CollectionUtil.isNotEmpty(includePatterns)) {
                if (isMatchPath(request.getServletPath(), includePatterns)) {
                    filterChain.doFilter(new XssServletRequestWrapper(request, xssProperties), servletResponse);
                } else {
                    filterChain.doFilter(request, servletResponse);
                }
                return;
            }
            // 默认：执行 XSS 过滤
            filterChain.doFilter(new XssServletRequestWrapper(request, xssProperties), servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 判断数组中是否存在匹配的路径
     *
     * @param requestUrl   请求地址
     * @param pathPatterns 指定匹配路径
     * @return true：匹配；false：不匹配
     */
    private static boolean isMatchPath(String requestUrl, List<String> pathPatterns) {
        for (String pattern : pathPatterns) {
            PathPattern pathPattern = PathPatternParser.defaultInstance.parse(pattern);
            PathContainer pathContainer = PathContainer.parsePath(requestUrl);
            if (pathPattern.matches(pathContainer)) {
                return true;
            }
        }
        return false;
    }
}
