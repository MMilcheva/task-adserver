create table users
(
    user_id    int auto_increment
        primary key,
    first_name varchar(50) null,
    last_name  varchar(50) not null,
    password   varchar(30) not null,
    username   varchar(20) not null
);

create table tasks
(
    task_id     int auto_increment
        primary key,
    description varchar(1000) not null,
    user_id     int           not null,
    status      varchar(15)   not null,
    due_date    date          not null,
    title       varchar(100)  not null,
    constraint tasks_users_fk
        foreign key (user_id) references users (user_id)
);


