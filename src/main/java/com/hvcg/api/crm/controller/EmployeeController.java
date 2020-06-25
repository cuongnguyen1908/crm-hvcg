package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.constant.Status;
import com.hvcg.api.crm.dto.EmployeeDTO;
import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeCreateDTO;
import com.hvcg.api.crm.dto.createDTO.EmployeeUpdateDTO;
import com.hvcg.api.crm.entity.AccountType;
import com.hvcg.api.crm.entity.Employee;
import com.hvcg.api.crm.entity.Region;
import com.hvcg.api.crm.exception.NotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/employees")
    public Page<EmployeeDTO> getAllCustomer(Pageable pageable) {
        return this.employeeRepository.findAllEmployee(pageable, Status.ACTIVE.getStatus());
    }


    @PostMapping("/employees")
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody EmployeeCreateDTO dto) {

        //check user exist
        if (this.employeeAccountRepository.existsByUsername(dto.getUsername())) {
            throw new NotFoundException("User name has exist");
        }
        ;

        //check region exist
        Optional<Region> optionalRegion = this.regionRepository.findByIdAndDeleteFlag(dto.getRegionId(),
                Status.ACTIVE.getStatus());
        if (!optionalRegion.isPresent()) {
            throw new NotFoundException("Region not found");
        }

        //check account type exist
        Optional<AccountType> optionalAccountType =
                this.accountTypeRepository.findAccountTypeByIdAndDeleteFlag(dto.getTypeAccountId(),
                        Status.ACTIVE.getStatus());
        if (!optionalAccountType.isPresent()) {
            throw new NotFoundException("Region not found");
        }
        this.employeeService.saveEmployee(dto, optionalRegion.get(), optionalAccountType.get());

        ResponseDTO responseDTO = new ResponseDTO("Create success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("employees/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> optionalEmployeeDTO = this.employeeRepository.findEmployeeById(employeeId,
                Status.ACTIVE.getStatus());
        if (optionalEmployeeDTO.isPresent()) {
            return optionalEmployeeDTO.get();
        } else {
            throw new NotFoundException("Can't find employee id - " + employeeId);
        }

    }

    @GetMapping("/employees/find")
    public Page<EmployeeDTO> searchCustomers(Pageable pageable, @RequestParam("s") String s) {
        return this.employeeRepository.searchAllEmployee(pageable, s, Status.ACTIVE.getStatus());
    }

    @DeleteMapping("employees/{employeeId}")
    public ResponseEntity<ResponseDTO> deleteEmployees(@PathVariable Long employeeId) {
        this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("Can't find employee id " +
                "- " + employeeId));

        Long accountId = this.employeeRepository.findAccountIdByEmployeeId(employeeId);
        this.employeeAccountRepository.deleteAccountById(accountId, Status.IN_ACTIVE.getStatus());
        this.employeeRepository.deleteCustomerByID(employeeId, Status.IN_ACTIVE.getStatus());


        ResponseDTO responseDTO = new ResponseDTO("Delete success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("employees/{employeeId}")
    public ResponseEntity<ResponseDTO> updateEmployee(@PathVariable Long employeeId,
                                                      @RequestBody EmployeeUpdateDTO dto) {
        Optional<Region> optionalRegion = this.regionRepository.findByIdAndDeleteFlag(dto.getRegionId(),
                Status.ACTIVE.getStatus());
        if (!optionalRegion.isPresent()) {
            throw new NotFoundException("Can't find region id - " + dto.getRegionId());
        }


        Optional<Employee> optionalEmployee = this.employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            this.employeeRepository.updateEmployee(
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getFirstName() + " " + dto.getLastName(),
                    dto.getDob(),
                    dto.getEmail(),
                    dto.getAddress(),
                    dto.getPhone(),
                    dto.getIdentityNumber(),
                    dto.getPosition(),
                    dto.getBankName(),
                    dto.getBankAccount(),
                    optionalRegion.get(),
                    employeeId);
        } else {
            throw new NotFoundException("Can't find employee id - " + employeeId);
        }


        ResponseDTO responseDTO = new ResponseDTO("Update success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
