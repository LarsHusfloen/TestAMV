create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS user
(
    User_id        integer UNIQUE auto_increment,
    User_fullName varchar(255),
    User_phoneNumber     varchar(255),
    CONSTRAINT U_User_ID_PK PRIMARY KEY (User_id)
);


#inserter en record av en bruker inn i databasen otra.
insert into user (User_fullName,
                       User_phoneNumber)
values (
        'Lars Husfloen', '41374981');