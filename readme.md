# playList - api

Para utilizar Api sigue los suguientes pasos:

* clonar el repositorio
* instalar las dependencias (maven)
* ejecutar el proyecto
* En el archivo [postman_collection.json](postman/Playlist%20API.postman_collection.json) se encuentran las peticiones para probar la api
* Primero en authentication se debe crear un usuario o logearse con el por defecto
* Usuario por defecto:
  * username: admin, password: admin123
* Utilizar el token obtenido luego de hacer el login para hacer las peticiones, configurar en variables de ambiente (jwt_token) de la colección.
* Probar el resto de las peticiones.
