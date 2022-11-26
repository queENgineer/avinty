
--drop table if exists  public.employees;

create table if not exists public.employees
(
    id         int auto_increment primary key,
    email      varchar(255) not null,
    password   varchar(66)  not null,
    full_name  varchar(255) not null,
    dep_id     int,
    created_at timestamp,
    created_by int,
    updated_at timestamp,
    updated_by int

);


--drop table if exists  public.departments;

create table if not exists public.departments
(
    id         int auto_increment primary key,
    name       varchar(100) not null,
    manager_id int,-- references employees(id),
    created_at timestamp,
    created_by int,
    updated_at timestamp,
    updated_by int

);

--alter table employees add constraint fk_dep_id
  --  foreign key (dep_id)
    --    references departments (id);