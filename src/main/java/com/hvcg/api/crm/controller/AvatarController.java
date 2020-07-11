package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.AvatarService;
import com.hvcg.api.crm.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/avatars")
public class AvatarController {

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AvatarService avatarService;


    @Autowired
    private CustomerRepository customerRepository;

    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @GetMapping("uploads/{fileName:.+}")
    public byte[] viewImage(@PathVariable String fileName) throws IOException {
        Resource resource = this.avatarService.loadFileAsResource(fileName);
        File serverFile = new File(resource.getURI());
        return Files.readAllBytes(serverFile.toPath());
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping("/update/{customerId}")
    public ResponseEntity<ResponseDTO> changeImage(@PathVariable Long customerId,
                                                   @RequestParam("photo") MultipartFile photo) {
        //find customer by id
        Optional<Customer> optionalCustomer = this.customerRepository.findCustomerByIdAndDeleteFlag(customerId,
                Status.ACTIVE.getStatus());
        if (optionalCustomer.isPresent()) { // customer exist
            try {
                Optional<Avatar> optionalAvatar = this.customerRepository.findAvatarById(customerId);

                if (optionalAvatar.isPresent()) {// founded ava inside customer
                    Avatar avatarFound = optionalAvatar.get();

                    //xoa in source
                    Path filePath = Paths.get("./uploads").toAbsolutePath().resolve(avatarFound.getName()).normalize();
                    File file = new File(filePath.toString());
                    if (file.delete()) {
                        //update
                        this.avatarService.storeFile(photo, avatarFound);
                    } else {
                        throw new NotFoundException("Delete in store error");
                    }

                } else {
                    //k co avatar inside customer
                    Avatar avatar = new Avatar();
                    //create/store
                    this.avatarService.storeFile(photo, avatar);
                    //add ava for customer
                    this.customerService.updateAvatar(avatar, customerId);
                }

            } catch (NoSuchElementException e) {
                throw new NotFoundException("Opp!, Something problem, please try again.");


            }
        } else {
            throw new NotFoundException("Customer not found id - " + customerId);
        }

        responseDTO.setContent(true);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
