# 📚 Biblioteca Digital API

API REST para la gestión completa de una biblioteca digital, desarrollada con Spring Boot y PostgreSQL. Permite administrar libros, autores, géneros, usuarios y préstamos a través de endpoints bien definidos y documentados.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas limpia y escalable:

```
src/
├── controller/       # Endpoints REST (AutorController, LibroController, ...)
├── service/          # Lógica de negocio
├── repository/       # Acceso a datos (Spring Data JPA)
├── model/            # Entidades JPA (Autor, Libro, Genero, Usuario, Prestamo)
├── dto/              # Objetos de transferencia de datos (Request/Response)
└── exception/        # Manejo centralizado de errores
```

---

## 📦 Entidades principales

| Entidad    | Descripción                                      |
|------------|--------------------------------------------------|
| `Autor`    | Información de autores de los libros             |
| `Genero`   | Categorías literarias (novela, ciencia ficción…) |
| `Libro`    | Catálogo de libros con autor y género asociados  |
| `Usuario`  | Usuarios registrados en la plataforma            |
| `Prestamo` | Registro de préstamos de libros a usuarios       |

---

## 🔌 Endpoints de la API

Todos los endpoints están bajo el prefijo `/api/v1/`

### Autores `/api/v1/autores`
| Método | Endpoint         | Descripción               |
|--------|------------------|---------------------------|
| GET    | `/`              | Listar todos los autores  |
| GET    | `/{id}`          | Obtener autor por ID      |
| POST   | `/`              | Crear nuevo autor         |
| PUT    | `/{id}`          | Actualizar autor          |
| DELETE | `/{id}`          | Eliminar autor            |

### Géneros `/api/v1/generos`
| Método | Endpoint         | Descripción               |
|--------|------------------|---------------------------|
| GET    | `/`              | Listar todos los géneros  |
| GET    | `/{id}`          | Obtener género por ID     |
| POST   | `/`              | Crear nuevo género        |
| PUT    | `/{id}`          | Actualizar género         |
| DELETE | `/{id}`          | Eliminar género           |

### Libros `/api/v1/libros`
| Método | Endpoint         | Descripción               |
|--------|------------------|---------------------------|
| GET    | `/`              | Listar todos los libros   |
| GET    | `/{id}`          | Obtener libro por ID      |
| POST   | `/`              | Registrar nuevo libro     |
| PUT    | `/{id}`          | Actualizar libro          |
| DELETE | `/{id}`          | Eliminar libro            |

### Usuarios `/api/v1/usuarios`
| Método | Endpoint         | Descripción               |
|--------|------------------|---------------------------|
| GET    | `/`              | Listar todos los usuarios |
| GET    | `/{id}`          | Obtener usuario por ID    |
| POST   | `/`              | Registrar nuevo usuario   |
| PUT    | `/{id}`          | Actualizar usuario        |
| DELETE | `/{id}`          | Eliminar usuario          |

### Préstamos `/api/v1/prestamos`
| Método | Endpoint         | Descripción                  |
|--------|------------------|------------------------------|
| GET    | `/`              | Listar todos los préstamos   |
| GET    | `/{id}`          | Obtener préstamo por ID      |
| POST   | `/`              | Registrar nuevo préstamo     |
| PUT    | `/{id}`          | Actualizar préstamo          |
| DELETE | `/{id}`          | Eliminar préstamo            |

---

## ⚙️ Configuración y ejecución local

### Prerrequisitos

- Java 17+
- PostgreSQL 14+
- Maven 3.8+

### Pasos

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/biblioteca-digital.git
   cd biblioteca-digital
   ```

2. **Crea la base de datos en PostgreSQL**
   ```sql
   CREATE DATABASE "BibliotecaDigitalBD";
   ```

3. **Configura las credenciales** en `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/BibliotecaDigitalBD
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```

4. **Ejecuta la aplicación**
   ```bash
   mvn spring-boot:run
   ```

5. **La API estará disponible en** `http://localhost:8080/api/v1/`

> Las tablas se generan automáticamente gracias a `spring.jpa.hibernate.ddl-auto=update`

---

## 📬 Ejemplo de uso

**Crear un nuevo autor:**
```http
POST /api/v1/autores
Content-Type: application/json

{
  "nombre": "Gabriel García Márquez",
  "nacionalidad": "Colombiana"
}
```

**Registrar un préstamo:**
```http
POST /api/v1/prestamos
Content-Type: application/json

{
  "usuarioId": 1,
  "libroId": 3,
  "fechaDevolucion": "2025-06-01"
}
```

---

## 🤝 Contacto

Desarrollado por Carlos Fernando Paredes  
📧 Ferchoriascos445gmail.com  
