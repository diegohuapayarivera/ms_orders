
# **Order Management API**

Este proyecto es una **API REST** construida con **Spring Boot** para gestionar órdenes de clientes. La API permite:
- Crear órdenes.
- Consultar órdenes por ID.
- Actualizar la cantidad si una orden ya existe.
- Manejar errores con respuestas HTTP adecuadas.

## **Tabla de Contenidos**
- [Requisitos Previos](#requisitos-previos)
- [Endpoints Disponibles](#endpoints-disponibles)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
  - [Localmente con Maven](#localmente-con-maven)
  - [Ejecución en Docker](#ejecución-en-docker)
- [Pruebas Unitarias](#pruebas-unitarias)
- [Docker](#docker)
  - [Dockerfile](#dockerfile)
  - [Construcción y Ejecución con Docker](#construcción-y-ejecución-con-docker)

---

## **Requisitos Previos**

Asegúrate de tener las siguientes herramientas instaladas en tu entorno:
- **Java 17**
- **Maven 3.8+**
- **Docker** (opcional para ejecutar la aplicación en contenedores)

---

## **Endpoints Disponibles**

### 1. **Crear una Orden**
**POST /orders**  
Crea una nueva orden o actualiza la cantidad si ya existe.

**Body (JSON):**
```json
{
    "customerId": "12345",
    "product": "Laptop",
    "quantity": 2
}
```

**Respuesta:**
```json
{
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "customerId": "12345",
    "product": "Laptop",
    "quantity": 2
}
```

---

### 2. **Consultar una Orden por ID**
**GET /orders/{id}**

**Respuesta (200):**
```json
{
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "customerId": "12345",
    "product": "Laptop",
    "quantity": 2
}
```

**Respuesta (404):**
```
Order con el ID {id} no se encontro
```

---

## **Ejecución del Proyecto**

### **Localmente con Maven**

1. **Clonar el Repositorio:**
   ```bash
   git clone https://github.com/diegohuapayarivera/ms_orders.git
   cd ms_orders
   ```

2. **Compilar el Proyecto:**
   ```bash
   mvn clean package
   ```

3. **Ejecutar la Aplicación:**
   ```bash
   java -jar target/ms_orders-0.0.1-SNAPSHOT.jar
   ```

4. **Acceder a la API:**
   - La aplicación estará disponible en: `http://localhost:8080`

---

## **Pruebas Unitarias**

Ejecuta las pruebas unitarias con el siguiente comando:

```bash
mvn test
```

Esto ejecutará las pruebas definidas en las clases **OrderServiceImplTest** y **OrderControllerTest** utilizando **JUnit** y **Mockito**.

---

## **Docker**

Puedes construir y ejecutar esta aplicación como un contenedor Docker utilizando el **Dockerfile** proporcionado.

### **Dockerfile**

```dockerfile
# Etapa 1: Construcción del proyecto
FROM maven:3.8.7-openjdk-17-slim AS build

WORKDIR /app

# Copia los archivos necesarios para descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente y compila el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto 8080 para la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### **Construcción y Ejecución con Docker**

1. **Construir la Imagen:**
   ```bash
   docker build -t ms-orders-app .
   ```

2. **Ejecutar el Contenedor:**
   ```bash
   docker run -p 8080:8080 ms-orders-app
   ```

3. **Acceder a la API:**
   - La aplicación estará disponible en: `http://localhost:8080`


## **Conclusión**

Este proyecto es una API REST simple para gestionar órdenes utilizando **Spring Boot** y **Docker** para facilitar su despliegue. Las pruebas unitarias garantizan que cada componente funcione correctamente, y el Dockerfile permite empaquetar y ejecutar la aplicación en cualquier entorno compatible con Docker.

Si tienes alguna duda o sugerencia, no dudes en abrir un **Issue** en el repositorio o realizar un **Pull Request**.
