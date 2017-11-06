//VERTEX SHADER - Water
#version 400 core

/*===== in ======*/
in vec2 position;

/*===== out =====*/
out vec4 clipSpace;
out vec2 textureCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;
out float visibility;

/*== uniforms ==*/
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

uniform float fogDensity;

uniform float tiling;

/*== constants ==*/
const float fogGradient = 10.0;

/*------------- main ---------------*/
void main(void) {

    vec4 worldPosition = modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    clipSpace = projectionMatrix * positionRelativeToCam;
	gl_Position = clipSpace;
	textureCoords = vec2(position.x/2.0 + 0.5, position.y/2 + 0.5) * tiling;
	toCameraVector = cameraPosition - worldPosition.xyz;
	fromLightVector = worldPosition.xyz - lightPosition;
	
	float distance = length(positionRelativeToCam.xyz * tiling);
   	visibility = exp(-pow((distance*fogDensity),fogGradient));
   	visibility = clamp(visibility,0.0,1.0);
 
}
