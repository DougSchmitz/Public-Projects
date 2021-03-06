drop database if exists blogdb;

create database blogdb;

use blogdb;


create table Users(
id int primary key auto_increment,
userName varchar(30) not null unique,
`password` varchar(100) not null,
enabled bool default 1
);

create table Roles(
id int primary key auto_increment,
`name` varchar(30) not null
);

create table UserRole(
userId int not null,
roleId int not null,
primary key(userId, roleId),
foreign key fk_userrole_users(userId)
references Users(id),
foreign key fk_userrole_users(roleId)
references Roles(id)
);

create table Pages(
id int primary key auto_increment,
title varchar(60) not null,
content text not null
);

create table Posts(
id int primary key auto_increment,
title varchar(60) not null,
author varchar(50) not null,
content text not null,
approved bool default 0,
`start` date not null,
`end` date not null
);

create table Tags(
id int primary key auto_increment,
tag varchar(60) not null
);

create table Post_Tags(
postId int not null,
tagId int not null,
foreign key fk_pt_posts(postId)
references Posts(id),
foreign key fk_pt_tags(tagId)
references Tags(id)
);

insert into users(userName, `password`, enabled)
values
('Admin', 'password', 1),
('Author', 'password', 1);


insert into Roles(`name`)
values
('ROLE_ADMIN'),
('ROLE_AUTHOR');

insert into UserRole(userId, roleId)
values
(1, 1),
(2, 2);

UPDATE users SET `password` = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE id = 1;
UPDATE users SET `password` = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE id = 2;