package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.AvatarRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.AvatarService;
import com.hvcg.api.crm.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AvatarController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private CustomerRepository customerRepository;

//    @PostMapping("/avatar/{customerId}")
//    public ResponseEntity<ResponseDTO> uploadImage(@RequestParam("file") MultipartFile file,
//                                                   @PathVariable Long customerId) {
//
//
//        Avatar avatar = this.avatarService.storeFile(file);
//        this.customerService.findCustomerById(customerId, false).orElseThrow(() -> new NotFoundException("Customer " +
//                "not found id - " + customerId));
//        this.customerService.updateAvatar(avatar, customerId);
//
//
//        ResponseDTO responseDTO = new ResponseDTO("Upload success");
//        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @GetMapping("uploads/{fileName:.+}")
    public byte[] viewImage(@PathVariable String fileName) throws IOException {
        Resource resource = this.avatarService.loadFileAsResource(fileName);
        File serverFile = new File(resource.getURI());
        return Files.readAllBytes(serverFile.toPath());
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @PostMapping("avatars/change/{customerId}")
    public ResponseEntity<ResponseDTO> changeImage(@PathVariable Long customerId, @RequestParam("photo") MultipartFile photo) {
        //find customer by id
        Optional<Customer> optionalCustomer = this.customerService.findCustomerById(customerId, false);
        if (optionalCustomer.isPresent()) { //has exist customer id
            try {
                Optional<Avatar> optionalAvatar = this.customerRepository.findAvatarById(customerId);
                System.out.println("find avatar inside customer: " + optionalAvatar.get());

                if (optionalAvatar.isPresent()) {// founded ava inside customer
                    Avatar avatarFound = optionalAvatar.get();

                    //xoa in source
                    Path filePath = Paths.get("./uploads").toAbsolutePath().resolve(avatarFound.getName()).normalize();
                    File file  = new File(filePath.toString());
                    if (file.delete()) {
                        //update
                        this.avatarService.storeFile(photo, avatarFound);
                    }else {
                        throw new NotFoundException("Delete in store error");
                    }
                }

            } catch (NoSuchElementException e) {
                //k co avatar inside customer
                Avatar avatar = new Avatar();
                //create/store
                this.avatarService.storeFile(photo, avatar);
                //add ava for customer
                this.customerService.updateAvatar(avatar, customerId);
            }
        } else {
            throw new NotFoundException("Customer not found id - " + customerId);
        }

        ResponseDTO responseDTO = new ResponseDTO("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }


}
