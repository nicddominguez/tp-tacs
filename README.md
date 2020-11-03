# TP-TACS
## Grupo 3

### Especificación API REST

La especificación de la API está escrita usando OpenAPI 3.0.1. La misma se encuentra en `tacs.api/src/main/resources/swagger/api.yaml`.

La especificación puede visualizarse usando online usando [Swagger Editor](https://editor.swagger.io/)

### Cómo ejecutar la aplicacion?

La aplicación se puede ejecutar usando `docker-compose` y consiste de 3 servicios:

* `tacs-api`:
    * API REST desarrollada con Java + Spring Framework
* `tacs-frontend`:
    * Servicio que sirve el frontend desarrollado con NodeJS + Express
* `tacs-reverse-proxy`:
    * Instancia de nginx que funciona como reverse proxy y dirige los requests a `tacs-api` o a `tacs-frontend` según corresponda
    * El archivo de configuración de nginx se encuentra en `nginx/nginx.conf`
    * Actualmente abre el puerto 8080, pero puede modificarse en `docker-compose.yaml`

```bash
$ > docker-compose up -d --build
```

#### *Notas para la ejecución*

1. La API REST utiliza autenticación con Google y necesita el client id de nuestra aplicación para verificar los `Google Id Token`. Definir la variable `GOOGLE_OAUTH_CLIENT_ID` con el valor `475854452552-js6cnac0fjktpav4dcp570860l7etar8.apps.googleusercontent.com` antes de ejecutar la aplicación.

2. La API REST firma sus tokens JWT usando una clave que opcionalmente recibe como variable de entorno.
    * Se puede configurar definiendo la variable de entorno `JWT_SECRET` a la clave deseada.
    * Por defecto utiliza la clave `"secret"`

3. Al acceder al frontend, es importante hacerlo a través de la url `http://localhost:8080` (para nginx) o `http://localhost:3000` (para el server de desarrollo de React).
    * Esto se debe a que la url necesita estar registrada en nuestra aplicación de Google y el registro no permite direcciones IP, de forma que no se puede usar `127.0.0.1` u otra dirección IP directamente.

4. Se debe setear en `tacs.api/src/main/resources/application.properties` la propiedad `application.admin-emails` a la lista de mails que se requiere que sean administradores.

### Colección de Postman

Se puede importar con este link una colección de Postman para testear la aplicación: https://www.getpostman.com/collections/e5a1ea2fa747c5fb2d7a.

Para simplicar el testing con Postman, se puede desactivar la autorización de la aplicación modificando `tacs.api/src/main/resources/application.properties`

```
security.jwt.secret=${JWT_SECRET:secret}
security.jwt.issuer=Grupo 3
security.google.clientId=${GOOGLE_OAUTH_CLIENT_ID:secret}
security.rutas.permitidas=/**/*
security.rutas.administrativas=/admin/**

server.servlet.context-path=/api/v1
spring.mvc.date-format=yyyy/MM/dd
```