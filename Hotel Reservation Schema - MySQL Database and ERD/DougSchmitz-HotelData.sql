use hotelsoftwareguild;

insert into amenity	( amenityName, price ) values
( 'jacuzzi', '25' ),
( 'refrigerator', '0' ),
( 'microwave', '0' ),
( 'oven', '0' );

insert into roomtype ( roomtypename, stdoccupancy, maxoccupancy, price, extrapersonprice ) values
( 'single', '2', '2', '149.99', null ),
( 'double', '2', '4', '174.99', '10' ),
( 'suite', '3', '8', '399.99', null );

insert into room ( roomid, isada, roomtypename ) values
( '201', '0', 'double' ),
( '202', '1', 'double' ),
( '203', '0', 'double' ),
( '204', '1', 'double' ),
( '205', '0', 'single' ),
( '206', '1', 'single' ),
( '207', '0', 'single' ),
( '208', '1', 'single' ),
( '301', '0', 'double' ),
( '302', '1', 'double' ),
( '303', '0', 'double' ),
( '304', '1', 'double' ),
( '305', '0', 'single' ),
( '306', '1', 'single' ),
( '307', '0', 'single' ),
( '308', '1', 'single' ),
( '401', '1', 'suite' ),
( '402', '1', 'suite' );

insert into guest ( guestname, address, city, stateabv, zip, phone ) values
( 'Doug Schmitz', '2020 Hallmark Drive', 'Shakopee', 'MN', '55379', '(555) 555-5555' ),
( 'Mack Simmer', '379 Old Shore Street', 'Council Bluffs', 'IA', '51501', '(291) 553-0508' ),
( 'Bettyann Seery', '750 Wintergreen Dr.', 'Wasilla', 'AK', '99654', '(478) 277-9632' ),
( 'Duane Cullison', '9662 Foxrun Lane', 'Harlingen', 'TX', '78552', '(308) 494-0198' ),
( 'Karie Yang', '9378 W. Augusta Ave.', 'West Deptford', 'NJ', '08096', '(214) 730-0298' ),
( 'Aurore Lipton', '762 Wild Rose Street', 'Saginaw', 'MI', '48601', '(377) 507-0974' ),
( 'Zachery Luechtefeld', '7 Poplar Dr.', 'Arvada', 'CO', '80003', '(814) 485-2615' ),
( 'Jeremiah Pendergrass', '70 Oakwood St.', 'Zion', 'IL', '60099', '(279) 491-0960' ),
( 'Walter Holaway', '7556 Arrowhead St.	', 'Cumberland', 'RI', '02864', '(446) 396-6785' ),
( 'Wilfred Vise', '77 West Surrey Street', 'Oswego', 'NY', '13126', '(834) 727-1001' ),
( 'Maritza Tilton', '939 Linda Rd.', 'Burke', 'VA', 22015, '(446) 351-6860' ),
( 'Joleen Tison	', '87 Queen St.', 'Drexel Hill', 'PA', '19026', '(231) 893-2755' );

insert into reservation ( startdate, enddate, guestid ) values
( '2023-02-02', '2023-04-02', '2' ),
( '2023-02-05', '2023-02-10', '3' ),
( '2023-02-22', '2023-02-24', '4' ),
( '2023-03-06', '2023-03-07', '5' ),
( '2023-03-17', '2023-03-20', '1' ),
( '2023-03-18', '2023-03-23', '6' ),
( '2023-03-29', '2023-03-31', '7' ),
( '2023-03-31', '2023-04-05', '8' ),
( '2023-04-09', '2023-04-13', '9' ),
( '2023-04-23', '2023-04-24', '10' ),
( '2023-05-30', '2023-06-02', '11' ),
( '2023-06-10', '2023-06-14', '12' ),
( '2023-06-10', '2023-06-14', '12' ),
( '2023-06-17', '2023-06-18', '6' ),
( '2023-06-28', '2023-07-02', '1' ),
( '2023-07-13', '2023-07-14', '9' ),
( '2023-07-18', '2023-07-21', '10' ),
( '2023-07-28', '2023-07-29', '3' ),
( '2023-08-30', '2023-09-01', '3' ),
( '2023-09-16', '2023-09-17', '2' ),
( '2023-09-13', '2023-09-15', '5' ),
( '2023-11-22', '2023-11-25', '4' ),
( '2023-11-22', '2023-11-25', '2' ),
( '2023-11-22', '2023-11-25', '2' ),
( '2023-12-24', '2023-12-28', '11' );

