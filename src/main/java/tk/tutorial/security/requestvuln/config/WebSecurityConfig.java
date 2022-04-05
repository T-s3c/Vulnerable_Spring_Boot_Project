package tk.tutorial.security.requestvuln.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.LinkedList;
import java.util.List;

// Save Session through enable http-only
// secure can not be true -> error
// application.yml

@Configuration
@EnableWebSecurity
// A6:2017 - Security Misconfiguration
// No csrf, no cors
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .cors()
                .disable();
    }

    // A6: 2017 - Security Misconfiguration -> (Default accounts and their passwords still enabled and unchanged)
    // weak passwords
    // https://docs.spring.io/spring-security/site/docs/5.0.x/api/org/springframework/security/core/userdetails/User.html#withDefaultPasswordEncoder--
    // Method withDefaultPasswordEncoder is not considered to be safe for production
    // A9: 2017 - Using Components with Known Vulnerabilities
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new LinkedList<>();
        users.add(User.
                withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());

        users.add(User.
                withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build());

        return new InMemoryUserDetailsManager(users);
    }
}
