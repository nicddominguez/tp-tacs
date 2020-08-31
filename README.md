# TP-TACS 
## Grupo 3
### Como levantar la aplicacion?

```bash
$ > git clone https://github.com/mesaglio/tp-tacs
$ > cd tp-tacs
$ > docker build -t tp-tacs .
$ > docker run -it -p PUERTO:8080 tp-tacs
```
---
Nota: Reemplazar PUERTO por el valor del puerto donde se desee levantar.

Ejemplo: `docker run -it -p 8080:8080 tp-tacs`
