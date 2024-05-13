package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Appointment;
import com.encrypt.hospital.repository.AppointmentRepository;
import com.encrypt.hospital.util.HMACSM3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
        //完整性
        appointment.setAppoID_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getAppoID())));
        appointment.setDocID_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getDocID())));
        appointment.setPatientID_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getPatientID())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(appointment.getDate());
        appointment.setDate_MAC(HMACSM3.generateHmacSm3(formattedDate));
       //appointment.setDate_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getDate())));
        appointment.setTime_MAC(HMACSM3.generateHmacSm3(appointment.getTime()));
        appointment.setDepartment_MAC(HMACSM3.generateHmacSm3(appointment.getDepartment()));
        appointment.setState_MAC(HMACSM3.generateHmacSm3(appointment.getState()));
        appointmentRepository.save(appointment);

        System.out.println("ID  "+HMACSM3.verifyHmacSm3(String.valueOf(appointment.getAppoID()), appointment.getAppoID_MAC()));
        System.out.println("docID  "+HMACSM3.verifyHmacSm3(String.valueOf(appointment.getDocID()), appointment.getDocID_MAC()));
        System.out.println("patID  "+HMACSM3.verifyHmacSm3(String.valueOf(appointment.getPatientID()), appointment.getPatientID_MAC()));
        System.out.println("date  "+HMACSM3.verifyHmacSm3(sdf.format(appointment.getDate()), appointment.getDate_MAC()));
        System.out.println("time  "+HMACSM3.verifyHmacSm3(appointment.getTime(), appointment.getTime_MAC()));
        System.out.println("dep  "+HMACSM3.verifyHmacSm3(appointment.getDepartment(), appointment.getDepartment_MAC()));
        System.out.println("state  "+HMACSM3.verifyHmacSm3(appointment.getState(), appointment.getState_MAC()));
        return appointment;
    }
}
