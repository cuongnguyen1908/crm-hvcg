package com.hvcg.api.crm.service.impl;

import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.entity.AccountType;
import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.EmployeeAccount;
import com.hvcg.api.crm.entity.Region;
import com.hvcg.api.crm.repository.EmployeeRepository;
import com.hvcg.api.crm.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public void saveCustomer(EmployeeCreateDTO dto, Region region, AccountType accountType) {
        ModelMapper modelMapper = new ModelMapper();
        Employee employeeEntity = modelMapper.map(dto, Employee.class);
        EmployeeAccount employeeAccount = new EmployeeAccount(dto.getUsername(), dto.getPassword(), accountType);
        employeeEntity.setFullName(dto.getFirstName() + dto.getLastName());
        employeeEntity.setRegion(region);
        employeeEntity.setEmployeeAccount(employeeAccount);
        this.employeeRepository.save(employeeEntity);
    }
}
