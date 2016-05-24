package com.hospitalsearch.service;

import com.hospitalsearch.entity.CardItem;
import org.springframework.stereotype.Component;

@Component
public interface CardItemService {
    void add(CardItem cardItem);
    void remove(Long id);
    void update(CardItem cardItem);
    void persist(CardItem cardItem);
    CardItem getById(Long id);
}
