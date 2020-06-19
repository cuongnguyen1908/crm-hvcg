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

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api")
public class AcountTypeController {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @PostMapping("/accountType")
    public ResponseEntity<ResponseDTO> createAccountType(@RequestBody AccountTypeCreateDTO dto) {
        System.out.println(dto.getName());
        AccountType accountTypeEntity = new AccountType();
        accountTypeEntity.setName(dto.getName());
                this.accountTypeRepository.save(accountTypeEntity);

        ResponseDTO responseDTO = new ResponseDTO("Create success");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }

}
