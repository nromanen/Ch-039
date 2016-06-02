package com.hospitalsearch.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patientcard")
public class PatientCard{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patientcard_gen")
    @SequenceGenerator(name = "patientcard_gen", sequenceName = "patientcard_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "patientCard", fetch = FetchType.EAGER)
    List<CardItem> cardItems;

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

    @Override
    public String toString() {
        return "PatientCard{" +
                "id=" + id +
                '}';
    }
	   
}
