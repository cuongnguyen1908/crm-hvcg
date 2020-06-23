package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import com.hvcg.api.crm.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAddressService customerAddressService;



    @Override
    public Page<Customer> findAllPaging(Pageable pageable, boolean status) {
        return  this.customerRepository.findAll(pageable, status);

    }



    @Override
    public Customer createCustomer(CustomerDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Customer customerEntity =modelMapper.map(dto, Customer.class);
        customerEntity.setFullName(dto.getLastName() + " " + dto.getFirstName());
        return this.customerRepository.save(customerEntity);
    }

    @Override
    public Optional<Customer> findCustomerById(Long id, boolean status) {
        return this.customerRepository.findCustomerById(id, status);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        //delete parent
        //find customer
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found -" + customerId));
        //set flag delete = true (1)
        customer.setDeleteFlag(true);
        this.customerRepository.save(customer);
        //delete child
        this.customerAddressService.deleteAllCustomerAddressByCustomerId(customerId, true);

    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public Page<Customer> searchAllCustomer(Pageable pageable , String searchValue) {
        if (searchValue != null && searchValue.trim().length() > 0) {
            return this.customerRepository.searchAllCustomer(pageable, searchValue.toLowerCase(), false);
        }
            return this.customerRepository.findAll(pageable, false);
    }

    @Override
    public void updateAvatar(Avatar avatar, Long id) {
        this.customerRepository.updateAvatar(avatar, id);
    }
}

