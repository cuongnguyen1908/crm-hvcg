package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.*;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerUpdateDTO;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ResponseDTO responseDTO = null;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllCustomer() {
        responseDTO.setContent(this.customerRepository.getAllCustomer(Status.ACTIVE.getStatus()));
        responseDTO.setMessage(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getById/{customerId}")
    public ResponseEntity<ResponseDTO> getCustomerById(@PathVariable Long customerId) {
        Optional<CustomerAvatarDTO> customerAvatarDTO = this.customerRepository
                .findCustomerById(customerId, Status.ACTIVE.getStatus());

        if(customerAvatarDTO.isPresent()){
            AvatarDTO avatar = this.customerRepository.findAvatarCustomById(customerId).orElse(null);
            customerAvatarDTO.get().setAvatar(avatar);
            responseDTO.setContent(customerAvatarDTO);
            responseDTO.setMessage(null);
        }else{
            responseDTO.setContent(null);
            responseDTO.setMessage("Not found customer id - " + customerId);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody CustomerCreateDTO dto) {
        this.customerService.createCustomer(dto);
        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/delete/{customerId}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long customerId) {
        responseDTO = this.customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody CustomerUpdateDTO dto) {
        if (this.customerRepository.existsCustomerByIdAndDeleteFlag(dto.getCustomerId(), Status.ACTIVE.getStatus())) {
            responseDTO = this.customerService.updateCustomer(dto);
        } else {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/search")
    public Page<CustomerDTO> searchCustomers(
            Pageable pageable,
            @RequestParam(value = "textSearch") Optional<String> textSearch) {
        if (textSearch.isPresent()) {
            return this.customerRepository.searchAllCustomerByFullName(pageable, Status.ACTIVE.getStatus(), textSearch.get().trim().toLowerCase());
        }else{
            return this.customerRepository.searchAllCustomerByFullName(pageable, Status.ACTIVE.getStatus(), "");

        }
    }
}
