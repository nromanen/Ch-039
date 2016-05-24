package com.hospitalsearch.service.mapper;


import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserBeanMapper implements EntityMapper<User, UserDto> {

    @Override
    public UserDto convertToBean(User user) {
        UserDto dto = new UserDto();
//        dto.setId(user.getId());
//        dto.setEmail(user.getEmail());
//        dto.setFirstName(user.getFirstName());
//        dto.setLastName(user.getLastName());
//        dto.setPhone(user.getPhone());
//        dto.setBirthDate(user.getBirthDate());
//        dto.setImagePath(user.getImagePath());
//        dto.setGender(user.getGender());
//        dto.setAddress(user.getAddress());
        return dto;
    }
}
