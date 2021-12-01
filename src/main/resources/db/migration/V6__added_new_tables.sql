create table if not exists challenges
(
    id          bigint primary key auto_increment,
    description varchar(250),
    activity_id bigint,
    foreign key (activity_id) references activities (id)
);

create table if not exists users_challenges
(
    id           bigint primary key auto_increment,
    challenge_id bigint,
    user_id      bigint,
    date         date,
    is_completed bit,
    is_deleted   bit,
    foreign key (user_id) references users (id),
    foreign key (challenge_id) references challenges (id)
);

create table if not exists weight_history
(
    id         bigint primary key auto_increment,
    date       date,
    user_id    bigint,
    is_deleted bit,
    foreign key (user_id) references users (id)
);

create table if not exists custom_activities
(
    id                  bigint primary key auto_increment,
    name                varchar(50),
    description         varchar(250),
    calorie_consumption int,
    user_id             bigint,
    is_deleted          bit,
    foreign key (user_id) references users (id)
);

create table if not exists planned_activities
(
    id            bigint primary key auto_increment,
    activity_id   bigint,
    user_id       bigint,
    activity_date date,
    is_deleted    bit,
    foreign key (user_id) references users (id),
    foreign key (activity_id) references activities (id)
);



