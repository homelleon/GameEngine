//VERTEX SHADER - Terrain
#version 430 core

/*===== in ======*/
in vec3 in_position;
in vec2 textureCoordinates;
in vec3 normal;

/*===== out =====*/
out vec3 tc_surfaceNormal;
out vec3 tc_toLightVector[10];
out vec3 tc_toCameraVector;
out float tc_visibility;
out vec4 tc_shadowCoords;
out vec2 tc_textureCoords;
out vec2 tc_mapCoords;
out vec2 tc_globalTextureCoords;

/*== uniforms ==*/
uniform int lightCount;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[10];

uniform sampler2D heightMap;

uniform mat4 toShadowMapSpace;
uniform float shadowDistance;
uniform float shadowTransitionDistance;

uniform float fogDensity;

uniform vec4 clipPlane;

uniform vec3 cameraPosition;
uniform float scaleY;
uniform int lod;
uniform vec2 index;
uniform mat4 localMatrix;
uniform mat4 worldMatrix;
uniform float gap;
uniform vec2 location;
uniform mat4 transformationMatrix;

uniform int lod_morph_area[8];

/*== constants ==*/
const float fogGradient = 5.0;

/*------------- functions ---------------*/
float morphLatitude(vec2 position) {

	vec2 frac = position - location;

	if(index == vec2(0,0)) {
		float morph = frac.x - frac.y;
		if(morph > 0)
			return morph;
	}

	if(index == vec2(1,0)) {
		float morph = gap - frac.x - frac.y;
		if(morph > 0)
			return morph;
	}
	if(index == vec2(0,1)) {
		float morph = frac.x + frac.y - gap;
		if(morph > 0)
			return -morph;
	}
	if(index == vec2(1,1)) {
		float morph = frac.y - frac.x;
		if(morph > 0)
			return -morph;
	}
	return 0;
}

float morphLongitude(vec2 position) {

	vec2 frac = position - location;

	if(index == vec2(0,0)) {
		float morph = frac.y - frac.x;
		if(morph > 0)
			return -morph;
	}

	if(index == vec2(1,0)) {
		float morph = frac.y - (gap - frac.x);
		if(morph > 0)
			return morph;
	}
	if(index == vec2(0,1)) {
		float morph = gap - frac.y - frac.x;
		if(morph > 0)
			return -morph;
	}
	if(index == vec2(1,1)) {
		float morph = frac.x - frac.y;
		if(morph > 0)
			return morph;
	}
	return 0;
}

vec2 morph(vec2 localPosition, int morph_area) {
	vec2 morphing = vec2(0,0);

	vec2 fixPointLatitude = vec2(0,0);
	vec2 fixPointLongitude = vec2(0,0);
	float distLatitude = 0;
	float distLongitude = 0;

	if(index == vec2(0,0)) {
		fixPointLatitude = location + vec2(gap,0);
		fixPointLongitude = location + vec2(0,gap);
	}
	else if(index == vec2(1,0)) {
		fixPointLatitude = location;
		fixPointLongitude = location + vec2(gap,gap);
	}
	else if(index == vec2(0,1)) {
		fixPointLatitude = location + vec2(gap,gap);
		fixPointLongitude = location;
	}
	else if(index == vec2(1,1)) {
		fixPointLatitude = location + vec2(0,gap);
		fixPointLongitude = location + vec2(gap,0);
	}

	float planarFactor = 0;
	if(cameraPosition.y > abs(scaleY))
		planarFactor = 1;
	else planarFactor = cameraPosition.y / abs(scaleY);

	distLatitude = length(cameraPosition - (worldMatrix *
			vec4(fixPointLatitude.x, planarFactor, fixPointLatitude.y,1)).xyz);
	distLongitude = length(cameraPosition - (worldMatrix *
			vec4(fixPointLongitude.x, planarFactor, fixPointLongitude.y,1)).xyz);
	if(distLatitude > morph_area)
		morphing.x = morphLatitude(localPosition.xy);
	if(distLongitude > morph_area)
		morphing.y = morphLongitude(localPosition.xy);

	return morphing;
}

/*------------- main ---------------*/
void main(void) {

   vec3 localPosition = (localMatrix * vec4(in_position.x, in_position.y, in_position.z,1)).xyz;

   if(lod > 0) {
	  localPosition.xz += morph(localPosition.xz, lod_morph_area[lod-1]);
   }

   float height = texture(heightMap, localPosition.xz).r;


   //vec4 worldPosition0 = transformationMatrix * vec4(in_position, 1.0);
   vec4 worldPosition = worldMatrix * vec4(localPosition.x, height, localPosition.z, 1);

   tc_mapCoords = vec4(transformationMatrix * vec4(localPosition.x,0,localPosition.z,1.0)).xz;

   tc_shadowCoords = toShadowMapSpace * worldPosition;
   
   gl_ClipDistance[0] = dot(worldPosition, clipPlane);
   
   vec4 positionRelativeToCam = viewMatrix * worldPosition;

   gl_Position = worldMatrix * vec4(localPosition.x, height, localPosition.z, 1);

   tc_textureCoords = textureCoordinates;

   tc_globalTextureCoords = vec4(worldMatrix * vec4(textureCoordinates.x, 0, textureCoordinates.y, 1.0)).xz;

   tc_surfaceNormal = vec3(localMatrix * vec4(normal,0.0));

   for(int i=0;i<lightCount;i++) {
      tc_toLightVector[i] = lightPosition[i] - worldPosition.xyz;
   }

   tc_toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
   
   float distance = length(positionRelativeToCam.xyz);
   tc_visibility = exp(-pow((distance*fogDensity), fogGradient));
   tc_visibility = clamp(tc_visibility,0.0,1.0);
   
   distance = distance - (shadowDistance - shadowTransitionDistance);
   distance = distance / shadowTransitionDistance;
   tc_shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
   
}
