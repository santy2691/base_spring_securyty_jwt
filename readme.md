### codigo base de sistema de login spring boot y jwt

#### Version 
esta version es la 1.0

#### descripcion 
este es un codigo base de sistemas de login para API REST que funciona a traves de un token(JWT). 

en esta base o ejemplo podemos hacer las llamadas necesarias a la API para: 
* crear un usuario.
* solicitar un token verifiacando las credenciales.

#### rutas: 
---
##### login 
esta ruta sirve para generar el token del usuario ya registrado en la API 


metodo: `POST`
ruta : `rest/login`
body: 
```
{
  "email": <email del usuario>,
  "password": <contraseña del usuario>
 }
```
---

##### registrer
este metodo sirve para registrar un usuario nuevo en la API 
el correo electronico no debe existir ya en la BD 

metodo: `POST`
ruta: `rest/registrer`
body: 
```
{
  "nombre": <nombre del usuario>,
  "apellido": <apellido del usuario>,
  "email": <correo electronico del usuario>,
  "password": <contraseña>
 }
```

---


#### Base de datos: 
en la ruta : `src\main\resources\sql\` se encuentra los script de la BD para las diferentes versiones   