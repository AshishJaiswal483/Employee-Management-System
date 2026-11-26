package com.abes.doctor_appointment_system.Service;

import com.abes.doctor_appointment_system.Entity.User;
import com.abes.doctor_appointment_system.Repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final UserRepository userRepository;
    private  final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,JwtService jwtService){
        this.userRepository=userRepository;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    public String Login(String userName,String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName,password)
        );
//        return "Login successfull";
        String token=jwtService.generateToken(userName);
        return token;
    }
}
