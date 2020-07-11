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
    public Page<EmployeeDTO> getAllCustomer(Pageable pageable) {
        return this.employeeRepository.findAllEmployee(pageable, Status.ACTIVE.getStatus());
    }


    @PostMapping("/create")
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

        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getById/{employeeId}")
    public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> optionalEmployeeDTO = this.employeeRepository.findEmployeeById(employeeId,
                Status.ACTIVE.getStatus());
        if (optionalEmployeeDTO.isPresent()) {
            responseDTO.setContent(optionalEmployeeDTO.get());
            responseDTO.setMessage(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } else {
            throw new NotFoundException("Can't find employee id - " + employeeId);
        }

    }

    @GetMapping("/search")
    public Page<EmployeeDTO> searchCustomers(Pageable pageable, @RequestParam("s") String s) {
        return this.employeeRepository.searchAllEmployee(pageable, s, Status.ACTIVE.getStatus());
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<ResponseDTO> deleteEmployees(@PathVariable Long employeeId) {
        this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("Can't find employee id " +
                "- " + employeeId));

        Long accountId = this.employeeRepository.findAccountIdByEmployeeId(employeeId);
        this.employeeAccountRepository.deleteAccountById(accountId, Status.IN_ACTIVE.getStatus());
        this.employeeRepository.deleteCustomerByID(employeeId, Status.IN_ACTIVE.getStatus());


        responseDTO.setContent(true);
        responseDTO.setMessage("Delete success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{employeeId}")
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
                    dto.getGender(),
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


        responseDTO.setContent(dto);
        responseDTO.setMessage("Update success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
