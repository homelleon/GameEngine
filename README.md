# GameEngine
OutworldMind Engine

@version 1.0

This is a Game Engine for implementing game or graphic editor that is written in Java 8 language. 
Uses LWJGL to control OpenGL and OpenAL libraries. 

Implemented functions.

base:
- map save/load functionality to use .txt file to load or save objects at scene.

graphics:
- all objects are stored in buffers
- uses shader program　uniforms to change position and graphic technics
- water reflection and refraction
- frustum culling technic to increase graphic performance
- ray casting technic to choose entities by selecting it with mouse coursor

sound:
- uses 3 dimentional audio buffer sound engine based on OpenAL library
