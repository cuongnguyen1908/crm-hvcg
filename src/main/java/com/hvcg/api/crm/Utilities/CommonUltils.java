package com.hvcg.api.crm.Utilities;
import com.hvcg.api.crm.dto.ResponsePagingDTO;
import com.hvcg.api.crm.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CommonUltils {

    private static String jwtPreFix;

    public CommonUltils(@Value("${application.jwt.tokenPrefix}") String jwtPreFix) {
        this.jwtPreFix = jwtPreFix;

    }


    public static ResponsePagingDTO setResponsePagingDTO(Page<?> pageObject) {
        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO();

        responsePagingDTO.setPageIndex((pageObject.getNumber() + 1));
        responsePagingDTO.setTotalElements(pageObject.getTotalElements());
        responsePagingDTO.setPageSize(pageObject.getSize());
        responsePagingDTO.setContent(pageObject.getContent());
        return responsePagingDTO;
    }

    public static String getUsernameByRequestHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace(jwtPreFix, "");
        return JwtUtils.getUserNameFromJwtToken(token);

    }
}
