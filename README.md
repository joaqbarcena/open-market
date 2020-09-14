## Ejemplo de las Apis de Mercado Libre


### Se utilizaron los recursos :
- `/sites` : Para buscar los sitios disponibles
- `/search` : Para buscar publicaciones
- `/items/{item}` : Para obtener solos las fotos (con `?attributes=pictures`)
- `/items/{item}/description` : Para obtener solo la descripcion  (con `?attributes=plain_text`)

Dentro de todo las API docs son claras excepto cuando habla del item per-se,
esta bien orientada para apps que funcionen como tool para subir y updatear
data en ML, ya que los `GET` para los recursos dentro de `/items` mas bien los deduci
de las docs que hablan sobre los item con (POST, PUT, DELETE).
Por ejemplo, en el momento que empece a hacer la `Product Page` encontre que en el json
que devuelve un `/search` el item no trae ni las `pictures` ni la `descripcion` lo cual
es correcto seria payload que no corresponder enviar en esa instancia, ahora si uno empieza
la busqueda de donde sacar las `pictures` o la `description` de un item he de ahi que al menos
en las docs no las supe ubicar, mas bien me guie por como hacen los `POST`, `PUT` de ambos y tratar
de hacer un `GET` a ver si era valido acceder a los recursos.

La api fue testeada con `Postman` antes de escribir los modelos en Android.

### Day/Night Support

La arme de forma que sea compatible con el theme `DayNight` por ahora solo
esta harcodeada en [MainApplication](./app/src/main/java/org/joaqbarcena/MainApplication.java)
(personalmente lo dejaria en `FOLLOW_SYSTEM`)




