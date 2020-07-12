package com.hvcg.api.crm.dto;


import org.springframework.stereotype.Component;

@Component
public class ResponsePagingDTO<T> {

    private T content;
    private int pageSize;
    private int pageIndex;
    private Long totalElements;

    public ResponsePagingDTO() {
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
