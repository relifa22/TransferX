package lt.javau12.TransferX.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // išjungiame CSRF, nes dirbam su Postman
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/api/auth/**").permitAll() // leidžiam visiems registruotis ir login'intis
                        // .anyRequest().authenticated() // viskam kitam reiks autorizacijos (kai vėliau prireiks)
                        .anyRequest().permitAll() // Leisti VISKĄ visiems be jokių sąlygų
                )
                .formLogin(form -> form.disable()) // šitą pridedam — išjungiam Springo login puslapį
                .build();

    }


}
