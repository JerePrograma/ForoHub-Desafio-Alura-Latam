CREATE TABLE usuarios (
                          id                BIGINT       NOT NULL AUTO_INCREMENT,
                          nombre            VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password        VARCHAR(300) NOT NULL,
                          perfil_id         BIGINT       NOT NULL,
                          status            TINYINT(1)   NOT NULL,
                          PRIMARY KEY (id),
                          CONSTRAINT fk_usuarios_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfiles (id)
);
