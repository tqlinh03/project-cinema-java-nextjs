package com.cinema.sso;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.cinema.sso.modal.role.RoleRepository;
import com.cinema.sso.modal.role.entities.Role;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class CinemaBackend {

 	public static void main(String[] args) { 
		SpringApplication.run(CinemaBackend.class, args);
	} 

	@Bean 
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(
					Role.builder()
						.name("USER")
						.build()
				);
				
			}
		};
	}

}
