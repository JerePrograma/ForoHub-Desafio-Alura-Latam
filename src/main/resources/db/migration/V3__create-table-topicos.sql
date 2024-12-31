CREATE TABLE topicos (
                         id              BIGINT       NOT NULL AUTO_INCREMENT,
                         titulo          VARCHAR(100) NOT NULL,
                         mensaje         VARCHAR(300) NOT NULL,
                         fecha_creacion  DATETIME     NOT NULL,
                         status          TINYINT(1)   NOT NULL,
                         autor_id        BIGINT       NOT NULL,
                         curso_id        BIGINT       NOT NULL,
                         PRIMARY KEY (id),
                         CONSTRAINT fk_topicos_autor_id FOREIGN KEY (autor_id) REFERENCES usuarios (id)
);
