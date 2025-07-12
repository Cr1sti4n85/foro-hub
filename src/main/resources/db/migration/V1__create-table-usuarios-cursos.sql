create table usuarios(

    id bigint not null auto_increment,
    nombre varchar(50) not null,
    email varchar(50) not null unique,
    password varchar(100) not null,

    primary key(id)

);

create table cursos(

    id bigint not null auto_increment,
    nombre varchar(50) not null,
    categoria varchar(30) not null unique,
    primary key(id)

);