show databases;

create database CricketMatch;

use CricketMatch;

show tables;

create table Matches (
MatchID INT,
TeamA_ID INT,
TeamB_ID INT,
TotalOvers DOUBLE,
tossWinner VARCHAR(255),
tossWinnerChoice VARCHAR(255),
TeamA_Score INT,
TeamA_WicketsFallen INT,
TeamB_Score INT,
TeamB_WicketsFallen INT,
Match_Winner VARCHAR(255),
primary key (MatchID),
foreign key(TeamA_ID) references Teams(TeamID),
foreign key(TeamB_ID) references Teams(TeamID)
);

create table Teams(
TeamID INT,
TeamName varchar(255),
primary key(TeamID)
);


create table Players(
PlayerID INT ,
TeamID INT,
PlayerName VARCHAR(255),
primary key (PlayerID),
foreign key (TeamID) references Teams(TeamID)
);

create table ScoreBoard(
scorecardID INT,
matchID INT,
teamID INT,
playerID INT,
runsScored INT,
ballsPlayed INT,
numberOf4s INT,
numberOf6s INT,
wicketTakenBy varchar(255),
oversBowled double,
wicketsTaken INT,
noBallsBowled INT,
wideBallsBowled INT,
runsGiven INT,
primary key(scorecardID),
foreign key(matchID) references Matches(MatchID),
foreign key(teamID) references Teams(TeamID),
foreign key(playerID) references Players(playerID)
);

select * from Matches;
select * from Teams;
delete from Teams;
select * from Players;
select * from ScoreBoard;


drop table Teams;



