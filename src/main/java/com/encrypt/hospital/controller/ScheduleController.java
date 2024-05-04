package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.DutySchedule;
import com.encrypt.hospital.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @GetMapping("/doctor/{docID}")
    public List<DutySchedule> getSchedulesByDoctorAndDate(@PathVariable Integer docID, @RequestParam Date date) {
        return service.findByDoctorAndDate(docID, date);
    }

    @PostMapping
    public DutySchedule addOrUpdateSchedule(@RequestBody DutySchedule DutySchedule) {
        return service.save(DutySchedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Integer id) {
        service.delete(id);
    }
}
