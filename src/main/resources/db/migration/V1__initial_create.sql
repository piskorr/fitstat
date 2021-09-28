CREATE SCHEMA if not exists fitstat;
CREATE TABLE if not exists users
(
    id           BIGINT primary key auto_increment,
    email        varchar(40),
    user_name     varchar(30),
    user_password varchar(255),
    first_name   varchar(40),
    last_name    varchar(40),
    height       double,
    weight       double,
    is_deleted   bit
);