# Realm of Valyron - RPG Game Backend

Microservicios independientes en Spring Boot 3.4.5 con arquitectura de descubrimiento de servicios usando Netflix Eureka.

## Requisitos previos

- **Docker Desktop** (Windows/Mac) o **Docker Engine** (Linux)
- **Docker Compose** v2.0+
- **MySQL 8.0** ejecutándose localmente en puerto 3306 con usuario `root` sin contraseña
  ```bash
  # Verificar que MySQL está corriendo
  mysql -u root -h localhost -e "SELECT VERSION();"
  ```

## Estructura del proyecto

```
valyron-microservices/
├── eureka-server/              # Registro de servicios (Netflix Eureka)
├── ms-auth/                    # Autenticación y JWT
├── ms-razas/                   # Razas (bonificaciones estadísticas)
├── ms-personajes/              # Gestión de personajes
├── ms-objetos/                 # Catálogo de items
├── ms-inventario/              # Inventario de personajes
├── ms-poderes/                 # Poderes/habilidades
├── ms-regiones/                # Mapa de regiones
├── ms-combate/                 # Motor de batalla
├── ms-misiones/                # Misiones/quests
├── ms-gremios/                 # Sistema de gremios
├── ms-historial/               # Registro de eventos
├── docker-compose.yml          # Orquestación Docker
└── README.md                   # Este archivo
```

## Levantar el proyecto con Docker

### Paso 1: Iniciar MySQL localmente

```bash
# Si usas Docker para MySQL
docker run --name mysql-valyron -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 -d mysql:8.0

# Si tienes MySQL instalado localmente
mysql.server start  # macOS
sudo service mysql start  # Linux
```

### Paso 2: Levantar todos los microservicios

```bash
# Desde el directorio raíz del proyecto
docker compose up --build

# Para ejecutar en background
docker compose up --build -d
```

Esto va a:
1. Compilar 13 imágenes Docker (multi-stage, descarga de dependencias cacheable)
2. Crear contenedores para cada microservicio
3. Arrancar eureka-server primero
4. Arrancar los 12 microservicios después (esperan a que Eureka esté healthy)

### Paso 3: Verificar que todo funciona

```bash
# Ver logs de todos los servicios
docker compose logs -f

# Ver logs de un servicio específico
docker compose logs -f ms-auth

# Ver estado de los contenedores
docker compose ps
```

## Endpoints principales

| Servicio | Puerto | Swagger UI |
|---|---|---|
| eureka-server | 8761 | http://localhost:8761/ |
| ms-auth | 8080 | http://localhost:8080/swagger-ui.html |
| ms-razas | 8081 | http://localhost:8081/swagger-ui.html |
| ms-personajes | 8082 | http://localhost:8082/swagger-ui.html |
| ms-objetos | 8083 | http://localhost:8083/swagger-ui.html |
| ms-inventario | 8084 | http://localhost:8084/swagger-ui.html |
| ms-poderes | 8085 | http://localhost:8085/swagger-ui.html |
| ms-regiones | 8086 | http://localhost:8086/swagger-ui.html |
| ms-combate | 8087 | http://localhost:8087/swagger-ui.html |
| ms-misiones | 8088 | http://localhost:8088/swagger-ui.html |
| ms-gremios | 8089 | http://localhost:8089/swagger-ui.html |
| ms-historial | 8090 | http://localhost:8090/swagger-ui.html |

### Ejemplo: crear un personaje

```bash
# 1. Ver razas disponibles
curl http://localhost:8081/api/v1/razas

# 2. Crear un personaje
curl -X POST http://localhost:8082/api/v1/personajes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Aragorn",
    "razaId": 1,
    "nivel": 1,
    "experiencia": 0
  }'
```

## Detener los servicios

```bash
# Detener y eliminar contenedores (mantiene imágenes)
docker compose down

# Detener, eliminar contenedores y volúmenes
docker compose down -v

# Detener sin eliminar
docker compose stop

# Reiniciar
docker compose restart
```

## Desarrollo local (sin Docker)

