package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface CustomerService {
    Page<Customer> findAllPaging(Pageable pageable, boolean status);

    Customer createCustomer(CustomerDTO dto);

    Optional<Customer> findCustomerById(Long id, boolean status);

    void deleteCustomer(Long id);

    Customer updateCustomer(Customer customer);

    Page<Customer> searchAllCustomer(Pageable pageable , String searchValue);

    void updateAvatar(Avatar avatar, Long id );

}

