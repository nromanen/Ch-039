/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hospitalsearch.util;

import java.util.Comparator;
import java.util.List;


/**
 *
 * @author kpaul
 * @param <T> entity, which can be used for making pagination
 */
public final class Page<T>{
	
	private int resultListCount;
    private int pageSize;
    private int pageCount;
    private boolean paginated;
    private boolean sortType;
    private List<T> items;
    private Class<T> clazz;
    private Comparator<T> comp;

    
    public Page(List<T> items,boolean paginated,int pageCount,int pageSize,int resultListCount,boolean sortType,Comparator<T> comp) {
    	this.items = items;
    	this.paginated = paginated;
    	this.pageCount = pageCount;
    	this.pageSize = pageSize;
    	this.resultListCount = resultListCount;
    	this.sortType = sortType;
    	this.comp = comp;
        this.clazz = (Class<T>) Page.class.getTypeParameters()[0].getClass();
        
    }

    public void makeSort(){
    	if(sortType) 
    		this.items.sort(this.comp) ;
    	else 
    		this.items.sort(this.comp.reversed());
    }
    public void setSortType(boolean sortType) {
		this.sortType = sortType;
	}
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public int getResultListCount() {
        return resultListCount;
    }

    
    public Integer getPageCount() {
        return pageCount;
    }

    public List<T> getPageItems() {
    	
    	return items;
    }

    public boolean isPaginated() {
        return paginated;
    }
        

    public boolean getSortType() {
		return sortType;
	}
}
