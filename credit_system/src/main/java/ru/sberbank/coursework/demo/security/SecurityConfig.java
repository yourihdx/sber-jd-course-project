package ru.sberbank.coursework.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/admin**").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/bank").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/bankss").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/client").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/clients").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/credit").hasAnyRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/credits").hasAnyRole(Role.ADMIN.name())
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails client = User.builder()
                .username("client")
                .password(passwordEncoder().encode("client"))
                .roles("CLIENT")
                .build();
        return new InMemoryUserDetailsManager(admin, client);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
