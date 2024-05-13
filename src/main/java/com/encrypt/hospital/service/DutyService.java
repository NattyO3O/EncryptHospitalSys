package com.encrypt.hospital.service;

import com.encrypt.hospital.repository.AppointmentCountDto;
import com.encrypt.hospital.repository.DutyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyService {
    @Autowired
    private DutyRepository dutyRepository;

    public List<AppointmentCountDto> getAppointmentCountsByDoctor(int docID) {
        return dutyRepository.findAppointmentCountsByDoctor(docID);
    }
}
