CREATE TABLE if not exists units
(
    id   bigint primary key auto_increment,
    unit varchar(30)
);

insert into units(id, unit)
values (1, 'meter');
insert into units(id, unit)
values (2, 'rep');
insert into units(id, unit)
values (3, 'second');

create table if not exists users_records
(
    id          bigint primary key auto_increment,
    record_date date,
    value integer,
    user_id     bigint,
    activity_id bigint,
    unit_id     bigint,
    is_deleted  bit,
    foreign key (user_id) references users (id),
    foreign key (activity_id) references activities (id),
    foreign key (unit_id) references units (id)
);