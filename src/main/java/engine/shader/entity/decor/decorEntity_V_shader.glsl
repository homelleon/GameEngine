//VERTEX SHADER - Entity
#version 400 core
#define LIGHT_MAX 10

/*===== in ======*/
in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

/*===== out =====*/
out vec2 pass_textureCoordinates;
out vec3 pass_normal;
out vec3 surfaceNormal;
out vec3 toLightVector[LIGHT_MAX];
out vec3 toCameraVector;
out float fogVisibility;
out vec4 shadowCoords;

/*=== uniforms ==*/
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec4 clipPlane;
uniform vec3 cameraPosition;

//light
uniform vec3 lightPosition[LIGHT_MAX];
uniform float usesFakeLighting;

//shadows
uniform mat4 toShadowMapSpace;
uniform float shadowDistance;
uniform float shadowTransitionDistance;

//texture
uniform float numberOfRows;
uniform vec2 offset;

//fog
uniform float fogDensity;

/*== constants ==*/
const float fogGradient = 5.0;

/*------------- main ---------------*/
void main(void) {

   vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
   shadowCoords = toShadowMapSpace * worldPosition;
   
   gl_ClipDistance[0] = dot(worldPosition, clipPlane);

   vec4 positionRelativeToCam = viewMatrix * worldPosition;
   gl_Position = projectionMatrix * positionRelativeToCam;
      
   pass_textureCoordinates = (textureCoordinates / numberOfRows) + offset;
   
   pass_normal = normal;
   vec3 unitNormal = normalize(normal);
   vec3 viewVector = worldPosition.xyz - cameraPosition;
   
   vec3 actualNormal = normal;
   if(usesFakeLighting > 0.5) {
      actualNormal = vec3(0.0, 1.0, 0.0);
   }

   surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
   for(int i=0; i < LIGHT_MAX; i++) {
      toLightVector[i] = lightPosition[i] - worldPosition.xyz; 
   }
   
   toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
   
   float distance = length(positionRelativeToCam.xyz);
   
   fogVisibility = exp(-pow((distance*fogDensity),fogGradient));
   fogVisibility = clamp(fogVisibility,0.0,1.0);
   
   distance = distance - (shadowDistance - shadowTransitionDistance);
   distance = distance / shadowTransitionDistance;
   shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);

}
