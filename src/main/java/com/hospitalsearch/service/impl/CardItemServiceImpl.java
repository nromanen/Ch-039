package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.CardItemDAO;
import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.CardItemService;
import com.hospitalsearch.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CardItemServiceImpl implements CardItemService {

    final static Logger log = Logger.getLogger(CardItemService.class);

    @Autowired
    private CardItemDAO dao;

    @Autowired
    UserService userService;

    @Override
    public void add(CardItem cardItem, String doctorEmail) {
        try{
            User doctor = userService.getByEmail(doctorEmail);
            LocalDateTime dateTime = LocalDateTime.now();
            Timestamp date = Timestamp.valueOf(dateTime);
            cardItem.setDate(date);
            cardItem.setDoctor(doctor);
            dao.save(cardItem);
            log.info("Save card item" +cardItem);
        }catch (Exception e){
            log.error("Saving card item" + cardItem+" - " + e);
        }
    }

    @Override
    public void remove(Long id) {
        try{
            CardItem cardItem = dao.getById(id);
            dao.delete(cardItem);
            log.info("Delete card item" +cardItem);
        }catch (Exception e){
            log.error("Deleting card item id:" +id+" - " + e);
        }

    }

    @Override
    public void update(CardItem cardItem) {
        try {
            CardItem entity = dao.getById(cardItem.getId());
            if (entity != null) {
                entity.setPrescription(cardItem.getPrescription());
                entity.setResult(cardItem.getResult());
                entity.setComplaint(cardItem.getComplaint());
                log.info("Update card item" +cardItem);
            }
        }catch (Exception e){
            log.error("Updating card item id:" +cardItem+" - " + e);
        }
    }

    @Override
    public boolean persist(CardItem cardItem, String doctorEmail,Long userId) {

        PatientCard patientCard = userService.getById(userId).getUserDetails().getPatientCard();
        cardItem.setPatientCard(patientCard);
        if (cardItem.getId() == null) {
            add(cardItem, doctorEmail);
            return true;
        }
        CardItem cardItemFromDB = dao.getById(cardItem.getId());
        if (cardItemFromDB.getDoctor().getEmail().equals(doctorEmail)) {
            update(cardItem);
            return true;
        }
        return false;
    }


    @Override
    public CardItem getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<CardItem> getCardItemList(User user, int pageNumber, int pageSize) {
        List<CardItem> cardItems = new ArrayList<>();
        try{
            cardItems = dao.getCardItemList(user,pageNumber,pageSize);
            log.info("Get card item list for user"+ user);
        }catch (Exception e){
            log.error("Getting card item list for user"+ user);
        }
        return cardItems;
    }

    @Override
    public Long countOfItems(PatientCard patientCard) {
        Long count = 0L;
        try{
            count = dao.countOfItems(patientCard);
            log.info("Get count of card items for patient card: "+ patientCard);
        }catch (Exception e){
            log.error("Getting count of card items for patient card: "+ patientCard);
        }
        return count;
    }

}
