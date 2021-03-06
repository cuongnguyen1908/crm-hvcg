package com.hvcg.api.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

@Entity(name = "avatar")
@Table(name = "avatar")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Avatar extends BaseEntity{

    @Column(name="name", length = 250)
    private String name;

    @Column(name="url", length = 500)
    private String url;

    @Column(name="thumb_url", length = 500)
    private String thumbUrl;

    @OneToOne(mappedBy = "avatar",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Customer customer;

    public Avatar() {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
