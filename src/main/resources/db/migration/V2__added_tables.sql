CREATE TABLE if not exists activities
(
    id                   BIGINT primary key auto_increment,
    name                 varchar(40),
    description          varchar(255),
    calories_consumption int,
    is_deleted           bit
);

CREATE TABLE if not exists users_activities
(
    id                BIGINT primary key auto_increment,
    activity_date     datetime,
    activity_duration int,
    is_deleted        bit
);