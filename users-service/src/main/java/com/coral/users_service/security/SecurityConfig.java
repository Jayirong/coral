package com.coral.users_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//esto es para marcar la clase como una de configuracion
@Configuration
public class SecurityConfig {
    //esto es para definir un metodo que devuelva, en este caso, un passwordEncoder para que spring pueda inyectarlo en el userservice
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
