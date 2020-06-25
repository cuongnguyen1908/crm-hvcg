package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.entity.CustomerAddress;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/api")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/addresses/{customerId}")
    public Page<CustomerAddressDTO> getAllCustomerAddressByCustomerId(@PathVariable String customerId,
                                                                      Pageable pageable) {
        return this.customerAddressService.findAllCustomerAddressByCustomerId(pageable, new Long(customerId),
                Status.ACTIVE.getStatus());
    }

    @PostMapping("/addresses")
    public ResponseEntity<ResponseDTO> createCustomerAddress(@RequestBody CustomerAddressCreateDTO dto) {
        this.customerAddressService.createCustomerAddress(dto);
        ResponseDTO responseDTO = new ResponseDTO("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PutMapping("/addresses/{customerAddressId}")
    public ResponseEntity<ResponseDTO> updateCustomerAddress(@PathVariable Long customerAddressId,
                                                             @RequestBody CustomerAddressCreateDTO dto) {
        CustomerAddress customerAddress =
                this.customerAddressRepository.findCustomerAddressByIdAndDeleteFlag(customerAddressId,
                        Status.ACTIVE.getStatus())
                        .orElseThrow(() -> new NotFoundException("Customer address not found id " + customerAddressId));
        Customer customer = this.customerRepository.findCustomerByIdAndDeleteFlag(dto.getCustomerId(),
                Status.ACTIVE.getStatus())
                .orElseThrow(() -> new NotFoundException("Customer not found id " + dto.getCustomerId()));

        customerAddress.setAddress(dto.getAddress());
        customerAddress.setDescription(dto.getDescription());
        customerAddress.setContactPhone(dto.getContactPhone());
        customerAddress.setContactPerson(dto.getContactPerson());
        this.customerAddressRepository.save(customerAddress);

        ResponseDTO responseDTO = new ResponseDTO("Update success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @DeleteMapping("/addresses/{customerAddressId}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long customerAddressId) {
        this.customerAddressService.deleteCustomerAddressById(customerAddressId);
        ResponseDTO responseDTO = new ResponseDTO("Delete success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
