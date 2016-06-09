package com.hospitalsearch.handlers;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andrew Jasinskiy on 05.06.16
 */
public class ErrorAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    private User user;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        user = userService.getByEmail(email);
        String error = "invalidAuthentication";
        if (user != null) {
            if(!user.getEnabled()){
                error = "invalidActivation";
            }
        }
        this.setDefaultFailureUrl("/login?email=" + email +"&error=" + error);
        super.onAuthenticationFailure(request, response, exception);
    }
}
