// VERTEX SHADER - Entity
#version 400 core
#define LIGHT_MAX 10

/* ===== in ====== */
in vec3 in_position;
in vec2 in_textureCoords;
in vec3 in_normal;

/* ===== out ===== */
out vec2 pass_textureCoordinates;
out vec3 pass_normal;
out vec3 surfaceNormal;
out vec3 toLightVector[LIGHT_MAX];
out vec3 toCameraVector;
out float fogVisibility;
out vec4 shadowCoords;

// reflection and refraction
out vec3 reflectedVector;
out vec3 refractedVector;

/* === uniforms == */
// matrices
uniform mat4 Transformation;
uniform mat4 Projection;
uniform mat4 View;

// camera variables
uniform vec3 cameraPosition;
uniform vec4 clipPlane;

// light
uniform vec3 lightPosition[LIGHT_MAX];
uniform int lightCount;
uniform float usesFakeLighting;

// reflection and refraction
uniform float reflectiveFactor;
uniform float refractiveFactor;
uniform float refractiveIndex;

// shadow variables
uniform mat4 toShadowMapSpace;
uniform float shadowDistance;
uniform float shadowTransitionDistance;

// material variables
uniform float numberOfRows;
uniform vec2 offset;

// ambient variables
uniform float fogDensity;
const float fogGradient = 5.0;

/*------------- main ---------------*/
void main(void) {

   vec4 worldPosition = Transformation * vec4(in_position, 1.0);
   shadowCoords = toShadowMapSpace * worldPosition;
   
   gl_ClipDistance[0] = dot(worldPosition, clipPlane);
   
   vec4 positionRelativeToCam = View * worldPosition;
   gl_Position = Projection * positionRelativeToCam;
      
   pass_textureCoordinates = (in_textureCoords / numberOfRows) + offset;
   
   pass_normal = in_normal;
   vec3 unitNormal = normalize(in_normal);
   vec3 viewVector = worldPosition.xyz - cameraPosition;
   
   if (reflectiveFactor > 0.0) {
	   reflectedVector = reflect(viewVector, unitNormal);
   }
   
   if (refractiveFactor > 0.0) {
	   refractedVector = refract(viewVector, unitNormal, 1.0 / refractiveIndex);
   }
      
   vec3 actualNormal = in_normal;
   if(usesFakeLighting > 0.5) {
      actualNormal = vec3(0.0,1.0,0.0);
   }

   surfaceNormal = (Transformation * vec4(actualNormal, 0.0)).xyz;

   for (int i = 0; i < LIGHT_MAX; i++) {
      toLightVector[i] = lightPosition[i] - worldPosition.xyz; 
   }
   
   toCameraVector = (inverse(View) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
   
   float distance = length(positionRelativeToCam.xyz);
   
   fogVisibility = exp(-pow((distance * fogDensity), fogGradient));
   fogVisibility = clamp(fogVisibility, 0.0, 1.0);
   
   distance = distance - (shadowDistance - shadowTransitionDistance);
   distance = distance / shadowTransitionDistance;
   shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
   
}
