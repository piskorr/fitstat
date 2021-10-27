CREATE SCHEMA if not exists fitstat;
CREATE TABLE if not exists users
(
    id            BIGINT primary key auto_increment,
    email         varchar(40) NOT NULL,
    user_name     varchar(30) NOT NULL,
    user_password varchar(255) NOT NULL,
    first_name    varchar(40),
    last_name     varchar(40),
    dob           date,
    sex           bit,
    height        double,
    weight        double,
    is_deleted    bit NOT NULL,
    role smallint NOT NULL
);