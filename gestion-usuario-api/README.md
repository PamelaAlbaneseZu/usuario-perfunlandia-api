# API de Gestión de Usuarios - Perfunlandia

## ¿Qué es esto?

Esta es una API REST para manejar usuarios, clientes y vendedores del sistema Perfunlandia. Tiene documentación automática con Swagger y enlaces HATEOAS para navegar fácilmente entre recursos.

## 🚀 Cómo usar

### 1. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

### 2. Ver la documentación
Abre tu navegador y ve a: **http://localhost:8082/swagger-ui.html**

Aquí podrás ver todos los endpoints disponibles y probarlos directamente.

## 📋 Endpoints disponibles

### Usuarios (todos los tipos)
- `GET /api/usuarios` - Ver todos los usuarios
- `GET /api/usuarios/{id}` - Ver un usuario específico
- `POST /api/usuarios` - Crear un nuevo usuario
- `PUT /api/usuarios/{id}` - Actualizar un usuario
- `DELETE /api/usuarios/{id}` - Eliminar un usuario

### Búsquedas específicas
- `GET /api/usuarios/rol/{rol}` - Buscar usuarios por rol (CLIENTE, VENDEDOR, ADMIN)
- `GET /api/usuarios/email/{email}` - Buscar usuario por email
- `GET /api/usuarios/estado/{estado}` - Buscar usuarios por estado (ACTIVO, INACTIVO)

## 🔗 Enlaces HATEOAS

Cada respuesta incluye enlaces que apuntan al API Gateway (puerto 8888):

```json
{
  "idUsuario": 1,
  "nombreCompleto": "Juan Pérez",
  "email": "juan@example.com",
  "rol": "CLIENTE",
  "estado": "ACTIVO",
  "_links": {
    "self": "http://localhost:8888/api/usuarios/1",
    "actualizar": "http://localhost:8888/api/usuarios/1",
    "eliminar": "http://localhost:8888/api/usuarios/1",
    "usuarios": "http://localhost:8888/api/usuarios"
  }
}
```

## 🌐 URLs importantes

- **API local**: http://localhost:8082/api/usuarios
- **API Gateway**: http://localhost:8888/api/usuarios
- **Documentación**: http://localhost:8082/swagger-ui.html

## 🛠️ Tecnologías usadas

- **Spring Boot 3.4.0** - Framework principal
- **HATEOAS** - Para enlaces entre recursos (todos apuntan al Gateway)
- **Swagger/OpenAPI** - Para documentación automática
- **MySQL/MariaDB** - Base de datos
- **JPA/Hibernate** - Para manejo de datos

## 📝 Notas

- La API corre en el puerto 8082
- **Todos los enlaces HATEOAS apuntan al API Gateway en puerto 8888**
- Toda la documentación se genera automáticamente
- Un solo controlador maneja todos los tipos de usuarios (clientes, vendedores, admin)
- No necesitas configurar nada más, solo ejecutar la aplicación

## 🎯 Ejemplo rápido

1. Ejecuta la aplicación
2. Ve a http://localhost:8082/swagger-ui.html
3. Prueba el endpoint "Listar usuarios"
4. Verás la respuesta con enlaces HATEOAS que van al Gateway
5. Usa los enlaces para navegar entre recursos

¡Eso es todo! La API está lista para usar. 🎉 