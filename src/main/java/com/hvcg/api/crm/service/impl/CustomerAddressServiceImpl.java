package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerAddressCreateDTO;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.entity.CustomerAddress;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.CustomerAddressRepository;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Page<CustomerAddressDTO> findAllCustomerAddressByCustomerId(Pageable pageable, Long id, boolean status) {
        return this.customerAddressRepository.findAllCustomerAddressByCustomerId(pageable, id, status);
    }

    @Override
    public void deleteAllCustomerAddressByCustomerId(Long customerId, boolean status) {
        this.customerAddressRepository.deleteAllByCustomerId(customerId, status);
    }

    @Override
    public void createCustomerAddress(CustomerAddressCreateDTO dto) {
        Optional<Customer> optionalCustomer =
                this.customerRepository.findCustomerByIdAndDeleteFlag(dto.getCustomerId(), Status.ACTIVE.getStatus());
        if (optionalCustomer.isPresent()) {
            CustomerAddress customerAddressEntity = new CustomerAddress();
            customerAddressEntity.setCustomer(optionalCustomer.get());
            customerAddressEntity.setAddress(dto.getAddress());
            customerAddressEntity.setContactPerson(dto.getContactPerson());
            customerAddressEntity.setContactPhone(dto.getContactPhone());
            customerAddressEntity.setDescription(dto.getDescription());
            this.customerAddressRepository.save(customerAddressEntity);

        } else {
            throw new NotFoundException("Customer id not found - " + dto.getCustomerId());
        }

    }

    @Override
    public void deleteCustomerAddressById(Long id) {
        CustomerAddress customerAddress = this.customerAddressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer address not found id - " + id));
        customerAddress.setDeleteFlag(true);

        this.customerAddressRepository.save(customerAddress);
    }
}
