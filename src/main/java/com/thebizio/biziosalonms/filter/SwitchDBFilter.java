package com.thebizio.biziosalonms.filter;


import com.thebizio.biziosalonms.service.multi_data_source.DBContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SwitchDBFilter implements Filter {
    public final String UN_AUTH_ERR_MSG = "org-code not found";
    public final String ORG_CODE_COOKIE_NAME = "org-code";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String orgCode = extractOrgCode(httpRequest);
        log.info("org-code from filter : " + orgCode);
        if (orgCode == null) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, UN_AUTH_ERR_MSG);
            return; // org-code not present so no need to go further
        }
        DBContextHolder.setCurrentDb(orgCode);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String extractOrgCode(HttpServletRequest req) {

        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals(ORG_CODE_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
