--liquibase formatted sql
--changeset test_atmos:create-tables
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS profile
(
    id          varchar PRIMARY KEY,
    name        varchar,
    username    varchar not null,
    password    varchar not null,
    role        varchar,
    created_date timestamp default now(),
    visible boolean default true
);

CREATE TABLE IF NOT EXISTS city
(
    id             varchar PRIMARY KEY,
    name           varchar not null,
    latitude       numeric,
    longitude      numeric,
    weather_status varchar,
    created_date    timestamp default now(),
    visible boolean default true
);


CREATE TABLE IF NOT EXISTS profile_city
(
    id uuid primary key default uuid_generate_v1(),
    city_id     varchar not null,
    profile_id  varchar not null,
    foreign key (city_id) references city (id),
    foreign key (profile_id) references profile (id),
    created_date timestamp default now()
);

insert into city(id, name, latitude, longitude, weather_status, created_date, visible)
values ('a3842d3c-7ccf-416a-8c0c-f9a0ae61e910', 'TASHKENT', 212.3, 1232, 'HOT', now(), true),
 ('a3842d3c-7ccf-4d6a-8ad0c-f9a0ae61e0212', 'ANDIJAN', 21232.3, 34232, 'COLD', now(), true),
 ('a3842d3c-7ccfad16a-8c0c-f9a0ae61e9e12', 'SAMARQAND', 212.33, 13432, 'HOT', now(), true),
 ('a4812d3c-7ccf-416a-8c0c-f9a0ae61e910', 'NAMANGAN', 3223.3, 34223, 'SUNNY', now(), true),
 ('a3842d3c-7ccf-4dqw6a-8c0c-f9aacdse61e910', 'BUXORO', 251.3, 753, 'RAINY', now(), true),
 ('a3842d3c-7ccf-416a-1ew-f9a0ae61e910', 'JIZZAKH', 5112.3, 159, 'SNOW', now(), true),
 ('a3842d3c-7ccf-dwcs3-8c0c-f9a0ae61e910', 'KHORAZM', 118.3, 852, 'WINDY', now(), true),
 ('a3842ddwc-7ccf-416a-8c0c-f9a0ae61e910', 'KAZAKSTAN', 1851.3, 963, 'SHOWER', now(), true),
 ('a3842d3c-7ccf-416a-8c0c-wdfa0ae61e910', 'KOREA', 2125.3, 741, 'LIGHTNING', now(), true),
 ('a3842xqad3c-7ccf-416a-8c0c-f9a0ae61e910', 'LONDON', 2545.3, 1452, 'RAINBOW', now(), true),
 ('1a3842d3c-7ccf-416a-8c0c-f9a0ae61e9101', 'CANADA', 7989.3, 5849, 'HURRICANE', now(), true),
 ('1a38qwdd3c-7ccf-416a-8c0c-f9a0ae61e9102', 'SINGAPORE', 485.3, 8989, 'HOT', now(), true)
 ON CONFLICT (id) DO NOTHING;



-- init profiles

insert into profile(id, name, username, password, role, created_date, visible)
values('1a38qwdd3c-7ccf-416a-8c0c-f9a0ae61e9102','admin','admin','123','ADMIN',now(),true),
('1a38qwdd3c-7ccf-416a-8c0c-f9a0ae61e9103','user','user','123','USER',now(),true)
on conflict (id) do nothing;
