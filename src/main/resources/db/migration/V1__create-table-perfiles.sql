CREATE TABLE perfiles (
                          id     BIGINT       NOT NULL AUTO_INCREMENT,
                          nombre VARCHAR(100) NOT NULL,
                          activo TINYINT(1)   NOT NULL,
                          PRIMARY KEY (id)
);
