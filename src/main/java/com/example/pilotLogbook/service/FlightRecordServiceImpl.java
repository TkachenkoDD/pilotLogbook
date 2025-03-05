package com.example.pilotLogbook.service;

import com.example.pilotLogbook.model.FlightRecord;
import com.example.pilotLogbook.repository.RecordsRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class FlightRecordServiceImpl implements FlightRecordService {

    private final RecordsRepository recordsRepository;

    @Override
    public void saveRecord(FlightRecord record) {
        record.setDurationFlight(findDuration(record.getDepartureTime(), record.getArrivalTime()));
        if (record.getNamePIC() == null)
            record.setNamePIC("SELF");
        else record.setNamePIC(record.getNamePIC().toUpperCase());
        recordsRepository.save(record);
        findTotals(record.getEmail());
    }

    @Override
    public List<FlightRecord> findAllRecords() {
        return recordsRepository.findAll();
    }

    @Override
    public List<FlightRecord> findByEmail(String email) {
        return recordsRepository.findAllByEmail(email);
    }

    @Override
    public void updateRecord(FlightRecord record) {
        record.setDurationFlight(findDuration(record.getDepartureTime(), record.getArrivalTime()));
        recordsRepository.save(record);
        findTotals(record.getEmail());
    }

    @Override
    public void deleteRecord(FlightRecord record) {
        recordsRepository.delete(record);
        findTotals(record.getEmail());
    }

    public String findDuration(String departureTime, String arrivalTime) {
        DateFormat tf = new SimpleDateFormat("HH:mm");
        String result;

        try {
            Date start = tf.parse(departureTime);
            Date end = tf.parse(arrivalTime);
            long durationInMls = end.getTime() - start.getTime();

            if (durationInMls < 0) {
                DateFormat tf2 = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
                Date end2 = tf2.parse("1970:01:02:" + arrivalTime);
                durationInMls = end2.getTime() - start.getTime();
            }
            long durationInMin = durationInMls / 60000;
            long hours = durationInMin / 60;
            long minutes = durationInMin % 60;
            result = (String.format("%02d:%02d", hours, minutes));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void findTotals(String email) {
        List<FlightRecord> recordsList = recordsRepository.findAllByEmail(email);
        List<String> timeList = new ArrayList<>();
        List<String> timePicList = new ArrayList<>();
        List<String> timeDualList = new ArrayList<>();
        int totalTakeOffs = 0;
        int totalLandings = 0;

        for (FlightRecord records : recordsList) {
            timeList.add(records.getDurationFlight());
            if (records.getNamePIC().equals("SELF"))
                timePicList.add(records.getDurationFlight());
            else timeDualList.add(records.getDurationFlight());
            totalTakeOffs += records.getTakeoffs();
            totalLandings += records.getLandings();
        }

        String result = timeCalculations(timeList);
        String resultPic = timeCalculations(timePicList);
        String resultDual = timeCalculations(timeDualList);

        recordsRepository.updateTotals( email, result, resultPic, resultDual,totalTakeOffs, totalLandings);
    }

    public String timeCalculations(List<String> timeList){
        int totalMinutes = 0;

        for (String time : timeList) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            totalMinutes += hours * 60 + minutes;
        }
        int totalHours = totalMinutes / 60;
        totalMinutes = totalMinutes % 60;
        return String.format("%02d:%02d", totalHours, totalMinutes);
    }
}
