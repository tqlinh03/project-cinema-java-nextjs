package com.cinema.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {

  private final UserDetailsService userDetailsService;
  
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

   @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }

  //   @Bean
  //   public CorsFilter corsFilter() {
  //       final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //       final CorsConfiguration config = new CorsConfiguration();
  //       config.setAllowCredentials(true);
  //       config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
  //       config.setAllowedHeaders(Arrays.asList(
  //               HttpHeaders.ORIGIN,
  //               HttpHeaders.CONTENT_TYPE,
  //               HttpHeaders.ACCEPT,
  //               HttpHeaders.AUTHORIZATION
  //       ));
  //       config.setAllowedMethods(Arrays.asList(
  //               "GET",
  //               "POST",
  //               "DELETE",
  //               "PUT",
  //               "PATCH"
  //       ));
  //       source.registerCorsConfiguration("/**", config);
  //       return new CorsFilter(source);

  //   }
}