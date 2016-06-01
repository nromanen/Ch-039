package com.hospitalsearch.service;

import com.hospitalsearch.entity.CardItem;
import org.springframework.stereotype.Component;

@Component
public interface CardItemService {
    void add(CardItem cardItem, String doctorEmail);
    void remove(Long id);
    void update(CardItem cardItem);
    boolean persist(CardItem cardItem, String doctorEmail, Long userId);
    CardItem getById(Long id);
}
