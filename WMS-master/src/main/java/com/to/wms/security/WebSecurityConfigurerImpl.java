package com.to.wms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfigurerImpl(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(getRestAuthenticationEntryPoint())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(getCustomAccessDeniedHandler())
                .and()
                .authorizeRequests()
                // ADDRESS
                .mvcMatchers(
                        HttpMethod.GET,
                        "/api/v1/address"
                ).hasAnyAuthority("OP_ADDRESS_MANAGEMENT", "WAREHOUSEMAN_ADDRESS")
                .mvcMatchers("/api/v1/address/**").hasAuthority("OP_ADDRESS_MANAGEMENT")
                // DEPARTMENT
                .mvcMatchers(
                        HttpMethod.GET,
                        "/api/v1/departments/**"
                ).hasAnyAuthority("OP_DEPARTMENTS_MANAGEMENT", "WAREHOUSEMAN_DEPARTMENTS")
                .mvcMatchers("/api/v1/departments/**").hasAuthority("OP_DEPARTMENTS_MANAGEMENT")
                // LOCATIONS
                .mvcMatchers(
                        HttpMethod.GET,
                        "/api/v1/locations/**"
                ).hasAnyAuthority("OP_LOCATIONS_MANAGEMENT", "WAREHOUSEMAN_LOCATIONS")
                .mvcMatchers("/api/v1/locations/**").hasAuthority("OP_LOCATIONS_MANAGEMENT")
                // CATEGORY
                .mvcMatchers(
                        HttpMethod.GET,
                        "/api/v1/categories/**"
                ).hasAnyAuthority("OP_CATEGORY_MANAGEMENT", "WAREHOUSE_CATEGORY")
                .mvcMatchers("/api/v1/categories/**").hasAuthority("OP_CATEGORY_MANAGEMENT")
                // PRODUCT
                .mvcMatchers(
                        HttpMethod.GET,
                        "/api/v1/products/**"
                ).hasAnyAuthority("OP_PRODUCT_MANAGEMENT", "WAREHOUSEMAN_PRODUCT")
                .mvcMatchers(
                        HttpMethod.PATCH,
                        "/api/v1/products/*/quantity"
                ).hasAnyAuthority("OP_PRODUCT_MANAGEMENT", "WAREHOUSEMAN_PRODUCT")
                .mvcMatchers("/api/v1/products/**").hasAuthority("OP_PRODUCT_MANAGEMENT")
                // ROLES
                .mvcMatchers("/api/v1/roles/**").hasAuthority("OP_ROLE_MANAGEMENT")
                .mvcMatchers("/api/v1/authorities/**").hasAuthority("OP_ROLE_MANAGEMENT")
                // USERS
                .mvcMatchers(HttpMethod.POST, "/api/v1/users/add").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/v1/users").hasAnyAuthority("OP_USER_MANAGEMENT", "OP_MANAGER_USER")
                .mvcMatchers("/api/v1/users/**").hasAuthority("OP_USER_MANAGEMENT")
                // OTHERS
                .mvcMatchers("/**").permitAll()
                //.and().formLogin()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                //.sessionManagement();
    }

    @Bean
    public RestAuthenticationEntryPoint getRestAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler getCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
