package com.abes.doctor_appointment_system.Configurations;

import com.abes.doctor_appointment_system.JwtAuthenticationFilter.JwtAuthenticationFilter;
import com.abes.doctor_appointment_system.Service.CustomUserDetailService;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;}

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Swagger access
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Public APIs
                        .requestMatchers(
                                "/patient/api/patientRegistration",
                                "/users/api/login",
                                "/Speciality/api/getAll",
                                "/Doctor/api/getAll"
                        ).permitAll()

                        // Admin APIs
                        .requestMatchers(
                                "/Speciality/api/add",
                                "/Doctor/api/add",
                                "/Slot/api/addSlot"
                        ).hasRole("ADMIN")

                        // Patient APIs
                        .requestMatchers(
                                "/Appointment/api/book/**",
                                "/Appointment/api/cancel/**",
                                "/Appointment/api/myAppointments",
                                "/Slot/api/getDoctorsSlotByDateAndId/**"
                        ).hasRole("PATIENT")

                        // Doctor APIs
                        .requestMatchers(
                                "/Appointment/api/doctorAppointments"
                        ).hasRole("DOCTOR")
                        .anyRequest().authenticated()
                );
//        .httpBasic(Customizer.withDefaults());

        return http.build();
    }
        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration config) throws Exception {

            return config.getAuthenticationManager();
        }

}
