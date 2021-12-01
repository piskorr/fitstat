create table if not exists activities
(
    id                   bigint primary key auto_increment,
    name                 varchar(40),
    description          varchar(255),
    calories_consumption double,
    is_deleted           bit
);

create table if not exists users_activities
(
    id                bigint primary key auto_increment,
    activity_date     datetime,
    activity_duration int,
    user_id           bigint,
    activity_id       bigint,
    is_deleted        bit,
    foreign key (user_id) references users (id),
    foreign key (activity_id) references activities (id)
);