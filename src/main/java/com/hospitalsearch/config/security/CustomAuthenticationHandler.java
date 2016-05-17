package com.hospitalsearch.config.security;

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
 * Created by andrew on 11.05.16.
 */
@Component
public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {
        String target = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, target);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String role = authentication.getAuthorities().toString();
        Map<String, String> roleMapper = new HashMap<String, String>();
        roleMapper.put("ROLE_MANAGER", "/hospitals");
        roleMapper.put("ROLE_ADMIN", "/admin/dashboard");
        roleMapper.put("ROLE_PATIENT", "/");
        roleMapper.put("ROLE_DOCTOR", "/hospitals");

        if(authentication.getAuthorities().size()>1){
            return "/";
        }

        System.out.println(roleMapper.entrySet());
        for (Map.Entry<String, String> entry : roleMapper.entrySet()) {
            if (role.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "/Access_Denied";
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
