package com.example.pilotLogbook.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/createFlightsRecBeforeTest.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/createFlightsRecAfterTest.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ControllerIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllRecordsTest() throws Exception {
        String jsonCont = "[{\"id\":2,\"email\":\"test@mail.com\",\"date\":\"2025-07-01\",\"departureTime\":\"09:00\",\"arrivalTime\":\"10:00\",\"durationFlight\":\"01:00\",\"takeoffs\":4,\"landings\":4,\"totalTime\":\"03:10\",\"namePIC\":\"SELF\",\"totalTakeoffs\":6,\"totalLandings\":6,\"picTime\":\"01:00\",\"dualTime\":\"00:00\"}," +
                        "{\"id\":3,\"email\":\"test@mail.com\",\"date\":\"2025-07-02\",\"departureTime\":\"23:15\",\"arrivalTime\":\"01:25\",\"durationFlight\":\"02:10\",\"takeoffs\":2,\"landings\":2,\"totalTime\":\"03:10\",\"namePIC\":\"PETROV\",\"totalTakeoffs\":6,\"totalLandings\":6,\"picTime\":\"02:10\",\"dualTime\":\"02:10\"}," +
                        "{\"id\":4,\"email\":\"test1@mail.com\",\"date\":\"2025-07-03\",\"departureTime\":\"11:30\",\"arrivalTime\":\"15:55\",\"durationFlight\":\"04:25\",\"takeoffs\":3,\"landings\":3,\"totalTime\":\"04:25\",\"namePIC\":\"SELF\",\"totalTakeoffs\":3,\"totalLandings\":3,\"picTime\":\"04:25\",\"dualTime\":\"00:00\"}]";

        var response = mockMvc.perform(MockMvcRequestBuilders.get("/apl/v1/logbook/all")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String result = response.getResponse().getContentAsString();

        assertEquals(200, response.getResponse().getStatus());
        assertEquals(jsonCont, result);
    }

    @Test
    void saveRecordTest() throws Exception {
        String jsonRequest = "{\"email\":\"kach@mail.com\",\"date\":\"2025-07-02\",\"departureTime\":\"09:00\",\"arrivalTime\":\"12:30\", \"takeoffs\":\"2\", \"landings\":\"2\", \"namePIC\":\"Petrov\"}";
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/apl/v1/logbook/save_record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        assertEquals(200, response.getResponse().getStatus());
        assertEquals("Record created", result);
    }

    @Test
    void findByEmailTest() throws Exception {
        String jsonCont = "[{\"id\":4,\"email\":\"test1@mail.com\",\"date\":\"2025-07-03\",\"departureTime\":\"11:30\",\"arrivalTime\":\"15:55\",\"durationFlight\":\"04:25\",\"takeoffs\":3,\"landings\":3,\"totalTime\":\"04:25\",\"namePIC\":\"SELF\",\"totalTakeoffs\":3,\"totalLandings\":3,\"picTime\":\"04:25\",\"dualTime\":\"00:00\"}]";
        var response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/apl/v1/logbook/test1@mail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        assertEquals(jsonCont, result);
    }

    @Test
    void updateRecordTest() throws Exception {
        String jsonRequest = "{\"id\":2,\"email\":\"test@mail.com\",\"date\":\"2025-07-04\",\"departureTime\":\"09:10\",\"arrivalTime\":\"10:00\",\"durationFlight\":\"01:00\",\"takeoffs\":4,\"landings\":4,\"totalTime\":\"03:10\",\"namePIC\":\"SELF\",\"totalTakeoffs\":6,\"totalLandings\":6,\"picTime\":\"01:00\",\"dualTime\":\"00:00\"}";
        String jsonResponse = "[{\"id\":4,\"email\":\"test1@mail.com\",\"date\":\"2025-07-03\",\"departureTime\":\"11:30\",\"arrivalTime\":\"15:55\",\"durationFlight\":\"04:25\",\"takeoffs\":3,\"landings\":3,\"totalTime\":\"04:25\",\"namePIC\":\"SELF\",\"totalTakeoffs\":3,\"totalLandings\":3,\"picTime\":\"04:25\",\"dualTime\":\"00:00\"}," +
                "{\"id\":3,\"email\":\"test@mail.com\",\"date\":\"2025-07-02\",\"departureTime\":\"23:15\",\"arrivalTime\":\"01:25\",\"durationFlight\":\"02:10\",\"takeoffs\":2,\"landings\":2,\"totalTime\":\"03:00\",\"namePIC\":\"PETROV\",\"totalTakeoffs\":6,\"totalLandings\":6,\"picTime\":\"00:50\",\"dualTime\":\"02:10\"}," +
                "{\"id\":2,\"email\":\"test@mail.com\",\"date\":\"2025-07-04\",\"departureTime\":\"09:10\",\"arrivalTime\":\"10:00\",\"durationFlight\":\"00:50\",\"takeoffs\":4,\"landings\":4,\"totalTime\":\"03:00\",\"namePIC\":\"SELF\",\"totalTakeoffs\":6,\"totalLandings\":6,\"picTime\":\"00:50\",\"dualTime\":\"02:10\"}]";
        var request = mockMvc.perform(MockMvcRequestBuilders
                        .put("/apl/v1/logbook//update_record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();
        var response = mockMvc.perform(MockMvcRequestBuilders.
                        get("/apl/v1/logbook/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResponse))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        assertEquals(200, request.getResponse().getStatus());
        assertEquals(jsonResponse, result);
    }

    @Test
    void deleteRecordTest() throws Exception {
        String jsonRequest = "{\"id\":2,\"email\":\"test@mail.com\",\"date\":\"2025-07-01\",\"departureTime\":\"09:00\",\"arrivalTime\":\"10:00\",\"durationFlight\":\"01:00\",\"takeoffs\":4,\"landings\":4,\"totalTime\":\"03:10\",\"namePIC\":\"SELF\",\"totalTakeoffs\":6,\"totalLandings\":6,\"picTime\":\"01:00\",\"dualTime\":\"00:00\"}";
        String jsonResponse = "[{\"id\":4,\"email\":\"test1@mail.com\",\"date\":\"2025-07-03\",\"departureTime\":\"11:30\",\"arrivalTime\":\"15:55\",\"durationFlight\":\"04:25\",\"takeoffs\":3,\"landings\":3,\"totalTime\":\"04:25\",\"namePIC\":\"SELF\",\"totalTakeoffs\":3,\"totalLandings\":3,\"picTime\":\"04:25\",\"dualTime\":\"00:00\"}," +
                "{\"id\":3,\"email\":\"test@mail.com\",\"date\":\"2025-07-02\",\"departureTime\":\"23:15\",\"arrivalTime\":\"01:25\",\"durationFlight\":\"02:10\",\"takeoffs\":2,\"landings\":2,\"totalTime\":\"02:10\",\"namePIC\":\"PETROV\",\"totalTakeoffs\":2,\"totalLandings\":2,\"picTime\":\"00:00\",\"dualTime\":\"02:10\"}]";
        var request = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/apl/v1/logbook/delete_record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();
        var response = mockMvc.perform(MockMvcRequestBuilders.
                get("/apl/v1/logbook/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResponse))
                .andReturn();
        String result = response.getResponse().getContentAsString();
        assertEquals(200, request.getResponse().getStatus());
        assertEquals(jsonResponse, result);
    }
}