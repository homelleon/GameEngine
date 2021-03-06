//VERTEX SHADER - Voxel
#version 400 core

/*===== in ======*/
in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

/*===== out =====*/
out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[10];
out vec3 toCameraVector;
out float visibility;
out vec4 shadowCoords;

/*== uniforms ==*/
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[10];
uniform int lightCount;

uniform mat4 toShadowMapSpace;
uniform float shadowDistance;
uniform float shadowTransitionDistance;

uniform float useFakeLighting;

uniform float numberOfRows;
uniform vec2 offset;

uniform float fogDensity;

uniform vec4 plane;

/*== constants ==*/
const float fogGradient = 3.0;

/*------------- main ---------------*/
void main(void) {

   vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
   shadowCoords = toShadowMapSpace * worldPosition;
   
   gl_ClipDistance[0] = dot(worldPosition, plane);
   
   vec4 positionRelativeToCam = viewMatrix * worldPosition;
   gl_Position = projectionMatrix * positionRelativeToCam;
   pass_textureCoords = (textureCoordinates / numberOfRows) + offset;
   
      
   vec3 actualNormal = normal;
   if(useFakeLighting > 0.5) {
      actualNormal = vec3(0.0,1.0,0.0);
   }

   surfaceNormal = (transformationMatrix * vec4(actualNormal,0.0)).xyz;
   for(int i=0;i<lightCount;i++) {
      toLightVector[i] = lightPosition[i] - worldPosition.xyz; 
   }
   toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
   
   float distance = length(positionRelativeToCam.xyz);
   visibility = exp(-pow((distance*fogDensity),fogGradient));
   visibility = clamp(visibility,0.0,1.0);
   
   distance = distance - (shadowDistance - shadowTransitionDistance);
   distance = distance / shadowTransitionDistance;
   shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
   
}
