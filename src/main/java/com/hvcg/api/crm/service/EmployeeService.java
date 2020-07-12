package com.hvcg.api.crm.service;

import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeUpdateDTO;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {

    ResponseDTO createEmployee(EmployeeCreateDTO dto, HttpServletRequest request);

    ResponseDTO deleteEmployee(Long employeeId);

    ResponseDTO updateEmployee(EmployeeUpdateDTO dto, HttpServletRequest request);
}
