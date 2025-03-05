package com.example.pilotLogbook.service;

import com.example.pilotLogbook.model.FlightRecord;

import java.util.List;

public interface FlightRecordService {

    List<FlightRecord> findAllRecords();

    void saveRecord(FlightRecord record);

    List<FlightRecord> findByEmail(String email);

    void updateRecord(FlightRecord record);

    void deleteRecord(FlightRecord record);
}
