package com.hvcg.api.crm.dto;

public class AvatarDTO {

    private Long id;

    private String name;

    private String url;

    private String thumbUrl;

    public AvatarDTO(Long id, String name, String url, String thumbUrl) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.thumbUrl = thumbUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
