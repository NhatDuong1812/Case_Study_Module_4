package com.example.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()){
            return;
        }
        redirectStrategy.sendRedirect(request,response,targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication){
        String url;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority authority:authorities){
            roles.add(authority.getAuthority());
        }
        if(isAdmin(roles)){
            url="/admin/product";
        } else if(isUser(roles)){
            url="/shop";
        } else {
            url = "/shop";
        }
        return url;
    }

    private boolean isUser(List<String> roles){
        return roles.contains("ROLE_USER");
    }

    private boolean isAdmin(List<String> roles){
        return roles.contains("ROLE_ADMIN");
    }

    @Override
    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}

