package com.digitalhouse.digitalexpirience.config;

import com.digitalhouse.digitalexpirience.jwt.AuthEntryPointJwt;
import com.digitalhouse.digitalexpirience.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt jwtAuthenticationEntryPoint;

    @Autowired
    private AuthTokenFilter jwtAuthenticationTokenFilter;

    @Value("${cors.allowed-origins:#{null}}")
    private String[] corsAllowedOrigins;
    @Value("${cors.max-age:3600}")
    private Long corsMaxAge;
    @Value("${cors.allowed-methods:GET,POST,OPTIONS,PUT,DELETE,PATCH}")
    private String[] corsAllowedMethods;
    @Value("${cors.allowed-headers:Authorization,Content-Type,Content-Disposition,WWW-Authenticate}")
    private String[] corsAllowedHeaders;
    @Value("${cors.exposed-headers:Authorization,Content-Type,Content-Disposition,WWW-Authenticate}")
    private String[] corsExposedHeaders;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/users/register/**").permitAll()
                .antMatchers("/users/register/confirm/**").permitAll()
                .antMatchers("/categories/**").permitAll()
                .antMatchers("/experiences/**").permitAll()
                .antMatchers("/attributes/**").permitAll()
                .antMatchers("/countries/**").permitAll()
                .antMatchers("/cities/**").permitAll()
                .antMatchers("/places/**").permitAll()
                .antMatchers("/files/**").permitAll()
                .antMatchers("/places/**").permitAll()
                .antMatchers("/bookings/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs", "configuration/ui", "/configuration/security", "/webjars/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .headers()
                .frameOptions().sameOrigin() // H2 Console Needs this setting
                .cacheControl(); // disable caching

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173",
                "http://digital-booking-web.s3-website.us-east-2.amazonaws.com"
        ));
        configuration.setAllowedMethods(List.of(corsAllowedMethods));
        configuration.setAllowedHeaders(List.of(corsAllowedHeaders));
        configuration.setMaxAge(corsMaxAge);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}