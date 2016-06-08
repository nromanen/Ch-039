package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.CardItemDAO;
import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository ("cardItemDAO")
public class CardItemDAOImpl extends GenericDAOImpl<CardItem,Long> implements CardItemDAO {

    @Autowired
       public CardItemDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }


    @Override
    public List<CardItem> getCardItemList(User user) {
        UserDetail userDetail = user.getUserDetails();
        userDetail.getPatientCard();
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(CardItem.class, "item")
                .add(Restrictions.eq("item.patientCard", userDetail.getPatientCard())).addOrder(Order.desc("item.date"));
        return criteria.list();
    }
}
