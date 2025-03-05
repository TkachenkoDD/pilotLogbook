package com.example.pilotLogbook.repository;

import com.example.pilotLogbook.model.FlightRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordsRepository extends JpaRepository<FlightRecord, Integer> {

    List<FlightRecord> findAllByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE FlightRecord  " +
            "set totalTime = :result, picTime = :resultPic, dualTime = :resultDual," +
            " totalTakeoffs = :totalTakeOffs, totalLandings = :totalLandings" +
            " WHERE email = :email")
    void updateTotals(String email, String result, String resultPic, String resultDual, int totalTakeOffs, int totalLandings);
}

