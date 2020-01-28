package com.graphaware.neo4j.experiment.security.neo4j;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component()
public class Neo4jContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String username = httpServletRequest.getHeader("username");
        DbContextHolder.setUsername(username);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        DbContextHolder.clearDbUser();
    }
}
