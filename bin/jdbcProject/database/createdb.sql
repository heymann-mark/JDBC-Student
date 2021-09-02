drop database if exists studentdb; -- only for your server
create database studentdb; -- only for your own server
use studentdb;  -- only for your own server

-- using auto_increment for new ids where needed--
create table student(
student_id integer not null auto_increment,
student_name varchar(30) not null,
primary key (student_id));

create table enrolled(
student_id integer not null auto_increment,
course_id integer not null, 
primary key(student_id),
unique (course_id));

--minimal toppings and sizes: one each
insert into student values ('Robert');
insert into student values ('Karen');
insert into student values ('Priscila');

insert into enrolled values (1,680);
insert into enrolled values (2,460);
insert into enrolled values (3,750);

