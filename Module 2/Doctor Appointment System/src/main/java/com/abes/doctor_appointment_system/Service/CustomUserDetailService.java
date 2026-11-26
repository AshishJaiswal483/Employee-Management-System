package com.abes.doctor_appointment_system.Service;

import com.abes.doctor_appointment_system.Entity.User;
import com.abes.doctor_appointment_system.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User>user =userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user with this email");
        }
        User u=user.get();

        UserDetails userDatail =
                org.springframework.security.core.userdetails.User
                        .withUsername(u.getEmail())
                        .password(u.getPassword())
                        .roles(u.getRole())
                        .build();

        return userDatail;

    }
}
