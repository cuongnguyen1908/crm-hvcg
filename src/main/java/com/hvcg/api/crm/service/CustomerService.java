package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerUpdateDTO;
import com.hvcg.api.crm.entity.Avatar;

public interface CustomerService {
    void createCustomer(CustomerCreateDTO dto);

    void deleteCustomer(Long id);

    void updateCustomer(CustomerUpdateDTO customerDTO);

    void updateAvatar(Avatar avatar, Long id);

}

