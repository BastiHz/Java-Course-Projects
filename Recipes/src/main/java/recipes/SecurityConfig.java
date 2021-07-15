package recipes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/register/**", "/actuator/shutdown/**", "/h2/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic();

        // Gain access to the h2-console in the browser, for testing.
        // May also be necessary for testing with Postman.
        // Do not use in a real product!
        http
            .csrf().disable()
            .headers().frameOptions().disable();

        // Ensure that only a single instance of a user is authenticated at a time. And if the
        // same user tries to access the url then it's previous session is terminated and then
        // the user has to provide login credentials again for which new session is created.
        // https://stackoverflow.com/a/64154354
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
