package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.*;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerUpdateDTO;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private ResponseDTO responseDTO = null;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllCustomer() {
        responseDTO.setContent(this.customerRepository.getAllCustomer(Status.ACTIVE.getStatus()));
        responseDTO.setMessage(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/getById")
    public ResponseEntity<ResponseDTO> getCustomerById(@RequestParam(value = "customerId") Long customerId, Pageable pageable) {
        Optional<CustomerDetailDTO> customerDetailDTO = this.customerRepository
                .findCustomerById(customerId, Status.ACTIVE.getStatus());

        customerDetailDTO.ifPresentOrElse(res -> {
            AvatarDTO avatar = this.customerRepository
                    .findAvatarCustomById(customerId).orElse(null);
            Page<CustomerAddressDTO> customerAddress = this.customerAddressRepository
                    .findAllCustomerAddressByCustomerId(pageable, customerId, Status.ACTIVE.getStatus());
            res.setCustomerAddress(CommonUltils.setResponsePagingDTO(customerAddress));
            res.setAvatar(avatar);
            responseDTO.setContent(res);
            responseDTO.setMessage(null);
        }, () -> {
            responseDTO.setMessage("Not found customer id - " + customerId);
        });
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);



    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody CustomerCreateDTO dto) {
        responseDTO = this.customerService.createCustomer(dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCustomer(@RequestParam(value = "customerId") Long customerId) {
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
    public ResponseEntity<ResponsePagingDTO> searchCustomers(
            Pageable pageable,
            @RequestParam(value = "textSearch", required = false) String textSearch) {
        if (textSearch != null && textSearch.length() > 0) {
            Page<CustomerDTO> result = this.customerRepository
                    .searchAllCustomerByFullName(pageable, Status.ACTIVE.getStatus(), textSearch.trim().toLowerCase());
            return new ResponseEntity<>(CommonUltils.setResponsePagingDTO(result), HttpStatus.OK);
        }

        Page<CustomerDTO> result = this.customerRepository
                .searchAllCustomerByFullName(pageable, Status.ACTIVE.getStatus(), "");

        return new ResponseEntity<>(CommonUltils.setResponsePagingDTO(result), HttpStatus.OK);

    }

}
