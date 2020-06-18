package com.hvcg.api.crm.dto;

import com.hvcg.api.crm.entity.Customer;


public class AvatarDTO {
    private String name;

    private String url;

    private String thumbUrl;



    public AvatarDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
