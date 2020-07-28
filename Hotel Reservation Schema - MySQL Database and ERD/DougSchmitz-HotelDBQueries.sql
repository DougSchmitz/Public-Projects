use hotelsoftwareguild;
-- 1. Write a query that returns a list of reservations that end in July 2023, including the name of the guest, the room number(s), and the reservation dates.
-- query
select guest.guestname Guest_Name, room.roomid Room_Number, reservation.enddate Reservation_End, reservation.startDate Reservation_Start
from guest
inner join reservation on guest.guestId = reservation.guestId
inner join roomreservation on reservation.reservationId = roomreservation.reservationId
inner join room on roomreservation.roomId = room.roomId
where month(reservation.enddate) = 7
and year(reservation.enddate) = 2023
order by Reservation_End;

-- results
-- Name				Room 	EndDate 	StartDate
-- Doug Schmitz		205		2023-07-02	2023-06-28
-- Walter Holaway	204		2023-07-14	2023-07-13
-- Wilfred Vise		401		2023-07-21	2023-07-18
-- Bettyann Seery	303		2023-07-29	2023-07-28

-- 2. Write a query that returns a list of all reservations for rooms with a jacuzzi, displaying the guest's name, the room number, and the dates of the reservation.
-- query
select guest.guestname Guest_Name, room.roomid Room_Number, reservation.startDate Reservation_Start, reservation.enddate Reservation_End, amenity.amenityName Jacuzzi
from guest
inner join reservation on guest.guestId = reservation.guestId
inner join roomreservation on reservation.reservationId = roomreservation.reservationId
inner join room on roomreservation.roomId = room.roomId
inner join roomamenity on room.roomid = roomamenity.roomid
inner join amenity on roomamenity.amenityname = amenity.amenityname
where amenity.amenityName = 'jacuzzi';

-- results
-- Name 			Room 	StartDate 	EndDate 	Amenity(Jacuzzi)
-- Karie Yang		201		2023-03-06	2023-03-07	jacuzzi
-- Bettyann Seery	203		2023-02-05	2023-02-10	jacuzzi
-- Karie Yang		203		2023-09-13	2023-09-15	jacuzzi
-- Doug Schmitz		205		2023-06-28	2023-07-02	jacuzzi
-- Wilfred Vise		207		2023-04-23	2023-04-24	jacuzzi
-- Walter Holaway	301		2023-04-09	2023-04-13	jacuzzi
-- Mack Simmer		301		2023-11-22	2023-11-25	jacuzzi
-- Bettyann Seery	303		2023-07-28	2023-07-29	jacuzzi
-- Duane Cullison	305		2023-02-22	2023-02-24	jacuzzi
-- Bettyann Seery	305		2023-08-30	2023-09-01	jacuzzi
-- Doug Schmitz		307		2023-03-17	2023-03-20	jacuzzi

-- 3. Write a query that returns all the rooms reserved for a specific guest, including the guest's name, the room(s) reserved, the starting date of the reservation, and how many people were included in the reservation. (Choose a guest's name from the existing data.)
-- query
select guest.guestname Guest_Name, room.roomid Room_Number, reservation.startDate Reservation_Start, reservation.enddate Reservation_End, (numchildren + numadults) as Total_People
from guest
inner join reservation on guest.guestId = reservation.guestId
inner join roomreservation on reservation.reservationId = roomreservation.reservationId
inner join room on roomreservation.roomId = room.roomId
where guest.guestName = 'Doug Schmitz';

-- results
-- Name 			Room 	StartDate 	End Date 		Totalppl
-- Doug Schmitz		307		2023-03-17	2023-03-20		2
-- Doug Schmitz		205		2023-06-28	2023-07-02		2

-- come back to this 
-- 4. Write a query that returns a list of rooms, reservation ID, and per-room cost for each reservation. The results should include all rooms, whether or not there is a reservation associated with the room.
-- query
select room.roomid Room_Number, reservation.reservationid Reservation_ID, roomreservation.totalRoomCost Room_Price
from room
left join roomreservation on roomreservation.roomid = room.roomid
left join reservation on roomreservation.reservationId = reservation.reservationId
order by roomreservation.reservationId;
-- results
-- room reservation cost
-- 306		
-- 402		
-- 308	1	299.98
-- 203	2	999.95
-- 305	3	349.98
-- 201	4	199.99
-- 307	5	524.97
-- 302	6	924.95
-- 202	7	349.98
-- 301	9	799.96
-- 207	10	174.99
-- 401	11	1199.97
-- 206	12	599.96	multiple rooms on 1 reservation
-- 208	12	599.96	multiple rooms on 1 reservation
-- 304	13	184.99
-- 205	14	699.96
-- 204	15	184.99
-- 401	16	1259.97
-- 303	17	199.99
-- 305	18	349.98
-- 208	19	149.99
-- 203	20	399.98
-- 401	21	1199.97
-- 301	22	599.97 	multiple rooms on 1 reservation
-- 206	22	449.97	multiple rooms on 1 reservation
-- 302	23	699.96

-- 5. Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.
-- query
select room.roomid Room_Number, reservation.startdate Start_Date, roomtype.maxOccupancy
from roomtype
inner join room on roomtype.roomtypename = room.roomtypename
inner join roomreservation on room.roomId = roomreservation.roomId
inner join reservation on roomreservation.reservationId = reservation.reservationId
where month(startdate) = 4
and year(startdate) = 2023
and roomtype.maxoccupancy >= 3;

-- results
-- Room		StartDate		MaxOccupancy
-- 301		2023-04-09		4

-- 6. Write a query that returns a list of all guest names and the number of reservations per guest, sorted starting with the guest with the most reservations and then by the guest's last name.
-- query
select guest.guestname Guest_Name, count(reservation.reservationid) Reservation_Count
from guest
left join reservation on guest.guestid = reservation.guestid
group by Guest_Name
order by Reservation_Count desc, substr( Guest_Name, instr( Guest_Name, ' ')+1);

-- results
-- Guest 				Reservation Count
-- Mack Simmer			4
-- Bettyann Seery		3
-- Duane Cullison		2
-- Walter Holaway		2
-- Aurore Lipton		2
-- Doug Schmitz			2
-- Maritza Tilton		2
-- Joleen Tison 		2
-- Wilfred Vise			2
-- Karie Yang			2
-- Zachery Luechtefeld	1

-- 7. Write a query that displays the name, address, and phone number of a guest based on their phone number. (Choose a phone number from the existing data.)
-- query
select guest.guestname Name, guest.address Address, guest.phone Phone_Number
from guest
where guest.phone = '(291) 553-0508';

-- results
-- Looking up phone #(291) 553-0508
-- Name 		Address 				Phone Number
-- Mack Simmer	379 Old Shore Street	(291) 553-0508
