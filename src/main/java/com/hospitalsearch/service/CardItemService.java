package com.hospitalsearch.service;

import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public interface CardItemService {
    void add(CardItem cardItem, String doctorEmail);
    void remove(Long id);
    void update(CardItem cardItem);
    boolean persist(CardItem cardItem, String doctorEmail, Long userId);
    CardItem getById(Long id);
    List<CardItem> getCardItemList(User user, int pageNumber, int pageSize);
    Long countOfItems(PatientCard patientCard);
}
