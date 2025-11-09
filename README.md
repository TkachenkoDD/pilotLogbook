✈️ Flight Logbook API

A simple REST application for pilots and crew members, designed for calculating and aggregating flight time.

✨ Key Features

Based on the input data (departure and arrival times), the application performs the following functions:

-User authorization based on email and encrypted password.

-Flight duration calculation: Automatically calculates the duration of each individual flight.

-Summary statistics: Tracks and provides summary data:

      -Total duration of all flights.

      -Total duration of flights as Pilot-in-Command (PIC).

      -Total duration of flights as a crew member.

-CRUD operations: Add, view, edit, and delete user's own records (summary values will be automatically recalculated).

🛠️ Tech Stack

    Language: Java

    Framework: Spring Boot, Spring Security

    Database: PostgreSQL

    Other Tools: Maven, Junit

⚙️ API Documentation

Add a new flight

POST /apl/v1/logbook/save_record

Request Body: JSON

{

      "date": "2025-07-02",
  
      "departureTime": "15:05",
  
      "arrivalTime": "16:20",
  
      "takeoffs": 1,
  
      "landings": 1,
  
      "namePIC": "Petrov" 
  
      // Allowed values: PIC's last name. 
  
      // Or "" if the user is the PIC. 
  
      // In this case, the field will be assigned the value "SELF"
  
}

Response (200 "Record created"):

Get All Records (and Summary) for a User

GET /apl/v1/logbook/test1@mail.com

Response (200 OK): JSON

[

  {
  
    "date": "2025-07-01",
    
    "departureTime": "09:00",
    
    "arrivalTime": "10:00",
    
    "durationFlight": "01:00",
    
    "takeoffs": 2,
    
    "landings": 2,
    
    "totalTime": "02:15",
    
    "namePIC": "SELF",
    
    "totalTakeoffs": 3,
    
    "totalLandings": 3,
    
    "picTime": "01:00",
    
    "dualTime": "01:15"
    
  },
  
  {
  
    "date": "2025-07-02",
    
    "departureTime": "15:05",
    
    "arrivalTime": "16:20",
    
    "durationFlight": "01:15",
    
    "takeoffs": 1,
    
    "landings": 1,
    
    "totalTime": "02:15",
    
    "namePIC": "PETROV",
    
    "totalTakeoffs": 3,
    
    "totalLandings": 3,
    
    "picTime": "01:00",
    
    "dualTime": "01:15"
    
  }
  
]
