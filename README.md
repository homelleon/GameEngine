# OutworldMind Engine
This is a Game Engine for game or graphic editor that is implemented in Java 8 language. 
Uses LWJGL 2.9.3 to control OpenGL and OpenAL libraries.
(planning to update used LWJGL build) 

DIRECTORY STRUCTURE
----------
```
src/main/java/engine/    engine core library tools
src/main/java/game/      game core library tools (here you can create your project)
src/main/java/physic/    phisics core library tools (NOT implemented yet)
src/main/java/editor/    game edit core library tools (NOT implemented yet)
document/                documentation
library/                 libraries used in engine core
src/main/java/resource/  resources for engine core
tool/                    tools is used for develop or design (will be deleted in future)
```

FUNCTIONS
----------
graphics:
```
- normal maped objects to create texture bump effect;
- water reflection and refraction;
- particle systems;
```
optimisation:
```
- all objects are stored in video buffers using VBO and VAO;
- frustum culling technic to increase graphic performance;
- uses shader program uniforms to manage position and visual effects on rendering;
```
techincs:
```
- ray casting technic to choose entities by selecting it with mouse coursor;
```
audio:
```
- uses 3 dimentional audio buffer sound engine based on OpenAL library;
```
GUI:
```
- can make your own in-game graphic interface and code its behaviour;
```
Exterior files:
```
- can choose game settings by changing settings.xml file;
- can manage models and textures by .xml file;
- can manage level map by .xml file;
- can manage game GUI by .xml file;
```

USAGE
----------
how to start:
```
1) create your own class in "game/main" folder.
2) make it extends "Game" class from "game" package of "engine" source folder (see default MyGame class);
3) create public methods "__onStart()" and "__onUpdate()";
4) put "super.__onStart()" and "super.__onUpdate" methods into created your methods before your code;
5) create method __onGamePause();
```
![MyGame](https://github.com/homelleon/GameEngine/blob/master/src/main/java/resource/other/myGame.jpg "MyGame example")
```
6) write your code;
7) use this.gameManager object to access the eninge in your game class;
8) run the engine/core/EngineMain.java file.
```
