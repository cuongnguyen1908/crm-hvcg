package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerUpdateDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    Customer createCustomer(CustomerCreateDTO dto);


    ResponseDTO deleteCustomer(Long id);

    ResponseDTO updateCustomer(CustomerUpdateDTO customerDTO);

    void updateAvatar(Avatar avatar, Long id);

}

