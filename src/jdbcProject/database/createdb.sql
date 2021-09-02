drop database if exists studentdb; -- only for your server
create database studentdb; -- only for your own server
use studentdb;  -- only for your own server

-- using auto_increment for new ids where needed--
create table student(
student_id integer not null auto_increment,
student_name varchar(30) not null,
primary key (student_id));

create table courses(
course_id integer not null,
cname varchar(30),
credits integer not null,
primary key(course_id)
);
create table enrolled(
student_id integer not null auto_increment,
course_id integer not null, 
);

--minimal toppings and sizes: one each
insert into student values (1,'Robert');
insert into student values (2,'Karen');
insert into student values (3,'Priscila');

insert into enrolled values (1,680);
insert into enrolled values (2,460);
insert into enrolled values (3,750);

insert into courses values (680,"Math",4);
insert into courses values (460,"English",4);
insert into courses values (750,"Science",4);

