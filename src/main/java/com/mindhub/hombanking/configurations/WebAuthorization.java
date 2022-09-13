package com.mindhub.hombanking.configurations;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.WebAttributes;

import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration

class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override //sobre escribir

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()

                .antMatchers(HttpMethod.POST, "/api/loans").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.POST,"/api/transactions").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.POST, "/api/postnet").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.PATCH,"/clients/current/cards/{id}").hasAnyAuthority("ADMIN","CLIENT")

                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts/{id}").hasAnyAuthority("ADMIN","CLIENT")

                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers(HttpMethod.POST,"/api/loans/create").hasAuthority("ADMIN")

                .antMatchers("/index.html","./login.html","/web/js/**","/web/img/**","/principal/**").permitAll()

                .antMatchers("/api/clients/current/**","/api/clients/**","/web/**","/web/accounts.html","/web/account.html","/api/loans").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers("/admin/**","/rest/**","/h2-console","/api/**").hasAuthority("ADMIN");

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }




}
