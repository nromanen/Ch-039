package com.hospitalsearch.dao.impl;

import com.hospitalsearch.dao.CardItemDAO;
import com.hospitalsearch.entity.CardItem;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository ("cardItemDAO")
public class CardItemDAOImpl extends GenericDAOImpl<CardItem,Long> implements CardItemDAO {

    @Autowired
       public CardItemDAOImpl(SessionFactory factory) {
        super();
        this.setSessionFactory(factory);
    }

}
