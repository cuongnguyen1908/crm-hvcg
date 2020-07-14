package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerUpdateDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import com.hvcg.api.crm.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private ResponseDTO responseDTO;


    @Override
    public void createCustomer(CustomerCreateDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customerEntity = modelMapper.map(dto, Customer.class);
        customerEntity.setFullName(dto.getLastName() + " " + dto.getFirstName());
        this.customerRepository.save(customerEntity);
    }


    @Override
    public void deleteCustomer(Long customerId) {
            this.customerAddressRepository
                    .deleteAllCustomerAddressByCustomerId(customerId, Status.IN_ACTIVE.getStatus());

            this.customerRepository.deleteCustomerById(customerId, Status.IN_ACTIVE.getStatus());

    }

    @Override
    public void updateCustomer(CustomerUpdateDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customerEntity = modelMapper.map(dto, Customer.class);
        customerEntity.setFullName(dto.getLastName() + " " + dto.getFirstName());
        customerEntity.setId(dto.getCustomerId());
        this.customerRepository.save(customerEntity);
    }


    @Override
    public void updateAvatar(Avatar avatar, Long id) {
        this.customerRepository.updateAvatar(avatar, id);
    }
}

