package com.example.pilotLogbook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FlightRecord {
    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    private String date;
//    private String departurePlace;
    private String departureTime;
//    private String arrivalPlace;
    private String arrivalTime;
    private String durationFlight;
    private int takeoffs;
    private int landings;
    private String totalTime;
//    private String aircraftModel;
//    private String aircraftRegistration;
    private String namePIC;
    private int totalTakeoffs;
    private int totalLandings;
    private String picTime;
    private String dualTime;
}
