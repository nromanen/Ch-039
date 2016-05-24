package com.hospitalsearch.service;

import com.hospitalsearch.entity.PatientCard;
import org.springframework.stereotype.Component;

@Component
public interface PatientCardService {
        void save(PatientCard patientCard);
        void remove(PatientCard patientCard);
        PatientCard getById(Long id);
        PatientCard add(PatientCard patientCard);

}
