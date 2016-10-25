#version 400 core

in vec2 position;

out vec4 clipSpace;
out vec2 textureCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;
out float visibility;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

uniform float fogDensity;

const float gradient = 10.0;

uniform float tiling;

void main(void) {

    vec4 worldPosition = modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    clipSpace = projectionMatrix * positionRelativeToCam;
	gl_Position = clipSpace;
	textureCoords = vec2(position.x/2.0 + 0.5, position.y/2 + 0.5) * tiling;
	toCameraVector = cameraPosition - worldPosition.xyz;
	fromLightVector = worldPosition.xyz - lightPosition;
	
	float distance = length(positionRelativeToCam.xyz);
   	visibility = exp(-pow((distance*fogDensity),gradient));
   	visibility = clamp(visibility,0.0,1.0);
 
}