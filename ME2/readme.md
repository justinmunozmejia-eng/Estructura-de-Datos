# ME2 Proyecto Data-Bay

## Descripción
Este proyecto desarrolla un sistema básico en Java que simula el funcionamiento de un puerto inteligente llamado **Data-Bay**.  
El objetivo principal es aplicar estructuras de datos fundamentales como **arreglos, matrices, colas y pilas**, usando programación orientada a objetos de forma sencilla y clara. 

## Objetivo
Modelar el recorrido de los contenedores dentro del puerto desde su registro en el manifiesto, su ubicación en el patio, su paso por inspección y su carga final en el buque. 

## Estructura del proyecto 

### `entidades`
Contiene la clase principal del modelo: 

- `Contenedor.java`: representa cada contenedor con los atributos `id`, `peso` y `prioridad`. 

### `servicios`
Contiene la lógica del sistema:

- `ServicioManifiesto.java`: administra el arreglo de contenedores y calcula el peso total. 
- `ServicioPatio.java`: administra la matriz donde se ubican los contenedores.
- `ServicioInspeccion.java`: implementa una cola FIFO usando `LinkedList`. 
- `ServicioCarga.java`: implementa una pila LIFO usando `Stack` y una pila auxiliar.

### `app`
Contiene la clase de ejecución principal: 

- `Aplicacion.java`: crea los contenedores de prueba y ejecuta todo el flujo del sistema. 

## Funcionalidad
El programa realiza las siguientes acciones:

1. Crea contenedores de prueba directamente en el código.
2. Los registra en un arreglo como manifiesto.
3. Los ubica en una matriz que representa el patio. 
4. Envía a una cola los contenedores con mayor prioridad.
5. Carga los contenedores en una pila que representa el buque.
6. Muestra en consola cada paso del proceso. 

## Ejecución
Para ejecutar el proyecto, compila la clase principal `Aplicacion.java` desde el paquete `app`. 

## Autor
Justin Muñoz Mejía
