package com.example.securityformlogin;

import com.example.securityformlogin.filters.AuthenticationLoggingFilter;
import com.example.securityformlogin.filters.RequestValidationFilter;
import com.example.securityformlogin.services.auth.AuthenticationProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProviderService authenticationProviderService;

//    @Autowired
//    private StaticKeyAuthenticationFilter filter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProviderService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .defaultSuccessUrl("/main", true);

        http.httpBasic();

        /// regex demo
//        http.authorizeRequests()
//                .mvcMatchers("/email/{email:.*(.+@.+\\.com)}")
//                .permitAll()
//                .anyRequest()
//                .denyAll();

//        http.authorizeRequests()
//                .mvcMatchers("/product/{code:^[0-9]*$}").permitAll()
//                .anyRequest().denyAll();

        /// add custom filter before existing ones
        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().permitAll();
    }
}
