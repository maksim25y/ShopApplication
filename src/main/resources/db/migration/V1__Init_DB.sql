create table if not exists users
(
    id bigserial primary key,
    firstname varchar(100) not null ,
    lastname varchar(100) not null ,
    email varchar(100) not null unique,
    username varchar(100) not null unique ,
    password varchar(100) not null ,
    role varchar(100) not null
);