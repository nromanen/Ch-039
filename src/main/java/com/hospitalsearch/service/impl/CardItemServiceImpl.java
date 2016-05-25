package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.CardItemDAO;
import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.CardItemService;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class CardItemServiceImpl implements CardItemService {
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

    @Autowired
    private CardItemDAO cardItemDAO;

    @Autowired
    UserService userService;

    @Override
    public void add(CardItem cardItem, String doctorEmail) {
        User doctor = userService.getByEmail(doctorEmail);
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(dateTime);
        cardItem.setDate(date);
        cardItem.setDoctor(doctor);
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
        if (entity != null) {
            entity.setPrescription(cardItem.getPrescription());
            entity.setResult(cardItem.getResult());
            entity.setComplaint(cardItem.getComplaint());
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
        CardItem cardItemFromDB = cardItemDAO.getById(cardItem.getId());
        if (cardItemFromDB.getDoctor().getEmail().equals(doctorEmail)) {
            update(cardItem);
            return true;
        }
        return false;
    }


    @Override
    public CardItem getById(Long id) {
        return cardItemDAO.getById(id);
    }

}
