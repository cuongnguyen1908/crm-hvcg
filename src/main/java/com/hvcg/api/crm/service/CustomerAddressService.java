package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerAddressUpdateDTO;

import javax.servlet.http.HttpServletRequest;


public interface CustomerAddressService {


    ResponseDTO createCustomerAddress(CustomerAddressCreateDTO dto, HttpServletRequest request);

    ResponseDTO deleteCustomerAddressById(Long customerAddressId);

    ResponseDTO deleteCustomerAddressByCustomerId(Long customerId);

    ResponseDTO updateCustomerAddress(CustomerAddressUpdateDTO dto);
}
