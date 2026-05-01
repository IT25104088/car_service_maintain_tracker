package com.cartracker.controller;

import com.cartracker.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "*")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Map<String, Object>> getReminder(
            @PathVariable String vehicleId,
            @RequestParam(required = false) Integer currentMileage) {
        return ResponseEntity.ok(reminderService.getReminder(vehicleId, currentMileage));
    }
}
