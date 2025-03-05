package com.example.pilotLogbook.service;

import com.example.pilotLogbook.model.FlightRecord;
import com.example.pilotLogbook.repository.RecordsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightRecordServiceImplTest {

    @Mock
    private RecordsRepository recordsRepository;
    @InjectMocks
    private FlightRecordServiceImpl flightRecordService;

    private FlightRecord record1;
    private FlightRecord record2;
    private FlightRecord record3;

    @Test
    void saveRecordTest() {
        flightRecordService.saveRecord(record2);
        verify(recordsRepository, times(1)).save(record2);
        assertEquals("PETROV", record2.getNamePIC());

        flightRecordService.saveRecord(record3);
        verify(recordsRepository, times(1)).save(record3);
        assertEquals("SELF", record3.getNamePIC());
    }

    @Test
    void findAllRecordsTest() {
        Mockito.when(recordsRepository.findAll()).thenReturn(List.of(record1, record2, record3));
        assertNotNull(flightRecordService.findAllRecords());
        assertEquals(3, flightRecordService.findAllRecords().size());
    }

    @Test
    void findByEmail() {
        String email = "test@email.com";
        Mockito.when(recordsRepository.findAllByEmail(email)).thenReturn(List.of(record1, record2));
        assertNotNull(flightRecordService.findByEmail(email));
        assertEquals(2, flightRecordService.findByEmail(email).size());
    }

    @Test
    void updateRecordTest() {
       flightRecordService.updateRecord(record1);
        Mockito.verify(recordsRepository, times(1)).save(record1);
    }

    @Test
    void deleteRecordTest() {
        flightRecordService.deleteRecord(record1);
        Mockito.verify(recordsRepository, times(1)).delete(record1);
    }

    @Test
    void findDurationTest() {
        String departureTime = "09:00";
        String arrivalTime = "10:00";
        assertEquals("01:00", flightRecordService.findDuration(departureTime, arrivalTime));

        String departureTime1 = "23:15";
        String arrivalTime1 = "01:25";
        assertEquals("02:10", flightRecordService.findDuration(departureTime1, arrivalTime1));

        String departureTime2 = "2315";
        String arrivalTime2 = "01:25";
        assertThrows(RuntimeException.class, () -> flightRecordService.findDuration(departureTime2, arrivalTime2));
    }

    @Test
    void findTotalsTest() {
        String email = "test@email.com";
        when(recordsRepository.findAllByEmail(email)).thenReturn(List.of(record2, record1));
        record1.setNamePIC("SELF");
        flightRecordService.findTotals(email);
        verify(recordsRepository, times(1))
                .updateTotals(email, "03:10", "01:00", "02:10", 6, 6);
    }

    @Test
    void timeCalculationsTest() {
        List<String> timeList = List.of("01:00", "03:15", "00:30");
        assertEquals("04:45", flightRecordService.timeCalculations(timeList));
    }

    @BeforeEach
    void setUp() {
        record1 = new FlightRecord();
        record1.setId(1);
        record1.setEmail("test@email.com");
        record1.setDepartureTime("09:00");
        record1.setArrivalTime("10:00");
        record1.setDurationFlight("01:00");
        record1.setTakeoffs(4);
        record1.setLandings(4);

        record2 = new FlightRecord();
        record2.setId(2);
        record2.setEmail("test@email.com");
        record2.setDepartureTime("23:15");
        record2.setArrivalTime("01:25");
        record2.setDurationFlight("02:10");
        record2.setNamePIC("Petrov");
        record2.setTakeoffs(2);
        record2.setLandings(2);

        record3 = new FlightRecord();
        record3.setId(3);
        record3.setEmail("test3@email.com");
        record3.setDepartureTime("11:30");
        record3.setArrivalTime("15:55");
        record3.setDurationFlight("04:25");
        record3.setTakeoffs(3);
        record3.setLandings(3);
    }



}