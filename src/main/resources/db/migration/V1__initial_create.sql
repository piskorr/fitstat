CREATE SCHEMA if not exists fitstat;
CREATE TABLE if not exists users
(
    id         BIGINT primary key auto_increment,
    email      varchar(40),
    pass       varchar(255),
    first_name varchar(40),
    last_name  varchar(40),
    height     double,
    weight     double,
    is_deleted bit
);