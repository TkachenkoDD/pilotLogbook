delete from flight_record;

insert into flight_record
(id, email, date, departure_time, arrival_time, takeoffs, landings, namepic, duration_flight, total_time, pic_time,
 dual_time, total_takeoffs, total_landings)
values (2, 'test@mail.com', '2025-07-01', '09:00',
        '10:00', 4, 4, 'SELF', '01:00',
        '03:10', '01:00', '00:00', 6, 6),
       (3, 'test@mail.com', '2025-07-02', '23:15',
        '01:25', 2, 2, 'PETROV', '02:10',
        '03:10', '02:10', '02:10', 6, 6),
       (4, 'test1@mail.com', '2025-07-03', '11:30',
        '15:55', 3, 3, 'SELF', '04:25',
        '04:25', '04:25', '00:00', 3, 3);







































