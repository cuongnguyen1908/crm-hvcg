package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.entity.CustomerAddress;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;


@RestController
@RequestMapping("/api")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CustomerAddressRepository  customerAddressRepository;

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @GetMapping("/addresses/{customerId}")
    public Page<CustomerAddressDTO> getAllCustomerAddressByCustomerId(@PathVariable String customerId, Pageable pageable) {
        return this.customerAddressService.findAllCustomerAddressByCustomerId(pageable, new Long(customerId), Status.ACTIVE.getStatus());
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @PostMapping("/addresses")
    public ResponseEntity<ResponseDTO> createCustomerAddress(@RequestBody CustomerAddressDTO dto) {
        this.customerAddressService.createCustomerAddress(dto);
        ResponseDTO responseDTO = new ResponseDTO("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @PutMapping("/addresses")
    public ResponseEntity<CustomerAddress> updateCustomerAddress(@RequestBody CustomerAddress dto) {
        this.customerAddressRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Customer address not found id " + dto.getId()));
        return new ResponseEntity<>(this.customerAddressRepository.save(dto), HttpStatus.OK);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @DeleteMapping("/addresses/{customerAddressId}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long customerAddressId) {
        this.customerAddressService.deleteCustomerAddressById(customerAddressId);
        ResponseDTO responseDTO = new ResponseDTO("Delete success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }



}
