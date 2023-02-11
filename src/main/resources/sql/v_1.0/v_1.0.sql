-- script inicial de al aplicacion
 \c template1
  drop database if exists login;
  create database login;
\c login


create table usuario(
    id int constraint usuario_pk primary key,
    apellido varchar(50),
    nombre varchar(50),
    email varchar(70) NOT NULL
    password varchar(255),
    role smallint
);

create sequence usuario_seq start with 1 increment by 1;