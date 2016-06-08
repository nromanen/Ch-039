package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.DoctorInfoDAO;
import com.hospitalsearch.dao.HospitalDAO;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.ManagerService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by igortsapyak on 06.06.16.
 */
@Service
public class ManagerServiceImpl implements ManagerService {


    @Autowired(required = true)
    DoctorInfoDAO doctorInfoDAO;
    @Autowired(required = true)
    private HospitalDAO hospitalDAO;
    @Autowired(required = true)
    private UserDAO userDAO;

    @Override
    public void applyManager(Map<String, Long> hospitalData) {

        User user = userDAO.getById(hospitalData.get("userId"));
        Hospital hospital = hospitalDAO.getById(hospitalData.get("hospitalId"));
        List<User> managers = new ArrayList<>();
        managers.add(user);
        hospital.setManagers(managers);
        hospitalDAO.update(hospital);

    }

    @Override
    public List<UserDetail> getDoctorsByManager() {

        return doctorInfoDAO.findByManagerId(userDAO.getByEmail(PrincipalConverter.getPrincipal()).getId());
    }

    @Override
    public void deleteHospitalManager(Long hospitalId){
        Hospital hospital = hospitalDAO.getById(hospitalId);
        hospital.getManagers().clear();
        hospitalDAO.update(hospital);

    }


}
