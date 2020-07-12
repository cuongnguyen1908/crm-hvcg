package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerAddressUpdateDTO;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/addresses")
public class CustomerAddressController {



    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ResponseDTO responseDTO;





    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCustomerAddress(@RequestBody CustomerAddressCreateDTO dto, HttpServletRequest request) {

        System.out.println("Workd in controller");

        responseDTO = this.customerAddressService.createCustomerAddress(dto, request);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateCustomerAddress(@RequestBody CustomerAddressUpdateDTO dto) {
        if (this.customerAddressRepository.existsCustomerAddressByIdAndCustomerIdAndDeleteFlag(dto.getCustomerAddressId(), dto.getCustomerId(), Status.ACTIVE.getStatus())) {
            responseDTO = this.customerAddressService.updateCustomerAddress(dto);
        } else {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCustomer(@RequestParam(value = "customerAddressId") Long customerAddressId) {
        responseDTO = this.customerAddressService.deleteCustomerAddressById(customerAddressId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
