package com.hospitalsearch.service.impl;

import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.WorkInterval;
import com.hospitalsearch.util.WorkIntervalBuilder;
import com.hospitalsearch.util.WorkIntervalDto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by igortsapyak on 11.05.16.
 */
public class WorkIntervalDtoService {

    private static final String REPLACE_PATTERN = "[\\[,\\]]";

    public static WorkIntervalDto createWorkintervalDto(Map<String, String[]> dataMap, DoctorInfo doctorInfo){

        Map<String, String> convertedMap = mapConvert(dataMap);

        WorkIntervalDto workIntervalDto = new WorkIntervalDto();

        workIntervalDto.setDoctorInfo(doctorInfo);

        workIntervalDto.setStatus(convertedMap.get("!nativeeditor_status"));

        workIntervalDto.setWorkInterval(create(convertedMap ,doctorInfo, workIntervalDto.getStatus()));

        return workIntervalDto;
    }

    private static Map<String, String> mapConvert(Map<String, String[]> dataMap){
        Map<String, String> convertedMap = new HashMap<>();
        for (Map.Entry<String, String[]> node: dataMap.entrySet()){
            convertedMap.put(node.getKey().replaceAll("\\d+_",""), Arrays.toString(node.getValue()).replaceAll(REPLACE_PATTERN, ""));
        }

        return convertedMap;
    }

    private static WorkInterval create(Map<String, String> convertedMap, DoctorInfo doctorInfo, String status){

        WorkInterval workInterval = new WorkIntervalBuilder()
                .setDoctorInfo(doctorInfo)
                .setStart_date(convertedMap.get("start_date"))
                .setEnd_date(convertedMap.get("end_date"))
                .setText(convertedMap.get("text"))
                .build();

        if (status.equals("deleted")||status.equals("updated")){
            workInterval.setId(Long.parseLong(convertedMap.get("id")));
        }

        return workInterval;
    }


}
