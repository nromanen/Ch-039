package com.hospitalsearch.dao;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by deplague on 5/10/16.
 */

@Component
public interface DoctorInfoDAO extends GenericDAO<DoctorInfo,Long>{
    public List<UserDetail> findByDepartmentId(Long id);
}
