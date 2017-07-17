# OutworldMind Engine
This is a Game Engine for implementing game or graphic editor that is written in Java 8 language. 
Uses LWJGL to control OpenGL and OpenAL libraries. 

DIRECTORY STRUCTURE
----------
```
engine/    engine core library tools
game/      game core library tools (here you can create your project)
physic/    phisics core library tools (NOT implemented yet)
editor/    game edit core library tools (NOT implemented yet)
document/  documentation
library/   libraries used in engine core
resource/  resources for engine core
tool/      tools is used for develop or design (will be deleted in future)
```

FUNCTIONS
----------
base
```
- map save/load functionality to use .txt file to load or save objects in the scene.
```
graphics:
```
- all objects are stored in video buffers
- uses shader program uniforms to change position and visual effects
- water reflection and refraction
- frustum culling technic to increase graphic performance (set as default for alfa-version)
- ray casting technic to choose entities by selecting it with mouse coursor (set as default for alfa-version)
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
- make it extends "Game" class from "game" package of "engine" source folder (see default MyGame class).
- create public methods "__onStart()" and "__onUpdate()".
- put "super.__onStart()" and "super.__onUpdate" methods into created your methods before your code.
```
![MyGame](https://github.com/homelleon/GameEngine/blob/master/resource/other/myGame.jpg "MyGame example")
```
- write your code using public methods of "scene" and "gameManager" objects.
- run the engine/core/EngineMain.java file.
```
