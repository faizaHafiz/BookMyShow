package com.sgsc.BMS.services;

import com.sgsc.BMS.Repositories.UserRepository;
import com.sgsc.BMS.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){

        this.userRepository = userRepository;
    }
    public User signUp(String email,String password){
        // check if user is available or not
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userRepository.save(user);

        return savedUser;

    }

}
