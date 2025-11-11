package com.fullStack.to_do.list.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("[JWT] دخل الفلتر: method={} uri={}", request.getMethod(), request.getRequestURI());

        try {
            String token = getJWTFromRequest(request);
            log.info("[JWT] هل التوكن موجود؟ {}", (token != null));

            if (StringUtils.hasText(token)) {
                log.debug("[JWT] قيمة التوكن (مخفّاة): {}", mask(token));
                boolean valid = tokenGenerator.validateToken(token);
                log.info("[JWT] صلاحية التوكن: {}", valid);

                if (valid) {
                    String username = tokenGenerator.getUsernameFromJWT(token);
                    log.info("[JWT] Username المستخرج من التوكن: {}", username);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    String authoritiesStr = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
                    log.info("[JWT] UserDetails loaded: username={}, authorities=[{}]",
                            userDetails.getUsername(), authoritiesStr);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    log.info("[JWT] تم تعيين Authentication في SecurityContext: principal={}, authorities=[{}]",
                            userDetails.getUsername(), authoritiesStr);
                } else {
                    log.warn("[JWT] التوكن غير صالح، لن يتم تعيين Authentication.");
                }
            } else {
                log.info("[JWT] لا يوجد توكن Bearer في الهيدر، المرور بدون مصادقة.");
            }
        } catch (Exception ex) {
            log.error("[JWT] استثناء أثناء التحقق أو تحميل المستخدم: {}", ex.getMessage(), ex);
        }

        log.info("[JWT] تمرير الطلب إلى السلسلة التالية (filterChain)...");
        filterChain.doFilter(request, response);
        log.info("[JWT] رجع الطلب من السلسلة (بعد المعالجات اللاحقة).");
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken)) {
            log.debug("[JWT] Header Authorization غير موجود.");
            return null;
        }
        if (!bearerToken.startsWith("Bearer ")) {
            log.debug("[JWT] Header Authorization لا يبدأ بـ 'Bearer '. القيمة: {}", bearerToken);
            return null;
        }
        String token = bearerToken.substring(7).trim();
        log.debug("[JWT] تم استخراج التوكن من الهيدر (مخفّاة): {}", mask(token));
        return token;
    }

    private String mask(String token) {
        if (token == null) return null;
        int keep = Math.min(8, token.length());
        return token.substring(0, keep) + "..." + "(" + token.length() + " chars)";
    }
}