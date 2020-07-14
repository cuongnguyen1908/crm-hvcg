package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeUpdateDTO;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {

    void createEmployee(EmployeeCreateDTO dto, HttpServletRequest request);

    void updateEmployee(EmployeeUpdateDTO dto, HttpServletRequest request);
}
