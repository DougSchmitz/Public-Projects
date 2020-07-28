drop database if exists databaseBullsAndCows;
create database databaseBullsAndCows;
use databaseBullsAndCows;

create table Game (
gameId int primary key auto_increment,
randomGameNumber char(4) not null,
gameComplete bool not null
);

create table Round (
roundId int primary key auto_increment,
guess char(4) not null,
guessTime datetime not null,
numOfMatches int not null,
numOfExactMatches int not null,
gameId int not null,
foreign key fk_Game_Round(gameID) references Game(gameID)
);
