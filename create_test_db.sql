-- Create data base `test`
drop database if exists test;
create database test character set utf8	collate utf8_general_ci;

use test;

-- Create table
drop table if exists t_parts;
create table t_parts (
  Part_Id int(11) not null auto_increment primary key,
  Part_Name nvarchar(255) not null,
  Part_Required boolean not null,
  Part_Amount int(11) not null default 0
)
engine = InnoDB
default character set = utf8;

-- Insert start values
insert into t_parts (Part_Name, Part_Required, Part_Amount) values
("материнская плата", 1, 3)
,("звуковая карта", 0, 2)
,("процессор", 1, 2)
,("подсветка корпуса", 0, 0)
,("HDD диск", 0, 1)
,("корпус", 1, 10)
,("память", 1, 10)
,("SSD диск", 1, 15)
,("видеокарта", 0, 7)
,("монитор", 1, 4)
,("клавиатура", 1, 6)
,("мышь", 1, 20)
,("коврик", 0, 20)
,("колонки", 0, 6)
,("гарнитура", 0, 4)
,("сетевой фильтр", 0, 8)
,("UPS", 0, 2)
,("привод", 0, 2)
,("кулер", 1, 8)
,("картридер", 0, 6)
,("вебкамера", 0, 10)
,("TV тюнер", 0, 1)
,("радио тюнер", 0, 0)
,("принтер", 0, 5)
,("сканер", 0, 4)
,("роутер", 0, 8);
