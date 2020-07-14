package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.Utilities.CommonUltils;
import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.ResponsePagingDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeUpdateDTO;
import com.hvcg.api.crm.repository.AccountTypeRepository;
import com.hvcg.api.crm.repository.EmployeeAccountRepository;
import com.hvcg.api.crm.repository.EmployeeRepository;
import com.hvcg.api.crm.repository.RegionRepository;
import com.hvcg.api.crm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeAccountRepository employeeAccountRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ResponseDTO responseDTO;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllCustomer() {
        responseDTO.setContent(this.employeeRepository.getAllEmployee(Status.ACTIVE.getStatus()));
        responseDTO.setMessage(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody EmployeeCreateDTO dto, HttpServletRequest request) {
        //check valid gender
        if (!(dto.getGender() == 0 || dto.getGender() == 1 || dto.getGender() == 2)) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail by Gender invalid");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        //check username of accountEmployee exist
        if (this.employeeAccountRepository.existsEmployeeAccountByUsername(dto.getUsername())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Username has exist");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        };

        //check region exist

        if (!this.regionRepository.existsRegionByIdAndDeleteFlag(dto.getRegionId(), Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Not found region id - " + dto.getRegionId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        };

        //check account type exist
        if (!this.accountTypeRepository.existsAccountTypeByIdAndDeleteFlag(dto.getTypeAccountId(), Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Not found accountType id - " + dto.getTypeAccountId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        };


        this.employeeService.createEmployee(dto, request);

        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<ResponseDTO> getEmployeeById(@RequestParam Long employeeId) {
        Optional<EmployeeDTO> optionalEmployee = this.employeeRepository.getEmployeeById(employeeId,
                Status.ACTIVE.getStatus());

        optionalEmployee.ifPresentOrElse(res -> {
            responseDTO.setContent(res);
            responseDTO.setMessage(null);
        },() -> {
            responseDTO.setContent(false);
            responseDTO.setMessage("Can't find employee id - " + employeeId);
        });
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }


    @GetMapping("/search")
    public ResponseEntity<ResponsePagingDTO> searchEmployees(
            Pageable pageable,
            @RequestParam(value = "textSearch", required = false) String textSearch) {
        if (textSearch != null && textSearch.length() > 0) {
            Page<EmployeeDTO> result = this.employeeRepository
                    .searchAllEmployeeByFullname(pageable, textSearch.trim().toLowerCase(), Status.ACTIVE.getStatus());
            return new ResponseEntity<>(CommonUltils.setResponsePagingDTO(result), HttpStatus.OK);
        }

        Page<EmployeeDTO> result = this.employeeRepository
                .searchAllEmployeeByFullname(pageable, "", Status.ACTIVE.getStatus());

        return new ResponseEntity<>(CommonUltils.setResponsePagingDTO(result), HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteEmployees(@RequestParam Long employeeId) {
        //exist employee
        if (!this.employeeRepository.existsEmployeeByIdAndDeleteFlag(employeeId, Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Delete fail employeeId' not found with id - " + employeeId);
        }
        Long accountEmployeeId = this.employeeRepository.getEmployeeAccountIdByEmployeeId(employeeId);
        if (accountEmployeeId != null){
            this.employeeAccountRepository.deleteAccountById(accountEmployeeId, Status.IN_ACTIVE.getStatus());
            this.employeeRepository.deleteCustomerById(employeeId, Status.IN_ACTIVE.getStatus());
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");
        }else{
            this.employeeRepository.deleteCustomerById(employeeId, Status.IN_ACTIVE.getStatus());
            responseDTO.setContent(true);
            responseDTO.setMessage("Delete success");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody EmployeeUpdateDTO dto, HttpServletRequest request) {
        //check valid gender
        if (!(dto.getGender() == 0 || dto.getGender() == 1 || dto.getGender() == 2)) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Create fail by Gender invalid");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        //check region exist

        if (!this.regionRepository.existsRegionByIdAndDeleteFlag(dto.getRegionId(), Status.ACTIVE.getStatus())) {
            responseDTO.setContent(false);
            responseDTO.setMessage("Not found region id - " + dto.getRegionId());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        };

        if (this.employeeRepository.existsEmployeeByIdAndDeleteFlag(dto.getEmployeeId(), Status.ACTIVE.getStatus())) {
             this.employeeService.updateEmployee(dto, request);
            responseDTO.setContent(dto);
            responseDTO.setMessage("Update success");
        } else {
            responseDTO.setContent(false);
            responseDTO.setMessage("Update fail employee not found id - " + dto.getEmployeeId());
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
