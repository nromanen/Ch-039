package com.hospitalsearch.dao;

import com.hospitalsearch.dao.GenericDAO;
import com.hospitalsearch.entity.CardItem;
import org.springframework.stereotype.Component;

@Component
public interface CardItemDAO extends GenericDAO<CardItem, Long> {
}
