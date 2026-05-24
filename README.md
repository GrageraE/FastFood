# FastFood
## Descripción

FastFood API es una aplicación backend desarrollada con Spring Boot hecha para la asignatura Ingeniería de software del grupo O del grado de Matematicas e informática del año 2026 en mayo

## Integrantes

Yu Xiang Yang Xu <br>
Daniel Ramon Gragera  <br>
Yun Chen

## Uso y compilacion
### Requisitos previos
- Java 17
- Docker (Es necesario instalar Docker para ejecutar la base de datos MongoDB en un contenedor)

### Compilación y ejecución
1. Levantar docker (a veces no es necesario, si no funciona use el comando):
```
docker compose up -d
```
2. Compilar el proyecto con Gradle:
```
./gradlew bootRun
```
3. Para acceder a las pruebas entre al link: http://localhost:8080/swagger-ui/index.html
4. Para acceder a la base de datos MongoDB entre en: http://localhost:8081/
---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Docker: Para leventar la base de datos
- MongoDB: La base de datos
- Compass: Aplicación para ver y gestionar los documentos de MongoDB
---

## Arquitectura

El proyecto sigue una arquitectura en capas:

- Controller: exposición de endpoints REST
- Service: lógica de negocio
- Repository: acceso a base de datos MongoDB
- Model: entidades y value objects
- DTO: comunicación con APIs externas y con el usuario de la API
- Exception: manejo de errores personalizados
- Security: configuración de seguridad y permisos
- Mappers: conversión entre entidades y DTOs
- Auth: Verificación de autenticación
---

## Funcionalidades

- Gestión de clientes
- Gestión de restaurantes
- CRUD de platos
- Aplicación de rebajas a platos
- Validación de permisos por restaurante, cliente y repartidor
- Geocodificación de direcciones
- Manejo de excepciones personalizadas

---

## Geocodificación

El sistema utiliza la API de Nominatim para convertir direcciones en coordenadas.

