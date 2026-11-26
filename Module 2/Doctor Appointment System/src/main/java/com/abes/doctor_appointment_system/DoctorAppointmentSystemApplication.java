package com.abes.doctor_appointment_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DoctorAppointmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorAppointmentSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner generatePassword(PasswordEncoder passwordEncoder) {
		return args -> {
			System.out.println("Hello World");
//			System.out.println(passwordEncoder.encode("admin123"));
			System.out.println(
					passwordEncoder.matches(
							"admin123",
							"$2a$10$7hshKmoz3akaKKQbfyRrU.TKY4CYXMsH6fwi8aOfy78.1AWNjFkze"
					)
			);
		};
	}
}


