package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerAddressUpdateDTO;

import javax.servlet.http.HttpServletRequest;


public interface CustomerAddressService {


    void createCustomerAddress(CustomerAddressCreateDTO dto, HttpServletRequest request);

    void updateCustomerAddress(CustomerAddressUpdateDTO dto);
}
