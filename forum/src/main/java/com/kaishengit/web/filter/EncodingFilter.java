package com.kaishengit.web.filter;

import com.kaishengit.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/16.
 */
public class EncodingFilter extends AbstractFilter {
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String result = filterConfig.getInitParameter("encoding");
        if(StringUtils.isNotEmpty(result)){
            encoding = result;
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //过滤内容
        servletRequest.setCharacterEncoding(encoding);
        servletRequest.setCharacterEncoding(encoding);

        //设置放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
