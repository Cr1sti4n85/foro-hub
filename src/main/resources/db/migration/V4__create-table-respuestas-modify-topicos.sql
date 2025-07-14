create table respuestas(
    id bigint not null auto_increment,
    mensaje TEXT not null,
    fecha_creacion datetime not null default current_timestamp,
    topico_id bigint not null,
    usuario_id bigint not null,
    solucion tinyint not null default 0,


    primary key(id),
    constraint fk_respuestas_usuario_id foreign key(usuario_id) references usuarios(id),
    constraint fk_respuestas_topico_id foreign key(topico_id) references topicos(id)
);

ALTER TABLE topicos
MODIFY COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ABIERTO';