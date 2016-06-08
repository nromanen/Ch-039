package com.hospitalsearch.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "patientcard")
public class PatientCard{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientcard_gen")
    @SequenceGenerator(name = "patientcard_gen", sequenceName = "patientcard_id_seq", initialValue = 1, allocationSize = 1)

    private Long id;

    @OneToMany(mappedBy = "patientCard", fetch = FetchType.EAGER)
    List<CardItem> cardItems;
    @OneToOne
    UserDetail userDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CardItem> getCardItems() {
        return cardItems;
    }

    public void setCardItems(List<CardItem> cardItems) {
        this.cardItems = cardItems;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    public String toString() {
        return "PatientCard{" +
                "id=" + id +
                '}';
    }
	   
}
