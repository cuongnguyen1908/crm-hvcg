package com.hvcg.api.crm.exception;

import com.hvcg.api.crm.dto.ResponseDTO;
import org.springframework.context.annotation.Bean;

public class Response {

    @Bean
    public ResponseDTO responseDTO() {
        return new ResponseDTO();
    }

}
