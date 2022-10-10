package tech.codegrill.springsecuritydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //authorize every request fro client
                .antMatchers("/", "index", "/css/*", "/js/*") // permit users to access these resources with or without authorizations
                .permitAll()// permit the access to all of these resources
                .anyRequest()// Allow any request to enter the server
                .authenticated() // request must be authenticated
                .and()
                .httpBasic(); // use basic authentication (pop-up auth from the browser )
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        /**
         * Determining how we retrieve user information from our database
         *
         */UserDetails andreasFefe = User.builder()
                .username("andreas")
                .password(passwordEncoder.encode("testPassword"))
                .roles("ADMIN") // ROLE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(
                andreasFefe
        );
    }
}
