package br.com.jgb.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.jgb.api.security.JWTAuthenticationFilter;
import br.com.jgb.api.security.JWTAuthorizationFilter;
import br.com.jgb.api.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JWTUtil jwtUtil;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.cors().and().csrf().disable();

                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                
                http.authorizeHttpRequests()
                .requestMatchers("/usuario/**").permitAll() // nao precisa de token
                .requestMatchers("/fornecedor/**").permitAll()
                .requestMatchers("/produto/**").permitAll()
                .requestMatchers("/servico/**").permitAll()
                .requestMatchers("/pessoa/**").permitAll()
                .requestMatchers("/cliente/**").permitAll()
                .requestMatchers("/funcao/**").permitAll()
                .requestMatchers("/perfil/**").permitAll()
                .requestMatchers("/cidade/**").permitAll()
                .requestMatchers("/evento/**").permitAll()
                .requestMatchers("/agenda/**").permitAll()
                .requestMatchers("/patrimonio/**").permitAll()
                .requestMatchers("/estado/**", "/estados/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .requestMatchers("/pessoa/associar/**").permitAll()
                .requestMatchers("/swagger-ui/**", 
                		  "/swagger-resources/*", 
                		  "/v3/api-docs/**", 
                		  "/v2/api-docs",
                	      "/configuration/ui",
                	      "/swagger-resources/**",
                	      "/configuration/security",
                	      "/swagger-ui.html",
                	      "/webjars/**").permitAll();
                
                authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder());
                this.authenticationManager = authenticationManagerBuilder.build();
                http.authorizeHttpRequests().anyRequest().authenticated().and()
                                .authenticationManager(authenticationManager);
                http.addFilter(new JWTAuthenticationFilter(this.authenticationManager, this.jwtUtil));
                http.addFilter(new JWTAuthorizationFilter(this.authenticationManager, this.jwtUtil, this.userDetailsService));
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                return http.build();
        }
        
        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
                configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
                final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
