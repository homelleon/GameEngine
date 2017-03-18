#OutworldMind Engine
==================
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
