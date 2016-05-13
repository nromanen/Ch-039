package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */

@Transactional
public interface UserDetailService {
    void save(UserDetail newUserDetail);
    void delete(UserDetail userDetail);
    void update(UserDetail updatedUserDetail);
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    UserDetail getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<UserDetail> getAll();
}
