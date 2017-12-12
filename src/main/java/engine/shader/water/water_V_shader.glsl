//VERTEX SHADER - Water
#version 400 core

/*===== in ======*/
in vec2 in_position;

/*===== out =====*/
out vec4 clipSpace;
out vec2 textureCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;
out float visibility;

/*== uniforms ==*/
uniform mat4 Projection;
uniform mat4 View;
uniform mat4 Model;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

uniform float fogDensity;

uniform float tiling;

/*== constants ==*/
const float fogGradient = 10.0;

/*------------- main ---------------*/
void main(void) {

    vec4 worldPosition = Model * vec4(in_position.x, 0.0, in_position.y, 1.0);
    vec4 positionRelativeToCam = View * worldPosition;
    clipSpace = Projection * positionRelativeToCam;
	gl_Position = clipSpace;
	textureCoords = vec2(in_position.x / 2.0 + 0.5, in_position.y / 2 + 0.5) * tiling;
	toCameraVector = cameraPosition - worldPosition.xyz;
	fromLightVector = worldPosition.xyz - lightPosition;
	
	float distance = length(positionRelativeToCam.xyz * tiling);
   	visibility = exp(-pow((distance * fogDensity), fogGradient));
   	visibility = clamp(visibility,0.0,1.0);
 
}
