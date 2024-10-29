package com.coral.users_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coral.users_service.model.User;
import com.coral.users_service.repository.UserRepository;

@SpringBootApplication
public class UsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			System.out.println("Eliminando usuarios existentes...");
			userRepository.deleteAll();

			System.out.println("iniciando la creacion del usuario admin...");
			User admin = new User(null, "adminqueso", passwordEncoder.encode("admin123"), "admin@admin.admin", "ADMIN");
			userRepository.save(admin);
			System.out.println("Usuario admin creado");
		};
	}

}
