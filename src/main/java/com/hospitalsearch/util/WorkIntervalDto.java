package com.hospitalsearch.util;


import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.WorkInterval;

/**
 * Created by igortsapyak on 10.05.16.
 */
public class WorkIntervalDto {

    private String status;

    private WorkInterval workInterval;

    private DoctorInfo doctorInfo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WorkInterval getWorkInterval() {
        return workInterval;
    }

    public void setWorkInterval(WorkInterval workInterval) {
        this.workInterval = workInterval;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }
}
