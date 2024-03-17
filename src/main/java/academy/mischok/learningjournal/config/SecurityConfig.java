package academy.mischok.learningjournal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/login/**",
            "/login-error",
            "/error",
            "/h2-console/**",
            "/img/**",
            "/js/login.js"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Disable CSRF for h2 console
                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?failure=true")
                        .successForwardUrl("/dashboard"))
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                                AUTH_WHITELIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}