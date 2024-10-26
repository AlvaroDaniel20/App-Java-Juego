# Juego de Batalla por Turnos - Estilo Pokémon

## Idea principal

La idea principal del juego es una batalla por turnos en la que el jugador enfrentará a un enemigo controlado por una IA. La IA del enemigo está diseñada para analizar patrones en las acciones del jugador, adaptándose y ajustando sus estrategias para derrotarlo. Durante la batalla, tanto el jugador como el enemigo pueden realizar ataques básicos, defensas, habilidades especiales y usar objetos para mejorar sus habilidades o recuperarse.

## Desarrollo del juego

El desarrollo se centra en una estructura de clases bien definida, que facilita el manejo de personajes y sus estadísticas. La clase base Character define los atributos y comportamientos básicos compartidos por el jugador y el enemigo, como el manejo de daño y defensa. Las clases Player y Enemy heredan de Character y expanden sus funcionalidades específicas. Para mejorar la experiencia de juego, la IA del enemigo utiliza un conjunto de condiciones que le permiten reaccionar de manera más inteligente frente al jugador, lo que agrega un nivel de desafío y hace la partida más inmersiva.

## Clases Principales

### Class Character

Character es la clase base del juego que contiene los atributos comunes de los personajes: nombre, puntos de vida, ataque, defensa, ataque especial y defensa especial. Además, se encargan métodos como recibir y aplicar daño, y curarse. Character también aplica una lógica de defensa que reduce el daño recibido basándose en la estadística de defensa del personaje y un factor aleatorio, lo que añade variabilidad a las batallas.

### Class Player

Player representa al jugador y extiende Character, permitiéndole realizar acciones como el ataque básico, el ataque especial y la defensa. También lleva un registro de las últimas acciones tomadas, lo que le permite a la IA del enemigo analizar los patrones de juego. Además, el jugador puede obtener objetos aleatorios durante la batalla que mejoran sus estadísticas o recuperan puntos de vida, añadiendo un elemento estratégico a la partida.

### Class Enemy

Enemy es la clase que representa al enemigo y también hereda de Character. La IA del enemigo selecciona acciones basadas en condiciones, como el estado de salud del enemigo y los patrones de ataque del jugador. Tiene capacidad para realizar ataques básicos, ataques especiales, defenderse o curarse, dependiendo de la situación, y se ajusta dinámicamente al comportamiento del jugador, ofreciendo un reto adicional en cada batalla. Además, se ha mejorado la lógica de selección de ataques especiales y defensas para minimizar errores en las acciones realizadas.

### Class Item

Item representa los objetos especiales que el jugador puede encontrar durante la batalla. Estos objetos pueden aumentar el ataque, defensa o curar puntos de vida al jugador. Cada tipo de objeto tiene un efecto específico y potencia, ofreciendo una ventaja estratégica temporal.

### Class BattleSystem

BattleSystem organiza la lógica del combate entre el jugador y el enemigo. Gestiona los turnos, muestra las opciones de acción para el jugador y llama a los métodos correspondientes en las clases Player y Enemy. Además, genera objetos aleatorios y controla el flujo de la batalla hasta que el jugador o el enemigo pierdan todos sus puntos de vida. Se han implementado mejoras en la lógica para asegurar que las opciones de ataque especial y defensa sean correctamente seleccionadas y ejecutadas.

## Conclusión

Este juego de batalla por turnos ofrece una experiencia dinámica y estratégica donde cada decisión cuenta. El sistema de inteligencia artificial del enemigo presenta un desafío al jugador al adaptarse a sus patrones, haciendo cada partida única. La implementación modular facilita futuras expansiones y ajustes en las reglas de combate y en las acciones posibles, con lo cual el juego puede evolucionar para ofrecer aún más opciones y profundidad en sus mecánicas. Esta estructura también es ideal para aprender sobre programación orientada a objetos en Java, ofreciendo un balance entre entretenimiento y aprendizaje de conceptos clave en desarrollo de videojuegos.