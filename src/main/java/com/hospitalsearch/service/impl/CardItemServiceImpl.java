package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.CardItemDAO;
import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.service.CardItemService;
import com.hospitalsearch.service.PatientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class CardItemServiceImpl implements CardItemService {
    
    @Autowired
    private CardItemDAO cardItemDAO;

    @Autowired
    private PatientCardService patientCardService;

    @Override
    public void add(CardItem cardItem) {
        LocalDate date = LocalDate.now();
        cardItem.setDate(date);
        cardItemDAO.save(cardItem);
    }

    @Override
    public void remove(Long id) {
        CardItem cardItem = cardItemDAO.getById(id);
        cardItemDAO.delete(cardItem);
    }

    @Override
    public void update(CardItem cardItem) {
        CardItem entity = cardItemDAO.getById(cardItem.getId());
        LocalDate date = LocalDate.now();
        if (entity != null) {
            entity.setDate(date);
            entity.setPrescription(cardItem.getPrescription());
            entity.setResult(cardItem.getResult());
            entity.setComplaint(cardItem.getComplaint());
        }
    }

    @Override
    public void persist(CardItem cardItem){

        if(cardItem.getId()==null) {
            add(cardItem);
        }else{
            update(cardItem);
        }
    }



    @Override
    public CardItem getById(Long id) {
        return cardItemDAO.getById(id);
    }

}
