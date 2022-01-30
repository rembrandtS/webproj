package com.devo.webproj.config;

import com.devo.webproj.handler.CustomAuthenticationFailureHandler;
import com.devo.webproj.handler.CustomAuthenticationSuccessHandler;
import com.devo.webproj.handler.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final CustomAuthenticationFailureHandler customFailureHandler;
    private final CustomAuthenticationSuccessHandler customSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/account/login", "/dist/**", "/plugins/**", "/accountApi/setPassword")
            .permitAll()
            .anyRequest()
            .authenticated()
        .and()
            .formLogin()
            .loginPage("/account/login")
            .successHandler(customSuccessHandler)
            .failureHandler(customFailureHandler)
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/logout") /* 로그아웃 url*/
            .logoutSuccessHandler(customLogoutSuccessHandler)
            .logoutSuccessUrl("/account/login") /* 로그아웃 성공 시 이동할 url */
            .invalidateHttpSession(true) /*로그아웃 시 세션 제거*/
            .deleteCookies("JSESSIONID") /*쿠키 제거*/
            .clearAuthentication(true) /*권한정보 제거*/
            .permitAll()
        .and()
            .sessionManagement()
            .maximumSessions(1) /* session 허용 갯수 */
            .expiredUrl("/account/login") /* session 만료 시 이동 페이지*/
            .maxSessionsPreventsLogin(false)
            .sessionRegistry(new SessionRegistryImpl());

        http.cors().and().csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, @Lazy PasswordEncoder passwordEncoder)
            throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email as user_id, password, CASE WHEN enabled = 1 THEN true ELSE false END "
                        + "from account "
                        + "where email = ?")
                .authoritiesByUsernameQuery("select a.email as user_id, c.code "
                        + "from account a inner join account_role b on b.account_id=a.id  "
                        + "inner join role c on b.role_id = c.id "
                        + "where a.email = ? ")
                .groupAuthoritiesByUsername(
                        "select b.id, b.name, b.role_type "
                                + "from account a "
                                + "inner join company b on b.id = a.company_id "
                                + "where a.email = ?"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
