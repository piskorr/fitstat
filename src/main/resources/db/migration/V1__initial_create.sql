create schema if not exists fitstat;
create table if not exists users
(
    id            bigint primary key auto_increment,
    email         varchar(40)  not null,
    user_name     varchar(30)  not null,
    first_name    varchar(40),
    last_name     varchar(40),
    dob           date,
    sex           bit,
    height        double,
    weight        double,
    is_deleted    bit          not null,
    role          smallint     not null
);