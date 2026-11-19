# Sistema de Reservas Multiempresa (SaaS) con Multitenancy y Tiempo Real

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-RBAC%20%2B%20JWT-6DB33F?logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Base%20de%20datos-4479A1?logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-0.11.5-black?logo=jsonwebtokens&logoColor=white)
![WebSockets](https://img.shields.io/badge/WebSockets-Tiempo%20real-365FA0)
![Swagger](https://img.shields.io/badge/Swagger%20OpenAPI-2.8.9-85EA2D)

Backend para la gestión de reservas orientado a múltiples empresas (multitenancy), con autenticación basada en JWT, control de acceso por roles (RBAC), subdominios personalizados, configuración visual dinámica y sincronización en tiempo real mediante WebSockets.

---

# Características Principales

## Multitenancy por Subdominio

Cada empresa opera de forma aislada utilizando un subdominio único.

**Ejemplo:**

```txt
https://restosoft.tudominio.com
https://cafedelmar.tudominio.com
https://hotelparaiso.tudominio.com
```

El backend identifica automáticamente la empresa a partir del subdominio y carga toda su configuración personalizada.

---

## Roles y Permisos (RBAC)

### Administrador

Acceso completo a:

- Configuración visual
- Gestión de subdominios
- Disponibilidad
- Personal
- Reportes
- Reservas

### Empleado

Acceso limitado a:

- Ver reservas asignadas
- Cambiar estados de reservas
- Operaciones diarias

---

## Personalización por Empresa

Cada empresa puede configurar:

- Logo
- Colores corporativos
- Estilos visuales
- Horarios de atención
- Días festivos
- Aforo máximo por hora

---

## Reservas en Tiempo Real

Las nuevas reservas son notificadas instantáneamente a los paneles administrativos mediante WebSockets.

No es necesario recargar la página.

---

# Arquitectura General

```text
Cliente Web
      │
      ▼
 API REST
      │
      ▼
Resolución Tenant
(Subdominio)
      │
      ▼
 Base de Datos
      │
      ▼
 WebSockets
      │
      ▼
Administradores y Empleados
```

---

# MÓDULO 1: EMPRESA (ADMINISTRADORES Y EMPLEADOS)

---

# 1. Autenticación, Roles y Seguridad

## Login

El usuario envía:

```json
{
  "email": "admin@empresa.com",
  "password": "********"
}
```

### Flujo

1. Se reciben las credenciales.
2. Se busca el usuario.
3. Se compara el password con el hash almacenado.
4. Se genera un JWT.
5. Se devuelve el token al cliente.

---

## JWT con Claims (RBAC)

El token contiene información necesaria para la autorización.

### Ejemplo

```json
{
  "user_id": 123,
  "empresa_id": 10,
  "rol": "administrador",
  "permisos": [
    "configuracion",
    "reservas",
    "reportes"
  ]
}
```

---

## Middleware de Autorización

Cada endpoint verifica:

```text
JWT válido
+
Empresa asociada
+
Permisos del rol
```

---

## Cierre de Sesión

El sistema permite invalidar tokens.

### Flujo

1. El usuario solicita logout.
2. El JWT es agregado a una blacklist.
3. Redis almacena temporalmente el token.
4. El token deja de ser válido.

---

# 2. Gestión del Administrador

---

## Configuración Visual

Permite personalizar la apariencia del portal del cliente.

### Funcionalidades

- Cargar logo
- Cambiar colores
- Modificar estilos
- Actualizar branding

### Endpoints

#### Obtener configuración

```http
GET /api/empresa/configuracion
```

#### Actualizar configuración

```http
PUT /api/empresa/configuracion
```

#### Subir logo

```http
POST /api/empresa/configuracion/logo
```

---

## Gestión de Subdominios

### Creación Automática

Cuando una empresa se registra:

```text
Empresa: Resto Soft

Subdominio generado:

restosoft.tudominio.com
```

---

### Edición de Subdominio

Solo administradores.

### Validaciones

- Formato válido
- Longitud mínima
- Longitud máxima
- Unicidad en base de datos

### Endpoint

```http
POST /api/empresa/subdominio
```

```http
PUT /api/empresa/subdominio
```

---

## Gestión de Disponibilidad

Permite definir:

- Horarios de atención
- Días festivos
- Bloqueos especiales
- Aforo máximo por hora

### Ejemplo

```json
{
  "lunes_viernes": {
    "inicio": "09:00",
    "fin": "22:00"
  },
  "sabado_domingo": {
    "inicio": "10:00",
    "fin": "23:00"
  },
  "aforo_maximo": 20
}
```

### Endpoints

```http
GET /api/empresa/disponibilidad
```

```http
PUT /api/empresa/disponibilidad
```

---

# 3. Gestión de Empleados

---

## Lectura de Agenda

Obtiene reservas filtradas por empresa.

### Endpoint

```http
GET /api/reservas/agenda
```

### Parámetros

```http
GET /api/reservas/agenda?fecha=2025-05-20
```

### Respuesta

```json
[
  {
    "id": 1,
    "cliente": "Juan Pérez",
    "hora": "10:00",
    "personas": 2,
    "estado": "Pendiente"
  }
]
```

---

## Cambio de Estado de Reserva

Estados permitidos:

- Pendiente
- Confirmada
- En progreso
- Asistió
- Cancelada

### Endpoint

```http
PUT /api/reservas/{id}/estado
```

### Request

```json
{
  "estado": "Confirmada",
  "observacion": "Mesa lista"
}
```

---

# MÓDULO 2: CLIENTE DE LA EMPRESA

---

# 1. Identificación y Carga Dinámica

---

## Resolución del Tenant

Cuando el cliente accede:

```text
https://restosoft.tudominio.com
```

El backend:

1. Extrae el subdominio.
2. Busca la empresa asociada.
3. Obtiene su configuración.
4. Devuelve la información personalizada.

---

## Datos Entregados

```json
{
  "empresa_id": 10,
  "nombre": "Resto Soft",
  "logo": "logo.png",
  "colores": {
    "primario": "#6C4DFF",
    "secundario": "#8D75FF"
  },
  "aforo_maximo": 20,
  "horarios": []
}
```

---

# 2. Proceso de Reserva

---

## Validación de Disponibilidad

Antes de registrar una reserva:

Se verifica:

- Fecha
- Hora
- Cantidad de personas
- Aforo máximo permitido

### Ejemplo

```text
Aforo máximo: 10

Reservas actuales: 8

Solicitud: 2 personas

Resultado: Disponible
```

---

## Persistencia de la Reserva

Si existe disponibilidad:

```json
{
  "id": 9876,
  "empresa_id": 10,
  "fecha": "2025-05-20",
  "hora": "10:00",
  "personas": 2,
  "estado": "Pendiente"
}
```

La reserva queda asociada al tenant correspondiente.

---

# Sincronización en Tiempo Real

---

## Emisión de Evento

Después de guardar la reserva:

```javascript
io.to(`empresa-${empresaId}`).emit(
  "nuevaReserva",
  reserva
);
```

---

## Evento Emitido

```json
{
  "evento": "NuevaReserva",
  "empresaId": 10,
  "reservaId": 9876
}
```

---

## Recepción en Panel Administrativo

Todos los usuarios conectados de la empresa reciben instantáneamente:

```text
Nueva reserva recibida
Cliente: María López
Hora: 10:00
Personas: 2
Estado: Pendiente
```

---

# WebSockets

## Conexión

```javascript
socket.emit("join-company", {
  empresaId: 10
});
```

---

## Sala Privada

```text
empresa-10
```

Solo reciben eventos:

- Administradores
- Empleados de la empresa

---

# Modelo de Datos Simplificado

## Empresas

```sql
empresas
```

| Campo | Tipo |
|---------|---------|
| id | UUID |
| nombre | VARCHAR |
| subdominio | VARCHAR |
| logo | TEXT |
| colores | JSON |
| creado_en | TIMESTAMP |

---

## Usuarios

```sql
usuarios
```

| Campo | Tipo |
|---------|---------|
| id | UUID |
| empresa_id | UUID |
| nombre | VARCHAR |
| email | VARCHAR |
| password_hash | TEXT |
| rol | ENUM |

---

## Disponibilidad

```sql
disponibilidad
```

| Campo | Tipo |
|---------|---------|
| id | UUID |
| empresa_id | UUID |
| aforo_maximo | INT |
| configuracion | JSON |

---

## Reservas

```sql
reservas
```

| Campo | Tipo |
|---------|---------|
| id | UUID |
| empresa_id | UUID |
| cliente | VARCHAR |
| fecha | DATE |
| hora | TIME |
| personas | INT |
| estado | ENUM |

---

# Seguridad

- JWT Authentication
- RBAC (Role Based Access Control)
- Password Hashing (Argon2/Bcrypt)
- Middleware de autorización
- Validación de tenant
- Rate Limiting
- Blacklist JWT con Redis
- Validación de subdominios únicos

---

# Flujo Completo

```text
Cliente
   │
   ▼
Subdominio
   │
   ▼
Resolución Tenant
   │
   ▼
Configuración Empresa
   │
   ▼
Reserva
   │
   ▼
Validación de Cupo
   │
   ▼
Base de Datos
   │
   ▼
WebSocket Event
   │
   ▼
Administrador / Empleados
   │
   ▼
Actualización en Tiempo Real
```

---

# Beneficios

- Arquitectura Multiempresa (SaaS)

- Aislamiento por Tenant

- Control de acceso por roles

- Personalización por empresa

- Gestión centralizada de reservas

- Actualización en tiempo real

- Escalable para cientos o miles de empresas

- Compatible con REST API + WebSockets

---
