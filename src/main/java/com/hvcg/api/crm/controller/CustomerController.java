package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.entity.Customer;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("/customers")
    public Page<CustomerDTO> getAllCustomer(Pageable pageable) {
        return this.customerRepository.getAllCustomer(pageable, Status.ACTIVE.getStatus());
    }

    @GetMapping("/customers/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable Long customerId) {
        CustomerDTO customerDTO = this.customerRepository.findCustomerById(customerId, Status.ACTIVE.getStatus())
                .orElseThrow(() -> new NotFoundException("Customer not found id - " + customerId));

        return customerDTO;
    }

    @PostMapping("/customers")
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody CustomerCreateDTO dto) {
        this.customerService.createCustomer(dto);

        ResponseDTO responseDTO = new ResponseDTO("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long customerId) {
        this.customerService.deleteCustomer(customerId);
        ResponseDTO responseDTO = new ResponseDTO("Delete success");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<ResponseDTO> updateCustomer(@PathVariable Long customerId,
                                                      @RequestBody CustomerDTO dto) {
        if (this.customerRepository.existsCustomerByIdAndDeleteFlag(customerId, Status.ACTIVE.getStatus())) {
            this.customerService.updateCustomer(customerId, dto);
        } else {
            throw new NotFoundException("Not found customer id - " + customerId);
        }

        ResponseDTO responseDTO = new ResponseDTO("Update success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping("/customers/find")
    public Page<Customer> searchCustomers(Pageable pageable, @RequestParam("s") String s) {
        return this.customerService.searchAllCustomer(pageable, s);
    }


}

