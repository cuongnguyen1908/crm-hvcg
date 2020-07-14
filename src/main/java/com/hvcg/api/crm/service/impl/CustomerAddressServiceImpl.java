package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerAddressUpdateDTO;
import com.hvcg.api.crm.entity.CustomerAddress;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {


    @Autowired
    private CustomerAddressRepository customerAddressRepository;


    @Override
    public void createCustomerAddress(CustomerAddressCreateDTO dto, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);
        this.customerAddressRepository.createCustomerAddress(dto.getAddress(), dto.getContactPerson(), dto.getContactPhone(), dto.getDescription(), dto.getCustomerId(), Status.ACTIVE.getStatus(), username);
    }

    @Override
    public void updateCustomerAddress(CustomerAddressUpdateDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        CustomerAddress customerAddressEntity = modelMapper.map(dto, CustomerAddress.class);
        customerAddressEntity.setId(dto.getCustomerAddressId());
        this.customerAddressRepository.save(customerAddressEntity);
    }
}
