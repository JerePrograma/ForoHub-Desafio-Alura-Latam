# ForoHub

**ForoHub** es una plataforma de discusión y colaboración donde los usuarios pueden compartir ideas, resolver problemas y aprender en comunidad. Diseñada con un enfoque en la simplicidad y la escalabilidad, ForoHub utiliza tecnologías modernas como Spring Boot y MySQL para ofrecer un entorno eficiente y seguro.

---

## Características

- **Gestión de usuarios**: Registro, inicio de sesión y asignación de roles.
- **Gestión de tópicos**: Creación, lectura, actualización y eliminación de temas de discusión.
- **Seguridad**: Integración con Spring Security para autenticación y autorización.
- **Soporte para múltiples perfiles**: Asociar usuarios con diferentes roles dentro del sistema.
- **Integración con Flyway**: Migraciones de base de datos para mantener un esquema consistente.
- **Documentación de API**: Generada automáticamente con Springdoc OpenAPI.

---

## Tecnologías Utilizadas

- **Backend**: Spring Boot (v3.4.1)
- **Base de datos**: MySQL
- **ORM**: Spring Data JPA
- **Seguridad**: Spring Security
- **Gestión de migraciones**: Flyway
- **Documentación de API**: Springdoc OpenAPI
- **Autenticación JWT**: Protección de endpoints mediante tokens JWT

---

## Requisitos Previos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.6+

---

## Instalación

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/tu_usuario/foro-hub.git
   cd foro-hub
   ```

2. Configurar la base de datos:

   - Crear una base de datos en MySQL llamada `foro_hub`.
   - Configurar las credenciales en el archivo `application.properties` o `application.yml`:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     spring.jpa.hibernate.ddl-auto=validate
     spring.flyway.enabled=true
     ```

3. Ejecutar las migraciones:

   ```bash
   mvn flyway:migrate
   ```

4. Construir y ejecutar la aplicación:

   ```bash
   mvn spring-boot:run
   ```

5. Acceder a la aplicación:

   - Navegar a `http://localhost:8080`
   - Documentación de la API: `http://localhost:8080/swagger-ui.html`

---

## Endpoints Principales

### Usuarios

- `POST /api/usuarios` - Crear un usuario
- `GET /api/usuarios/{id}` - Obtener un usuario por ID
- `GET /api/usuarios` - Listar todos los usuarios
- `PUT /api/usuarios/{id}` - Actualizar un usuario
- `DELETE /api/usuarios/{id}` - Eliminar un usuario

### Tópicos

- `POST /api/topicos` - Crear un tópico
- `GET /api/topicos/{id}` - Obtener un tópico por ID
- `GET /api/topicos` - Listar todos los tópicos
- `PUT /api/topicos/{id}` - Actualizar un tópico
- `DELETE /api/topicos/{id}` - Eliminar un tópico

---

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas agregar nuevas funcionalidades, solucionar errores o mejorar la documentación, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad o corrección:

   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```

3. Realiza tus cambios y commitea:

   ```bash
   git commit -m "Descripción de los cambios realizados"
   ```

4. Sube tus cambios:

   ```bash
   git push origin feature/nueva-funcionalidad
   ```

5. Crea un Pull Request desde tu repositorio fork hacia el repositorio original.

---

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
