package com.abes.doctor_appointment_system.Controller;

import com.abes.doctor_appointment_system.DTO.LoginDto;
import com.abes.doctor_appointment_system.Repository.UserRepository;
import com.abes.doctor_appointment_system.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
   this.userService=userService;
    }
    @PostMapping("/login")
    public String Login(@RequestBody LoginDto request){
        String message= userService.Login(request.getEmail(),request.getPassword());
        return message;
    }
}
