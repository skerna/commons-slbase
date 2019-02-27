# Skerna slbase

Skerna Slbase,  hoy en día, la mayor parte lenguajes
cuenta con potentes estructuras de datos y existen cientos de librerías 
que proporcionan funcionalidad extra.

Esta librería proporciona utilidades especificas comunes en Skerna:


### Factory Singlenton

Es una mezcla de patron factory y singlenton permitiendo
construir objetos complejos y ademas brindarte la posibilidad
de retorna una unica instancia o generar un nueva.

Se ha discutido mucho sobre si  X patron de diseño vs X es 
mejor o no // o es un antipatron, el caso es que estos estan
y deben ser utilizados siempre en función de resolver un problema
o caso de uso.



### Syncronized 


En kotlin existen algunas primitivas como sincronized , sin embargo
a partir de la version  X de kotlin el bloque syncronized ha sido deprecado
por esta razón se implementa nuestra propia version de este bloque

Nota original de la doc en kotlin
```text 
Synchronization on any object is not supported on every platform and will be removed from the common standard library soon.
``` 
