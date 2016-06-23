package com.hospitalsearch.service.impl;

import com.hospitalsearch.entity.Appointment;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.util.AppointmentBuilder;
import com.hospitalsearch.dto.AppointmentDto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by igortsapyak on 11.05.16.
 */
public class AppointmentDtoService {

    private static final String REPLACE_PATTERN = "[\\[,\\]]";

    public static AppointmentDto createAppointmentDto(Map<String, String[]> dataMap, DoctorInfo doctorInfo){

        Map<String, String> convertedMap = mapConvert(dataMap);

        AppointmentDto AppointmentDto = new AppointmentDto();

        AppointmentDto.setDoctorInfo(doctorInfo);

        AppointmentDto.setStatus(convertedMap.get("!nativeeditor_status"));

        AppointmentDto.setAppointment(create(convertedMap ,doctorInfo, AppointmentDto.getStatus()));

        return AppointmentDto;
    }

    private static Map<String, String> mapConvert(Map<String, String[]> dataMap){
        Map<String, String> convertedMap = new HashMap<>();
        for (Map.Entry<String, String[]> node: dataMap.entrySet()){
            convertedMap.put(node.getKey().replaceAll("\\d+_",""), Arrays.toString(node.getValue()).replaceAll(REPLACE_PATTERN, ""));
        }

        return convertedMap;
    }

    private static Appointment create(Map<String, String> convertedMap, DoctorInfo doctorInfo, String status){

        Appointment appointment = new AppointmentBuilder()
                .setDoctorInfo(doctorInfo)
                .setStart_date(convertedMap.get("start_date"))
                .setEnd_date(convertedMap.get("end_date"))
                .setText(convertedMap.get("text"))
                .build();

        if (status.equals("deleted")||status.equals("updated")){
            appointment.setId(Long.parseLong(convertedMap.get("id")));
        }

        return appointment;
    }


}
