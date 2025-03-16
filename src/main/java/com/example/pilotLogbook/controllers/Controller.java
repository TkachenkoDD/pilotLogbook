package com.example.pilotLogbook.controllers;

import com.example.pilotLogbook.model.FlightRecord;
import com.example.pilotLogbook.service.FlightRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apl/v1/logbook")
@AllArgsConstructor
public class Controller {

    private final FlightRecordService recordService;

    @GetMapping("/all")
    public List<FlightRecord> findAllRecords(){
        return recordService.findAllRecords();
    }

    @PostMapping("/save_record")
    public String saveRecord(@RequestBody FlightRecord record){
        recordService.saveRecord(record);
        return "Record created";
    }

    @GetMapping("/{email}")
    public List<FlightRecord> findByEmail(@PathVariable String email){
        return recordService.findByEmail(email);
    }

    @PutMapping("/update_record")
    public void updateRecord(@RequestBody FlightRecord record){
        recordService.updateRecord(record);
    }

    @DeleteMapping("/delete_record")
    public void deleteRecord(@RequestBody FlightRecord record){
        recordService.deleteRecord(record);
    }

}
