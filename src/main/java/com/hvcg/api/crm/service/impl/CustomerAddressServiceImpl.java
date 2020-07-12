package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
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

    @Autowired
    private ResponseDTO responseDTO;


    @Override
    public ResponseDTO createCustomerAddress(CustomerAddressCreateDTO dto, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);
        this.customerAddressRepository.createCustomerAddress(dto.getAddress(), dto.getContactPerson(), dto.getContactPhone(), dto.getDescription(), dto.getCustomerId(), Status.ACTIVE.getStatus(), username);
        responseDTO.setContent(true);
        responseDTO.setMessage("Create success");
        return responseDTO;
    }

    //delete by id
    @Override
    public ResponseDTO deleteCustomerAddressById(Long customerAddressId) {
        if (this.customerAddressRepository
                .existsCustomerAddressByIdAndDeleteFlag(customerAddressId, Status.ACTIVE.getStatus())) {
            this.customerAddressRepository.deleteCustomerAddressById(customerAddressId, Status.IN_ACTIVE.getStatus());
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");
        }else{
            responseDTO.setContent(false);
            responseDTO.setMessage("Delete fail customerAddressId not exist");
        }

        return responseDTO;

    }

//    @Override
//    public ResponseDTO deleteCustomerAddressByCustomerId(Long customerId) {
//            this.customerAddressRepository.deleteAllCustomerAddressByCustomerId(customerId, Status.IN_ACTIVE.getStatus());
//            responseDTO.setContent(true);
//            responseDTO.setMessage("Delete success");
//        return responseDTO;
//    }

    @Override
    public ResponseDTO updateCustomerAddress(CustomerAddressUpdateDTO dto) {
        ModelMapper modelMapper = new ModelMapper();

        CustomerAddress customerAddressEntity = modelMapper.map(dto, CustomerAddress.class);
        customerAddressEntity.setId(dto.getCustomerAddressId());
        this.customerAddressRepository.save(customerAddressEntity);
        responseDTO.setContent(dto);
        responseDTO.setMessage("Update success");
        return responseDTO;
    }
}
