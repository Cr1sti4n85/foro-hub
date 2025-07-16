ALTER TABLE topicos DROP FOREIGN KEY fk_topicos_curso_id;

ALTER TABLE topicos
ADD CONSTRAINT fk_topicos_curso_id
FOREIGN KEY (curso_id) REFERENCES cursos(id)
ON DELETE CASCADE;