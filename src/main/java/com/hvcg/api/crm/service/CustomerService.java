package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
//    Page<Customer> findAllPaging(Pageable pageable, boolean status);

    Customer createCustomer(CustomerCreateDTO dto);

//    Optional<Customer> findCustomerById(Long id, boolean status);

    void deleteCustomer(Long id);

    void updateCustomer(Long customerId, CustomerDTO customerDTO);

    Page<Customer> searchAllCustomer(Pageable pageable, String searchValue);

    void updateAvatar(Avatar avatar, Long id);

}

