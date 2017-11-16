//VERTEX SHADER - NM Entity
#version 400 core

#define LIGHT_MAX 10

/*==== in =====*/
in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;
in vec3 tangent;

/*==== out =====*/
out vec2 pass_textureCoordinates;
out vec3 toLightVector[LIGHT_MAX];
out vec3 toCameraVector;
out float visibility;
out vec4 shadowCoords;

/*=== uniforms ==*/
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPositionEyeSpace[LIGHT_MAX];
uniform vec4 clipPlane;
uniform float usesFakeLighting;

uniform mat4 toShadowMapSpace;
uniform float shadowDistance;
uniform float shadowTransitionDistance;

uniform float numberOfRows;
uniform vec2 offset;

uniform float fogDensity;

/*== constants ==*/
const float fogGradient = 2.0;

/*------------- main ---------------*/
void main(void) {
	
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	shadowCoords = toShadowMapSpace * worldPosition;
	
	gl_ClipDistance[0] = dot(worldPosition, clipPlane);
	mat4 modelViewMatrix = viewMatrix * transformationMatrix;
	vec4 positionRelativeToCam = modelViewMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * positionRelativeToCam;
	
	pass_textureCoordinates = (textureCoordinates / numberOfRows) + offset;
	
	vec3 actualNormal = normal;
	if(usesFakeLighting > 0.5) {
	   actualNormal = vec3(0.0, 1.0, 0.0);
	}
	
	vec3 surfaceNormal = (modelViewMatrix * vec4(actualNormal, 0.0)).xyz;

	vec3 norm = normalize(surfaceNormal);
	vec3 tang = normalize((modelViewMatrix * vec4(tangent, 0.0)).xyz);
	vec3 bitang = normalize(cross(norm, tang));
	
	mat3 toTangentSpace = mat3(
		tang.x, bitang.x, norm.x,
		tang.y, bitang.y, norm.y,
		tang.z, bitang.z, norm.z
	);
	


	for(int i = 0; i < LIGHT_MAX; i++) {
		toLightVector[i] = toTangentSpace * (lightPositionEyeSpace[i] - positionRelativeToCam.xyz);
	}
	toCameraVector = toTangentSpace * (-positionRelativeToCam.xyz);
	
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*fogDensity), fogGradient));
	visibility = clamp(visibility, 0.0, 1.0);

	distance = distance - (shadowDistance - shadowTransitionDistance);
    distance = distance / shadowTransitionDistance;
    shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
    
	
}
