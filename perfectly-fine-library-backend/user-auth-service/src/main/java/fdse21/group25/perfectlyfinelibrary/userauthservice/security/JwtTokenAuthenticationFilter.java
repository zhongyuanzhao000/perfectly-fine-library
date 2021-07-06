package fdse21.group25.perfectlyfinelibrary.userauthservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jwt.SignedJWT;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import fdse21.group25.perfectlyfinelibrary.common.dto.LoginUserDto;
import fdse21.group25.perfectlyfinelibrary.userauthservice.service.JwtService;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtTokenAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            this.logger.warn("Authorization header not found");
            filterChain.doFilter(request, response);
            return;
        }
        SignedJWT token;
        try {
            token = jwtService.convert(authorization);
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        try {
            LoginUserDto user = jwtService.verify(token);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null,
                    AuthorityUtils.createAuthorityList(user.getRole().name())));
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
