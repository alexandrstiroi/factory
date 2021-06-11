package org.shtiroy.factory.security;

import org.shtiroy.factory.entity.User;
import org.shtiroy.factory.entity.UserLog;
import org.shtiroy.factory.repository.UserLogRepository;
import org.shtiroy.factory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLogRepository userLogRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        userRepository.updateLastLogin(user.getId(), new Timestamp(System.currentTimeMillis()));

        UserLog userLog = new UserLog();
        userLog.setUserId(user);
        userLog.setLogDate(new Timestamp(System.currentTimeMillis()));
        userLog.setOperationLog("LOGIN");

        userLogRepository.save(userLog);

        httpServletResponse.sendRedirect("/");
    }
}
