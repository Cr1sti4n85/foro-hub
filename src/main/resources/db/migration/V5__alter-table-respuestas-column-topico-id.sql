ALTER TABLE respuestas DROP FOREIGN KEY fk_respuestas_topico_id;

ALTER TABLE respuestas
ADD CONSTRAINT fk_respuestas_topico_id
FOREIGN KEY (topico_id) REFERENCES topicos(id)
ON DELETE CASCADE;
