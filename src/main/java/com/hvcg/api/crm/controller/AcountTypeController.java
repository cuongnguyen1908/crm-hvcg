package com.hvcg.api.crm.controller;

import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.AccountTypeCreateDTO;
import com.hvcg.api.crm.entity.AccountType;
import com.hvcg.api.crm.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounttype")
public class AcountTypeController {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccountType(@RequestBody AccountTypeCreateDTO dto) {
        AccountType accountTypeEntity = new AccountType();
        accountTypeEntity.setName(dto.getName());
        this.accountTypeRepository.save(accountTypeEntity);

        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
