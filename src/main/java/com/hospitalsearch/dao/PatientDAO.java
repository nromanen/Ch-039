package com.hospitalsearch.dao;

import com.hospitalsearch.entity.PatientInfo;
import org.springframework.stereotype.Component;

/**
 * Created by deplague on 5/11/16.
 */
@Component
public interface PatientDAO extends  GenericDAO<PatientInfo,Long> {
}
