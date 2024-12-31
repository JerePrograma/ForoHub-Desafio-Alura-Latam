CREATE TABLE cursos (
                        id        BIGINT       NOT NULL AUTO_INCREMENT,
                        nombre    VARCHAR(300) NOT NULL,
                        categoria VARCHAR(100) NOT NULL,
                        status    TINYINT(1)   NOT NULL,
                        PRIMARY KEY (id)
);
