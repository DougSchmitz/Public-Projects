drop database if exists HotelSoftwareGuild;

create database HotelSoftwareGuild;

use HotelSoftwareGuild;

create table Amenity (
	amenityName varchar(15) primary key,
    price decimal(2,0) not null
);

create table RoomType (
	roomTypeName varchar(10) primary key not null,
    stdOccupancy int not null,
    maxOccupancy int not null,
	price decimal(5,2) not null,
    extraPersonPrice int
);

create table Room (
	roomId int primary key,
    isAda bool,
    roomTypeName varchar(10) not null,
	foreign key fk_Room_RoomType(roomTypeName) references RoomType(roomTypeName)
);

create table Guest (
	guestId int primary key auto_increment,
    guestName varchar(20) not null,
    address varchar(50) not null,
    city varchar(30) not null,
    stateAbv char(2) not null,
    zip char(5) not null,
    phone char(14) not null
);

create table Reservation (
	reservationId int primary key auto_increment,
    startDate date not null,
    endDate date not null,
    guestId int not null,
    foreign key fk_Reservation_Guest(guestId) references Guest(guestId)
);

create table RoomReservation (
    numAdults int not null,
    numChildren int, 
	totalRoomCost decimal(6,2) not null,
	roomId int not null,
	reservationId int not null,
	primary key pk_RoomReservation(roomId, reservationID),
	foreign key fk_RoomReservation_Room(roomId) references Room(roomId),
	foreign key fk_RoomReservation_Reservation(reservationId) references Reservation(reservationId)
);

create table RoomAmenity (
	roomId int not null,
    amenityName varchar(15) not null,
	primary key pk_RoomAmenity(roomId, amenityName),
    foreign key fk_RoomAmenity_Room(roomId) references Room(roomId),
    foreign key fk_RoomAmenity_Amenity(amenityName) references Amenity(amenityName)
);