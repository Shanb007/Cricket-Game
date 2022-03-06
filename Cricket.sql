show databases;

create database CricketMatch;

use CricketMatch;

show tables;

drop table Matches;

create table Matches (
MatchID INT auto_increment,
TeamA_ID INT,
TeamB_ID INT,
TotalOvers DOUBLE,
primary key (MatchID),
foreign key(TeamA_ID) references Teams(TeamID),
foreign key(TeamB_ID) references Teams(TeamID)
);

create table MatchResults(
MatchID INT,
tossWinner VARCHAR(255),
tossWinnerChoice VARCHAR(255),
Match_Winner VARCHAR(255),
primary key(MatchID),
foreign key(MatchID) references Matches(MatchID)
);

create table Teams(
TeamID INT auto_increment,
TeamName varchar(255),
primary key(TeamID)
);


create table Players(
PlayerID INT auto_increment,
TeamID INT,
PlayerName VARCHAR(255),
primary key (PlayerID),
foreign key (TeamID) references Teams(TeamID)
);

create table ScoreBoard(
scoreBoardID INT auto_increment,
matchID INT,
FirstInningTeam VARCHAR(255),
FirstInningTeam_Score INT,
FirstInningTeam_WicketsFallen INT,
SecondInningTeam VARCHAR(255),
SecondInningTeam_Score INT,
SecondInningTeam_WicketsFallen INT,
primary key(scoreBoardID),
foreign key(matchID) references Matches(MatchID)
);

create table PlayersMatchDetails(
playerID INT,
matchID INT,
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
foreign key(playerID) references Players(PlayerID),
foreign key(matchID) references Matches(MatchID)
);

create table PerBallDetails(
ballID INT auto_increment,
matchID INT,
BattingTeamID INT,
ballNumber INT,
currentOver INT,
ballOutcome VARCHAR(255),
BatsmanID INT,
BowlerID INT,
primary key(ballID),
foreign key(matchID) references Matches(MatchID),
foreign key(BattingTeamID) references Teams(TeamID),
foreign key(BatsmanID) references Players(PlayerID),
foreign key(BowlerID) references Players(PlayerID)
);



show tables;

select * from Matches;
select * from MatchResults;
select * from Teams;
delete from Teams;
select * from Players;
select * from ScoreBoard;
select * from PlayersMatchDetails;
select * from PerBallDetails;


Truncate table PlayersMatchDetails;
Truncate table MatchResults;
truncate table Matches;
truncate table Players;
drop table PlayersMatchDetails;
drop table ScoreBoard;
drop table MatchResults;
drop table Matches;
drop table PerBallDetails;




