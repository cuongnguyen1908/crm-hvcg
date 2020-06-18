package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.entity.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface CustomerAddressService {

    Page<CustomerAddressDTO> findAllCustomerAddressByCustomerId(Pageable pageable, Long id, boolean status);

    void deleteAllCustomerAddressByCustomerId(Long customerId, boolean status);

    void createCustomerAddress(CustomerAddressDTO customerAddressDto);

    void deleteCustomerAddressById(Long id);
}
