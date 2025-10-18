create table member
(
    id              bigint auto_increment primary key,
    email           varchar(50)  not null,
    password        varchar(256) not null,
    name            varchar(20)  not null,
    role            varchar(20)  not null,
    employee_number varchar(30)  null,
    status          varchar(20)  null,
    created_at      datetime     not null,
    updated_at      datetime     null
);