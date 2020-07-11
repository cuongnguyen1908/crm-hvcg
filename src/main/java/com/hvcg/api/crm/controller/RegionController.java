package com.hvcg.api.crm.controller;


import com.hvcg.api.crm.dto.ResponseDTO;
import com.hvcg.api.crm.dto.createDTO.RegionCreateDTO;
import com.hvcg.api.crm.entity.Region;
import com.hvcg.api.crm.exception.NotFoundException;
import com.hvcg.api.crm.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/region")
public class RegionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionController.class);


    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ResponseDTO  responseDTO;


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createRegion(@RequestBody RegionCreateDTO dto) {
        if (this.regionRepository.existsRegionByName(dto.getName())) {
            LOGGER.error("Error 404...");
            throw new NotFoundException("Region name has exist");
        }
        Region region = new Region();
        region.setName(dto.getName());
        region.setAddress(dto.getAddress());
        region.setAliasName(dto.getAliasName());
        this.regionRepository.save(region);


        responseDTO.setContent(dto);
        responseDTO.setMessage("Create success!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
