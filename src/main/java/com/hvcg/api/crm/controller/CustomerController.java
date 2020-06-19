package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.exception.NotFoundException;

import com.hvcg.api.crm.service.CustomerService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerService customerService;



    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @GetMapping("/customers")
    public Page<Customer> getAllCustomer(Pageable pageable) {
        return this.customerService.findAllPaging(pageable, false);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        Optional<Customer> theCustomer = this.customerService.findCustomerById(new Long(customerId), false);
        if (theCustomer.isPresent()) {
            return theCustomer.get();
        }else {
            throw new NotFoundException("Customer id not found - " + customerId);

        }

    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @PostMapping("/customers")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody CustomerDTO dto) {
        this.customerService.createCustomer(dto);
        ResponseDTO responseDTO = new ResponseDTO("Create success");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable String customerId) {
         this.customerService.deleteCustomer(new Long(customerId));
        ResponseDTO responseDTO = new ResponseDTO("Delete success");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;

    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @PutMapping("/customers")
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody Customer customer) {
        this.customerService.updateCustomer(customer);
        ResponseDTO responseDTO = new ResponseDTO("Update success");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="apiKey") })
    @GetMapping("/customers/find")
    public Page<Customer> searchCustomers(Pageable pageable , @RequestParam("s") String s){
        return this.customerService.searchAllCustomer(pageable, s);
    }







}

