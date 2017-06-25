# OutworldMind Engine
This is a Game Engine for implementing game or graphic editor that is written in Java 8 language. 
Uses LWJGL to control OpenGL and OpenAL libraries. 

DIRECTORY STRUCTURE
----------
```
engine/     engine core library tools
game/       game core library tools
phisics/    phisics core library tools
editor/     game edit core library tools
doc/        documentation
libs/       libraries used in engine core
res/        resources for engine core
tools/      tools
```

FUNCTIONS
----------
base
```
- map save/load functionality to use .txt file to load or save objects in the scene.
```
graphics:
```
- all objects are stored in buffers
- uses shader program uniforms to change position and visual effects
- water reflection and refraction
- frustum culling technic to increase graphic performance
- ray casting technic to choose entities by selecting it with mouse coursor
```
sound:
```
- uses 3 dimentional audio buffer sound engine based on OpenAL library
```

USAGE
----------
game logic:
```
- create your own class in "game/main" folder.
- make it extend "Game" class from "game" package of "engine" source folder (see default MyGame class).
- create public methods "__onStart()" and "__onUpdate()".
- put "super.__onStart()" and "super.__onUpdate" methods into created by you methods before your code.
- write youre code using public methods of "scene" and "gameManager" objects.
- run the game.
```
