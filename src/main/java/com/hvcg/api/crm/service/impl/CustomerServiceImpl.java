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

import java.util.Date;
import java.util.Optional;


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
    public ResponseDTO createCustomer(CustomerCreateDTO dto) {
        //valid gender
        if (!(dto.getGender() == 0 || dto.getGender() == 1 || dto.getGender() == 2)) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail by Gender invalid");
            return responseDTO;
        }

        //valid birthday
        if (dto.getDayOfBirth().after(new Date())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Birthday is bigger than current day");
            return responseDTO;
        }

        //valid phone is number
        if (!dto.getPhone().matches("[0-9]+")) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Phone is invalid format");
            return responseDTO;
        }

        ModelMapper modelMapper = new ModelMapper();
        Customer customerEntity = modelMapper.map(dto, Customer.class);
        customerEntity.setFullName(dto.getLastName() + " " + dto.getFirstName());
        this.customerRepository.save(customerEntity);
        responseDTO.setContent(true);
        responseDTO.setMessage("Create success");


        return responseDTO;
    }


    @Override
    public ResponseDTO deleteCustomer(Long customerId) {
        if (this.customerRepository.existsCustomerByIdAndDeleteFlag(customerId, Status.ACTIVE.getStatus())){

            this.customerAddressRepository
                    .deleteAllCustomerAddressByCustomerId(customerId, Status.IN_ACTIVE.getStatus());

            this.customerRepository.deleteCustomerById(customerId, Status.IN_ACTIVE.getStatus());
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");
        }else{
            responseDTO.setContent(false);
            responseDTO.setMessage("Delete fail customerId' not found");
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO updateCustomer(CustomerUpdateDTO dto) {

        ModelMapper modelMapper = new ModelMapper();

        Customer customerEntity = modelMapper.map(dto, Customer.class);
        customerEntity.setFullName(dto.getLastName() + " " + dto.getFirstName());
        customerEntity.setId(dto.getCustomerId());
        this.customerRepository.save(customerEntity);
        responseDTO.setContent(true);
        responseDTO.setMessage("Update success");
        return responseDTO;

    }


    @Override
    public void updateAvatar(Avatar avatar, Long id) {
        this.customerRepository.updateAvatar(avatar, id);
    }
}

