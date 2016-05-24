package com.hospitalsearch.util;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.WorkInterval;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by igortsapyak on 09.05.16.
 */
public class WorkIntervalBuilder {

    private WorkInterval workIntervalInstance = new WorkInterval();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public WorkIntervalBuilder setStart_date(String start_date){
        workIntervalInstance.setStart_date(LocalDateTime.parse(start_date, formatter));
        return this;
    }

    public WorkIntervalBuilder setEnd_date(String end_date){
        workIntervalInstance.setEnd_date(LocalDateTime.parse(end_date, formatter));
        return this;
    }

    public WorkIntervalBuilder setText(String text){
        workIntervalInstance.setText(text);
        return this;
    }

    public WorkIntervalBuilder setId(Long id){
        workIntervalInstance.setId(id);
        return this;
    }

    public WorkIntervalBuilder setDoctorInfo(DoctorInfo doctorInfo){
        workIntervalInstance.setDoctorInfo(doctorInfo);
        return this;
    }


    public WorkInterval build(){
        return workIntervalInstance;
    }

}
