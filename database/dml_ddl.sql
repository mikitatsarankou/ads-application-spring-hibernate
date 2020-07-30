create database adsdb;
use adsdb;

create table adsdb.roles (
	id int not null auto_increment primary key
);

create table adsdb.users (
	id int not null auto_increment primary key,
    username varchar(20) unique not null,
    `password` varchar(256) not null
);

create table users_roles (
	user_id int not null,
    role_id int not null,
    
    foreign key (user_id) references adsdb.users(id),
    foreign key (role_id) references adsdb.roles(id)
);

create table adsdb.`profiles` ( 
	id int not null auto_increment primary key,
    user_id int not null,
    create_date timestamp not null,
    `name` varchar(20) not null,
    surname varchar(20) not null,
    email varchar(30) not null,
    phone_number varchar(15) not null,
    
	foreign key (user_id) references adsdb.users(id) 
);

create table adsdb.profiles_ratings (
	id int not null auto_increment primary key,
	profile_id int not null,
    number_of_sales int,
    overall_rating int,
    
    foreign key (profile_id) references adsdb.`profiles`(id)
);


create table adsdb.categories (
	id int not null auto_increment primary key,
    `name`  varchar(25) not null
);

create table adsdb.regions (
	id int not null auto_increment primary key,
    `name` varchar(25) not null
);

create table adsdb.towns (
	id int not null auto_increment primary key,
    region_id int not null,
    `name` varchar(25) not null,
    
    foreign key (region_id) references adsdb.regions(id)
);

create table adsdb.locations (
	id int not null auto_increment primary key,
    region_id int not null,
    town_id int not null,
    
    foreign key (region_id) references adsdb.regions(id),
    foreign key (town_id) references adsdb.towns(id)
);

create table adsdb.announcements (
	id int not null auto_increment primary key,
    announcement_text varchar(120) not null,
    price int not null,
    create_date timestamp not null,
    profile_id int not null,
    category_id int not null,
    location_id int not null,
    priority_pay_date timestamp,
    paid_priority_expire_date timestamp,
    priority int not null,
    
    foreign key (location_id) references adsdb.locations(id),
    foreign key (category_id) references adsdb.categories(id),
    foreign key (profile_id) references adsdb.`profiles`(id)
);

create table adsdb.comments (
	id int not null auto_increment primary key,
    profile_id int not null,
    announcement_id int not null,
    create_date timestamp not null,
    comment_text varchar(100) not null,
    
    foreign key (profile_id) references adsdb.`profiles`(id),
    foreign key (announcement_id) references adsdb.announcements(id)
);

alter table adsdb.announcements add `announcement_status` ENUM ('OPEN', 'SOLD');
alter table adsdb.roles add `role_name` ENUM ('ADMIN', 'USER');

insert into adsdb.roles (role_name) value 
('ADMIN'),
('USER');

insert into adsdb.regions (`name`) value
('Brest region'),
('Vitebsk region'),
('Gomel region'),
('Grodno region'),
('Minsk region'),
('Mogilev region');

insert into adsdb.towns (region_id, `name`) value
(1, 'Brest'),
(2, 'Vitebsk'),
(3, 'Gomel'),
(4, 'Grodno'),
(5, 'Minsk'),
(6, 'Mogilev');

insert into adsdb.locations (region_id, town_id) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6);

insert into adsdb.categories (`name`) value 
('appliances'),
('cars'),
('electronics');

insert into adsdb.users (username, `password`) values 
('admin', '$2a$10$DaDM/C1BZ5FSVpfiKuJKjeBhCjwzB41bMU6AVDy5fmpZ9hCmJbA9e'), -- 1111
('test', '$2a$10$x6g6vhC4l6NmFqDz0qerXeVTzQdWOrQQYh0qhOXHZGSPoG0PEBQWa'), -- 12345678
('mikita_tsarankou', '$2a$10$NsGF8j7Y/7/Pyxqn7dTbSe/Uo1fhojHuuyU85JbGthBniopKq1/DC'); -- 9136406

insert into adsdb.profiles (user_id, create_date, `name`, surname, email, phone_number) values 
(1, '2020-06-20 12:00:00', 'admin', 'admin', 'admin@support.by', '+375290000000'),
(2, '2020-06-20 12:15:00', 'test', 'test', 'test@support.by', '+375291234567'),
(3, '2020-07-04 13:48:40', 'mikita', 'tsarankou', 'tsarankou@gmail.com', '+375298808000');


insert into adsdb.profiles_ratings (profile_id, number_of_sales, overall_rating) values 
(1, 0, 2),
(2, 0, 11),
(3, 0, 0);

insert into adsdb.users_roles (user_id, role_id) values 
(1, 1),
(1, 2),
(2, 1),
(3, 2);
    
insert into adsdb.announcements (announcement_text, price, create_date, profile_id, category_id, location_id, priority_pay_date, paid_priority_expire_date, priority, announcement_status) values
('peguet 407 2010 г. состояние отличное', 8900, '2020-06-20 12:56:20', 2, 2, 4, null, '2020-06-21 12:57:20', 5, 'OPEN'),
('iphone 5se, б.у 2 года', 150, '2020-06-20 13:56:20', 2, 3, 4, null, null, 5, 'OPEN'),
('iphone SE новый, россия', 800, '2020-06-20 14:56:20', 2, 3, 5, null, null, 5, 'OPEN'),
('Хендаи Солярис 2014, 66 000 км, автомат, 1.6 л, бензин, седан', 9316, '2020-06-26 13:24:35', 2, 2, 4, '2020-06-30 16:56:22', '2020-07-07 16:56:22', 9, 'OPEN'),
('Фольксваген поло. 1997, 260 000 км, механика, 1.4 л, бензин', 1677, '2020-06-26 13:26:52', 2, 2, 4, '2020-07-01 13:10:25', '2020-07-08 13:10:25', 9, 'OPEN'),
('Land Rover Range Rover Sport I (рестайлинг) HSE 2009, 176 100 км, автомат, 3.0 л, дизель', 19300, '2020-06-26 13:25:29', 2, 2, 4, null, '2020-07-01 16:56:22', 5, 'SOLD');

insert into adsdb.comments (profile_id, announcement_id, create_date, comment_text) values
(1, 1, '2020-06-20 13:25:21', 'торг возможен?'),
(2, 1, '2020-06-20 13:27:10', 'да, возможен');


