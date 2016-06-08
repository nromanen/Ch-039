package com.hospitalsearch.util;

public class PageConfigDTO {

    private String type;
    private Integer itemsPerPage;
    private String currentSearchQuery;

    public PageConfigDTO() {

    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrentSearchQuery(String currentSearchQuery) {
        this.currentSearchQuery = currentSearchQuery;
    }

    public String getCurrentSearchQuery() {
        return currentSearchQuery;
    }

}
