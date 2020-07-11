package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.CustomerCreateDTO;
import com.hvcg.api.crm.dto.updateDTO.CustomerUpdateDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.CustomerRepository;
import com.hvcg.api.crm.service.CustomerAddressService;
import com.hvcg.api.crm.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private ResponseDTO responseDTO;



    @Override
    public Customer createCustomer(CustomerCreateDTO dto) {
        if (!(dto.getGender() == 0 || dto.getGender() == 1 || dto.getGender() == 2) ) {
            throw new NotFoundException("Gender is incorect format");
        }
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        System.out.println(sdt);
        ModelMapper modelMapper = new ModelMapper();
        Customer customerEntity = modelMapper.map(dto, Customer.class);
        customerEntity.setFullName(dto.getLastName() + " " + dto.getFirstName());
        return this.customerRepository.save(customerEntity);
    }


    @Override
    public ResponseDTO deleteCustomer(Long customerId) {
        //delete parent

        //find customer
        Optional<Customer> customer = this.customerRepository
                .findCustomerByIdAndDeleteFlag(customerId, Status.ACTIVE.getStatus());
        if (customer.isPresent()){
            Customer customerOpt = customer.get();
            //delete child
            this.customerAddressService.deleteAllCustomerAddressByCustomerId(customerId, true);
            //set flag delete = true (1)

            customerOpt.setDeleteFlag(Status.IN_ACTIVE.getStatus());
            this.customerRepository.save(customerOpt);
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");


        }else{
            responseDTO.setContent(false);
            responseDTO.setMessage("Delete fail");
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

//    @Override
//    public Page<Customer> searchAllCustomer(Pageable pageable, String searchValue) {
//        if (searchValue != null && searchValue.trim().length() > 0) {
//            return this.customerRepository.searchAllCustomer(pageable, searchValue.toLowerCase(), false);
//        }
//        return this.customerRepository.findAll(pageable, false);
//    }

    @Override
    public void updateAvatar(Avatar avatar, Long id) {
        this.customerRepository.updateAvatar(avatar, id);
    }
}

