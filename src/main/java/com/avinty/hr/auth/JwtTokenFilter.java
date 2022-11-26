package com.avinty.hr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenManager tokenManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        
        final  String authHeader= httpServletRequest.getHeader("Authorization");
         String token=null;
         String username=null;

        if(authHeader!=null && authHeader.contains("Bearer")){
                token=authHeader.substring(7);
            try {
                 username = tokenManager.getUsernameToken(token);
                 
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        if(username!=null && token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            if(tokenManager.tokenValidate(token)){
                UserDetails details=  userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken unamePassAuthToken=new UsernamePasswordAuthenticationToken(username,details.getPassword(),details.getAuthorities());
                unamePassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(unamePassAuthToken);
            }

        }
    
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
