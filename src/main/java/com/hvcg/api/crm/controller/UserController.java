package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.UserDTO;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/users/getById{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        UserDTO userDTO = this.userRepository.selectUserById(userId, Status.ACTIVE.getStatus())
                .orElseThrow(() -> new NotFoundException("User not found id - " + userId));
        return userDTO;

    }
}
