package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerAddressService {

    Page<CustomerAddressDTO> findAllCustomerAddressByCustomerId(Pageable pageable, Long id, boolean status);

    void deleteAllCustomerAddressByCustomerId(Long customerId, boolean status);

    void createCustomerAddress(CustomerAddressCreateDTO dto);

    void deleteCustomerAddressById(Long id);
}
