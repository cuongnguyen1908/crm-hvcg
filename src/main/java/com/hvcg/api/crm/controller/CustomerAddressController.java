package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerAddressUpdateDTO;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/addresses")
public class CustomerAddressController {



    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private ResponseDTO responseDTO;





    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createCustomerAddress(@RequestBody CustomerAddressCreateDTO dto, HttpServletRequest request) {

        this.customerAddressService.createCustomerAddress(dto, request);
        responseDTO.setContent(true);
        responseDTO.setMessage("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateCustomerAddress(@RequestBody CustomerAddressUpdateDTO dto) {
        if (this.customerAddressRepository.existsCustomerAddressByIdAndCustomerIdAndDeleteFlag(dto.getCustomerAddressId(), dto.getCustomerId(), Status.ACTIVE.getStatus())) {
            this.customerAddressService.updateCustomerAddress(dto);
            responseDTO.setContent(dto);
            responseDTO.setMessage("Update success");
        } else {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCustomerAddress(@RequestParam(value = "customerAddressId") Long customerAddressId) {
        if (this.customerAddressRepository
                .existsCustomerAddressByIdAndDeleteFlag(customerAddressId, Status.ACTIVE.getStatus())) {
            this.customerAddressRepository.deleteCustomerAddressById(customerAddressId, Status.IN_ACTIVE.getStatus());
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");
        }else{
            responseDTO.setContent(false);
            responseDTO.setMessage("Delete fail customerAddressId not exist");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }


}
