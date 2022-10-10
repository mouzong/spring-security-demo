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

import static tech.codegrill.springsecuritydemo.security.ApplicationUserRole.*;

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
         */

        // Creating the ADMIN role User
        UserDetails andreasFefeUser = User.builder()
                .username("andreas")
                .password(passwordEncoder.encode("testPassword"))
                .roles(ADMIN.name()) // ROLE_ADMIN
                .build();

         // Creating the student role user
        UserDetails frykerUser = User.builder()
                .username("fryker")
                .password(passwordEncoder.encode("password123"))
                .roles(STUDENT.name())
                .build();

        return new InMemoryUserDetailsManager(
                andreasFefeUser,
                frykerUser
        );
    }
}
