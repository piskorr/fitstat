create table if not exists users_records
(
    id         bigint primary key auto_increment,
    record_date       date,
    user_id    bigint,
    activity_id bigint,
    is_deleted bit,
    foreign key (user_id) references users (id),
    foreign key (activity_id) references activities(id)
);