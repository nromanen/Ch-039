package com.hospitalsearch.util;

public class PageConfigDTO {

    private Boolean sortType;
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

    public Boolean getSortType() {
		return sortType;
	}

	public void setSortType(Boolean sortType) {
		this.sortType = sortType;
	}

	public void setCurrentSearchQuery(String currentSearchQuery) {
        this.currentSearchQuery = currentSearchQuery;
    }

    public String getCurrentSearchQuery() {
        return currentSearchQuery;
    }

}
