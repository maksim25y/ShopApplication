create table if not exists users
(
    id bigserial primary key,
    firstname varchar(100),
    lastname varchar(100),
    email varchar(100),
    username varchar(100),
    password varchar(100),
    role varchar(100)
);