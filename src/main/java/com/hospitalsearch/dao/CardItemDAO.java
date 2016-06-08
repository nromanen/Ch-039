package com.hospitalsearch.dao;

import com.hospitalsearch.dao.GenericDAO;
import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CardItemDAO extends GenericDAO<CardItem, Long> {
    List<CardItem> getCardItemList(User user, int pageNumber, int pageSize);
    Long countOfItems(PatientCard patientCard);
}
