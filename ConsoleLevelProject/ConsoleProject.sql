create database Entertainment;
use Entertainment;

create table WebSeries(
ShowID int PRIMARY KEY,
ShowName Varchar(50),
ShowGenre Varchar(50),
ShowPlatform Varchar(50),
IMDBRating DECIMAL(2,1)


);

insert into WebSeries values(1, 'Breaking Bad', 'Action / Thriller', 'Netflix', 9.5);
insert into WebSeries values(2, 'Game of Thrones', 'Fantasy / Drama', 'HBO', 9.2);
insert into WebSeries values(3, 'Stranger Things', 'Sci-Fi / Horror', 'Netflix', 8.2);
insert into WebSeries values(4, 'Money Heist', 'Heist / Action', 'Netflix', 8.2);
insert into WebSeries values(5, 'Narcos', 'Crime / Biography', 'Netflix', 8.8);
insert into WebSeries values(6, 'Dark', 'Sci-Fi / Mystery', 'Netflix', 8.8);
insert into WebSeries values(7, 'The Boys', 'Superhero / Dark Comedy', 'Prime Video', 8.7);

drop table WebSeries;

select * from WebSeries;

create table AdminTable(

AdminID int primary key,
AdminUserName Varchar(40),
AdminPassword Varchar(40)

);

insert into AdminTable values(100, 'admin', 'admin@123');
insert into AdminTable values(200, 'arjun', 'arjun@007');

select * from AdminTable;

select * from WebSeries where ShowID = 5 and showName = 'Narcos';

select * from student;