insert into roomreservation ( roomid, reservationid, numadults, numchildren, totalroomcost ) values
( '308', '1', '1', '0', '299.98' ),
( '203', '2', '2', '1', '999.95' ),
( '305', '3', '2', '0', '349.98' ),
( '201', '4', '2', '2', '199.99' ),
( '307', '5', '1', '1', '524.97' ),
( '302', '6', '3', '0', '924.95' ),
( '202', '7', '2', '2', '349.98' ),
( '304', '8', '2', '0', '874.95' ),
( '301', '9', '1', '0', '799.96' ),
( '207', '10', '1', '1', '174.99' ),
( '401', '11', '2', '4', '1199.97' ),
-- updated
( '206', '12', '2', '0', '599.96' ),
( '208', '12', '1', '0', '599.96' ),
-- updated done
( '304', '13', '3', '0', '184.99' ),
( '205', '14', '2', '0', '699.96' ),
( '204', '15', '3', '1', '184.99' ),
( '401', '16', '4', '2', '1259.97' ),
( '303', '17', '2', '1', '199.99' ),
( '305', '18', '1', '0', '349.98' ),
( '208', '19', '2', '0', '149.99' ),
( '203', '20', '2', '2', '399.98' ),
( '401', '21', '2', '2', '1199.97' ),
-- updated
( '206', '22', '2', '0', '449.97' ),
( '301', '22', '2', '2', '599.97' ),
-- update done
( '302', '23', '2', '0', '699.96' );

insert into roomamenity ( roomid, amenityname ) values
( '201', 'microwave' ),
( '201', 'jacuzzi' ),
( '202', 'refrigerator' ),
( '203', 'microwave' ),
( '203', 'jacuzzi' ),
( '204', 'refrigerator' ),
( '205', 'microwave' ),
( '205', 'refrigerator' ),
( '205', 'jacuzzi' ),
( '206', 'refrigerator' ),
( '207', 'microwave' ),
( '207', 'refrigerator' ),
( '207', 'jacuzzi' ),
( '208', 'microwave' ),
( '208', 'refrigerator' ),
( '301', 'microwave' ),
( '301', 'jacuzzi' ),
( '302', 'refrigerator' ),
( '303', 'microwave' ),
( '303', 'jacuzzi' ),
( '304', 'refrigerator' ),
( '305', 'microwave' ),
( '305', 'refrigerator' ),
( '305', 'jacuzzi' ),
( '306', 'microwave' ),
( '306', 'refrigerator' ),
( '307', 'microwave' ),
( '307', 'refrigerator' ),
( '307', 'jacuzzi' ),
( '308', 'microwave' ),
( '308', 'refrigerator' ),
( '401', 'microwave' ),
( '401', 'refrigerator' ),
( '401', 'oven' ),
( '402', 'microwave' ),
( '402', 'refrigerator' ),
( '402', 'oven' );

-- delete Jeremiah Pendergrass and only his records and reservations
delete
from roomreservation
where reservationId = 8;

-- Error Code: 1451. Cannot delete or update a parent row: a foreign key constraint fails (`hotelsoftwareguild`.`roomreservation`, CONSTRAINT `roomreservation_ibfk_2` FOREIGN KEY (`reservationId`) REFERENCES `reservation` (`reservationId`))
delete
from reservation
where guestId = 8;

-- Error Code: 1451. Cannot delete or update a parent row: a foreign key constraint fails (`hotelsoftwareguild`.`reservation`, CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`guestId`) REFERENCES `guest` (`guestId`))
delete 
from guest
where guestId = 8;