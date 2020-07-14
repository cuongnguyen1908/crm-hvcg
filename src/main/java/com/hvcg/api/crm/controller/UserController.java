package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.UserDTO;
import com.hvcg.api.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseDTO responseDTO;



    @GetMapping("/getById")
    public ResponseEntity<ResponseDTO> getUserById(@RequestParam(value = "userId") Long userId) {
        Optional<UserDTO> optionalUserDTO = this.userRepository.getUserById(userId, Status.ACTIVE.getStatus());
        optionalUserDTO.ifPresentOrElse(userDTO -> {
            responseDTO.setContent(userDTO);
            responseDTO.setMessage(null);
        }, () -> {
            responseDTO.setContent(false);
            responseDTO.setMessage("User not found with id - " + userId);
        });
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
