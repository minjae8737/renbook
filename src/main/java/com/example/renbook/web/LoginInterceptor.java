package com.example.renbook.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("로그인 인증 requestURI={}", requestURI);

        HttpSession session = request.getSession(false);
        session.setAttribute("requestURI", requestURI);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("비회원 사용자 요청");

            //로그인 페이지로 이동
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

}
