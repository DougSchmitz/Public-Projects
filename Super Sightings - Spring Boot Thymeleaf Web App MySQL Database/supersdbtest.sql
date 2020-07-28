drop database if exists supersdbtest;
create database supersdbtest;
use supersdbtest;

create table `power`(
	id int primary key auto_increment,
	power varchar(50) not null
);

create table `organization`(
	id int primary key auto_increment,
    `name` varchar(50) not null,
    `description` varchar(250) not null,
    address varchar(75) not null,
    email varchar(50) not null
);

create table location(
	id int primary key auto_increment,
    `name` varchar(50) not null,
    `description` varchar(250) not null,
    address varchar(75) not null,
    latitude decimal(8,6) not null,
    longitude decimal(9,6) not null
);

create table hero(
	id int primary key auto_increment,
    `name` varchar(30) not null,
    `description` varchar(250) not null,
    powerId int not null,
    foreign key (powerId) references `power`(id)
);

create table sighting(
	id int primary key auto_increment,
    `date` date not null,
	heroId int not null,
	locationId int not null,
    foreign key(heroId) references hero(id),
	foreign key (locationId) references location(id)
);

create table hero_organization(
	heroId int not null,
    organizationId int not null,
    primary key(heroId, organizationId),
    foreign key(heroId) references hero(id),
    foreign key(organizationId) references organization(id)
);

insert into power ( id, power ) values
( 1, "super healing" ),
( 2, "beam of concussive blast" ), 
( 3, "can control the weather" ),
( 4, "super human strength" ),
( 5, "sticky hands" );

insert into `organization` ( id, `name`, `description`, address, email ) values
( 1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com" ),
( 2, "Avengers", "A collection of earth's mighiest heroes", "890 Fifth Avenue, New York City, New York 11145", "info@avengers.com" );

insert into location ( id, `name`, `description`, address, latitude, longitude ) values
( 1, "IDS Center", "Secret hangout for X-Men on days off", "80 S 8th St, Minneapolis, MN 55402", 44.976040, -93.272461 ),
( 2, "Empire State Building", "The heartbeat of New York City", "20 W 34th St, New York, NY 10001", 40.705608, -74.016724);

insert into hero ( id, `name`, `description`, powerId ) values
( 1, "Wolverine", "Super agile superhero", 1 ),
( 2, "Cyclops", "Gifted mutant with leadership qualities", 2 ),
( 3, "Storm", "Mutant Amazon Woman", 3 ),
( 4, "Beast", "Famously blue mutant", 4 ),
( 5, "Spiderman", "Funny kid with spider costume", 5 );

insert into sighting ( id, `date`, heroId, locationId ) values
( 1, "2020-05-20", 5, 2 ),
( 2, "2020-05-20", 5, 1 ),
( 3, "2020-04-19", 5, 1 ),
( 4, "2020-04-19", 1, 1 ),
( 5, "2020-04-19", 2, 1 ),
( 6, "2020-04-19", 3, 1 ),
( 7, "2020-05-05", 4, 2 ), 
( 8, "2019-05-05", 1, 2 ),
( 9, "2018-04-11", 1, 1 ),
( 10, "2017-03-13", 3, 2 ),
( 11, "2016-10-10", 3, 2 );

insert into hero_organization ( heroId, organizationId ) values
( 1, 1 ),
( 1, 2 ),
( 2, 1 ),
( 3, 1 ),
( 4, 1 ),
( 5, 2 );