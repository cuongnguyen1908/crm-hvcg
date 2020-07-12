package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeUpdateDTO;
import com.hvcg.api.crm.repository.EmployeeAccountRepository;
import com.hvcg.api.crm.repository.EmployeeRepository;
import com.hvcg.api.crm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private EmployeeAccountRepository employeeAccountRepository;



    @Override
    public ResponseDTO createEmployee(EmployeeCreateDTO dto, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);

        //create employee account
         this.employeeAccountRepository.createEmployeeAccount(username, Status.ACTIVE.getStatus(),
                dto.getUsername(), dto.getPassword(), dto.getTypeAccountId());

        int accountEmployeeId =  this.employeeAccountRepository.getIdLastInsert();

        //create employee
        this.employeeRepository.createEmployee(
                username,
                Status.ACTIVE.getStatus(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getLastName() + " " + dto.getFirstName(),
                dto.getGender(),
                dto.getDob(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getIdentityNumber(),
                dto.getPosition(),
                dto.getBankName(),
                dto.getBankAccount(),
                dto.getRegionId(),
                Long.valueOf(accountEmployeeId));



        responseDTO.setContent(true);
        responseDTO.setMessage("Create success");
        return responseDTO;
    }

    @Override
    public ResponseDTO deleteEmployee(Long employeeId) {
        Long accountEmployeeId = this.employeeRepository.getEmployeeAccountIdByEmployeeId(employeeId);
        if (accountEmployeeId != null){

            this.employeeAccountRepository.deleteAccountById(accountEmployeeId, Status.IN_ACTIVE.getStatus());
            this.employeeRepository.deleteCustomerById(employeeId, Status.IN_ACTIVE.getStatus());
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");
        }else{
            responseDTO.setContent(false);
            responseDTO.setMessage("Delete fail employeeId' not found");
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO updateEmployee(EmployeeUpdateDTO dto, HttpServletRequest request) {
        String username = CommonUltils.getUsernameByRequestHeader(request);
        this.employeeRepository.updateEmployee(username, new Date(), dto.getFirstName(), dto.getLastName(), dto.getLastName() + " " + dto.getFirstName(), dto.getGender(), dto.getDob(), dto.getEmail(), dto.getAddress(), dto.getPhone(),dto.getIdentityNumber(),dto.getPosition(),dto.getBankName(),dto.getBankAccount(),dto.getRegionId(), dto.getEmployeeId());
        responseDTO.setContent(dto);
        responseDTO.setMessage("Update success");
        return responseDTO;
    }
}
