package ca.sheridancollege.Assignement3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JdbcUserDetailsManager userDetailsManager;

    private LoggingAccessDeniedHandler accessDeniedHandler;
    @Autowired
    public void setAccessDeniedHandler(LoggingAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler=accessDeniedHandler;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Autowired
    @Lazy
    DataSource dataSource;

    @Autowired
    @Lazy
    BCryptPasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/addBook").hasRole("ADMIN") // Only ADMIN role can access /addBook
                .anyRequest().permitAll() // Allow any request for other URLs
                .and()
            .formLogin()
                .loginPage("/login") // Specify your login page URL
                .permitAll() // Allow anyone to access the login page
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler); // Configure accessDeniedHandler
        
        return http.build();
    }


    @Bean(name = "userDetailsManager")
    public JdbcUserDetailsManager getUserDetailsManager() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(admin);
        return users;
    }
}
