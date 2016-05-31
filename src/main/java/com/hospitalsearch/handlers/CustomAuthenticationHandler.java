package com.hospitalsearch.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Jasinskiy
 */
@Component
public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger logger = LogManager.getLogger(CustomAuthenticationHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {
        String target = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.error("Error redirect");
            return;
        }
        redirectStrategy.sendRedirect(request, response, target);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String role = authentication.getAuthorities().toString();
        Map<String, String> roleMapper = new HashMap<>();
        roleMapper.put("MANAGER", "/hospitals");
        roleMapper.put("ADMIN", "/admin/users?status=true");
        roleMapper.put("PATIENT", "/");
        roleMapper.put("DOCTOR", "/hospitals");

        if (authentication.getAuthorities().size() > 1) {
            return "/";
        }

        for (Map.Entry<String, String> entry : roleMapper.entrySet()) {
            if (role.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "/403";
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
