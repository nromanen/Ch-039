package com.hospitalsearch.service;

import java.util.Map;

/**
 * Created by tsapy on 27.06.2016.
 */
public interface EmailService {

    void sendMassageFromUserToUser(Map<String,String> massageData);

}
