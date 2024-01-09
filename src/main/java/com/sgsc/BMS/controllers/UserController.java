package com.sgsc.BMS.controllers;

import com.sgsc.BMS.Dtos.ResponseStatus;
import com.sgsc.BMS.Dtos.SignUpRequestDto;
import com.sgsc.BMS.Dtos.SignUpResponseDto;
import com.sgsc.BMS.Repositories.UserRepository;
import com.sgsc.BMS.models.User;
import com.sgsc.BMS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        SignUpResponseDto response = new SignUpResponseDto();
        User user;

        try{
            user = userService.signUp(signUpRequestDto.getEmail(),signUpRequestDto.getPassword());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setUserId(user.getId());

        }catch(Exception ex){
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