Si prefieres ejecutar los servicios directamente en tu máquina:

```bash
# Build individual (desde cada directorio)
cd ms-auth
./mvnw clean package -DskipTests
./mvnw spring-boot:run

# O build all
for dir in eureka-server ms-auth ms-razas ms-personajes ms-objetos ms-inventario ms-poderes ms-regiones ms-combate ms-misiones ms-gremios ms-historial; do
  (cd $dir && ./mvnw clean package -DskipTests)
done
```

## Arquitectura

### Autenticación

`ms-auth` es el único emisor de JWT (JJWT 0.11.5). El secret compartido es:
```
RealmOfValyronSecretKeyParaJWT2024$$$
```

Servicios protegidos (validan JWT):
- `ms-combate`
- `ms-historial`

Servicios abiertos (no requieren autenticación):
- Todos los demás

### Comunicación inter-servicios

Los servicios se llaman entre sí usando Spring WebClient con descubrimiento automático vía Eureka:

```
ms-personajes → ms-razas
ms-inventario → ms-objetos
ms-combate → ms-personajes, ms-inventario, ms-poderes, ms-historial
ms-misiones → ms-historial
```

**En Docker:** Los nombres se resuelven automáticamente a los contenedores (ej: `http://ms-personajes:8082/api/v1/...`)

**En local:** Se resuelven vía Eureka a `localhost:puerto`

### Base de datos

Cada servicio tiene su propia base de datos MySQL aislada:

| Servicio | Base de datos | Schema |
|---|---|---|
| ms-auth | auth_db | auto-generado |
| ms-razas | razas_db | auto-generado |
| ms-personajes | personajes_db | auto-generado |
| ms-objetos | objetos_db | auto-generado |
| ms-inventario | inventario_db | auto-generado |
| ms-poderes | poderes_db | auto-generado |
| ms-regiones | regiones_db | auto-generado |
| ms-combate | combate_db | auto-generado |
| ms-misiones | misiones_db | auto-generado |
| ms-gremios | gremios_db | auto-generado |
| ms-historial | historial_db | auto-generado |

**Nota:** Los schemas se crean automáticamente con `spring.jpa.hibernate.ddl-auto=update`

## Troubleshooting

### "Cannot connect to MySQL"
```bash
# Verificar que MySQL está escuchando en localhost:3306
netstat -tlnp | grep 3306  # Linux
netstat -tlnp | grep 3306  # macOS con homebrew
```

### "Eureka no registra servicios"
```bash
# Esperar 30 segundos después de levantar Docker
# Eureka toma tiempo para registrar clientes (default: 30s)
# Ver http://localhost:8761 en el navegador
```

### "Connection refused en inter-servicio llamadas"
- En Docker: asegurar que el nombre del servicio en la URL coincide con el `container_name` en docker-compose.yml
- En local: asegurar que todos los servicios están corriendo y Eureka los tiene registrados

### Limpiar build cache de Docker
```bash
docker compose down
docker system prune -a
docker compose up --build
```

## Variables de entorno

Las variables de entorno se pasan automáticamente via docker-compose. Para modificarlas, editar `docker-compose.yml`:

```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/auth_db...
  - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
  - JWT_SECRET=RealmOfValyronSecretKeyParaJWT2024$$$
```

## Desarrollo

### Reconstruir una sola imagen
```bash
docker compose up --build ms-auth
```

### Ver logs en tiempo real
```bash
docker compose logs -f ms-combate
```

### Ejecutar comandos en un contenedor
```bash
docker compose exec ms-auth /bin/sh
```

## Notas de producción

- ⚠️ La contraseña de MySQL está vacía (desarrollo solo)
- ⚠️ Los secrets (JWT) están en texto plano (usar variables de entorno en producción)
- ⚠️ No hay HTTPS configurado
- ⚠️ Los logs no están centralizados
- ✅ Base de datos separada por servicio (escalabilidad horizontal)
- ✅ Descubrimiento de servicios automático (resilencia)
- ✅ Health checks en Eureka (auto-deregistro de servicios caídos)
