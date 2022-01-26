package com.devo.webproj.handler;

import com.devo.webproj.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 때 처리 로직 작성

        setUserInfo(request);   // 세션에 로그인 사용자 정보 입력
        setTargetUrl(request, response);
    }

    private void setTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setDefaultTargetUrl("/layout/page");    // 로그인 성공 시 기본 리다이렉트 주소
        SavedRequest savedRequest = requestCache.getRequest(request, response); // 요청한 주소
        if(savedRequest != null){   // 로그인 전 요청한 주소가 있다면 리다이렉트
            String targetUrl = savedRequest.getRedirectUrl();

            if(targetUrl.split("/").length > 3) redirectStrategy.sendRedirect(request, response,targetUrl); // 요청 주소 체계가 맞으면 요청한 주소로 리다이렉트
            else redirectStrategy.sendRedirect(request, response,getDefaultTargetUrl());    // 요청 주소 체계가 틀리면 기본 주소로 리다이렉트
        } else redirectStrategy.sendRedirect(request, response,getDefaultTargetUrl());  // 로그인 전 요청한 주소가 없다면 기본 주소로 리다이렉트

    }

    private void setUserInfo(HttpServletRequest request) {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) email = ((UserDetails) principal).getUsername();
        else email = principal.toString();

        accountService.setUserInfo(email, request.getSession().getId());
    }
}
