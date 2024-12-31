CREATE TABLE respuestas (
                            id              BIGINT       NOT NULL AUTO_INCREMENT,
                            mensaje         VARCHAR(600) NOT NULL,
                            topico_id       BIGINT       NOT NULL,
                            fecha_creacion  DATETIME     NOT NULL,
                            autor_id        BIGINT       NOT NULL,
                            solucion        TINYINT(1)   NOT NULL,
                            status          TINYINT(1)   NOT NULL,
                            PRIMARY KEY (id),
                            CONSTRAINT fk_respuestas_topico_id FOREIGN KEY (topico_id) REFERENCES topicos (id),
                            CONSTRAINT fk_respuestas_autor_id FOREIGN KEY (autor_id) REFERENCES usuarios (id)
);
