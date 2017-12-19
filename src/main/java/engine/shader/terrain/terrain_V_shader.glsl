// VERTEX SHADER - Terrain
#version 430 core
#define LOD_MAX 8 // max level of distance count
#define LIGHT_MAX 10 // max light source count

/* ===== in ====== */
in vec3 in_position;

/* ===== out ===== */
out float tc_visibility;
out vec4 tc_shadowCoords;
out vec2 tc_textureCoords;
out float tc_clipDistance;

/* == uniforms == */
uniform mat4 View;
uniform mat4 Projection;
uniform mat4 Local;
uniform mat4 World;

uniform sampler2D heightMap;

uniform mat4 toShadowMapSpace;
uniform float shadowDistance;
uniform float shadowTransitionDistance;

uniform float fogDensity;

uniform vec3 cameraPosition;
uniform float scaleY;
uniform int lod;
uniform vec2 index;
uniform float gap;
uniform vec2 location;

uniform int lod_morph_area[LOD_MAX];

/* == constants == */
const float fogGradient = 5.0;

/* ------------- functions --------------- */
float morphLatitude(vec2 position) {

	vec2 frac = position - location;

	if(index == vec2(0, 0)) {
		float morph = frac.x - frac.y;
		if(morph > 0) {
			return morph;
		}
	}

	if(index == vec2(1, 0)) {
		float morph = gap - frac.x - frac.y;
		if(morph > 0) {
			return morph;
		}
	}
	if(index == vec2(0, 1)) {
		float morph = frac.x + frac.y - gap;
		if(morph > 0) {
			return -morph;
		}
	}
	if(index == vec2(1, 1)) {
		float morph = frac.y - frac.x;
		if(morph > 0) {
			return -morph;
		}
	}
	return 0;
}

float morphLongitude(vec2 position) {

	vec2 frac = position - location;

	if(index == vec2(0, 0)) {
		float morph = frac.y - frac.x;
		if(morph > 0) {
			return -morph;
		}
	}

	if(index == vec2(1, 0)) {
		float morph = frac.y - (gap - frac.x);
		if(morph > 0) {
			return morph;
		}
	}
	if(index == vec2(0, 1)) {
		float morph = gap - frac.y - frac.x;
		if(morph > 0) {
			return -morph;
		}
	}
	if(index == vec2(1, 1)) {
		float morph = frac.x - frac.y;
		if(morph > 0) {
			return morph;
		}
	}
	return 0;
}

vec2 morph(vec2 localPosition, int morph_area) {
	vec2 morphing = vec2(0, 0);

	vec2 fixPointLatitude = vec2(0, 0);
	vec2 fixPointLongitude = vec2(0, 0);
	float distLatitude = 0;
	float distLongitude = 0;

	if(index == vec2(0, 0)) {
		fixPointLatitude = location + vec2(gap, 0);
		fixPointLongitude = location + vec2(0, gap);
	}
	else if(index == vec2(1, 0)) {
		fixPointLatitude = location;
		fixPointLongitude = location + vec2(gap, gap);
	}
	else if(index == vec2(0, 1)) {
		fixPointLatitude = location + vec2(gap, gap);
		fixPointLongitude = location;
	}
	else if(index == vec2(1, 1)) {
		fixPointLatitude = location + vec2(0, gap);
		fixPointLongitude = location + vec2(gap, 0);
	}

	float planarFactor = 0;
	if(cameraPosition.y > abs(scaleY)) {
		planarFactor = 1;
	} else {
		planarFactor = cameraPosition.y / abs(scaleY);
	}

	distLatitude = length(cameraPosition - (World *
			vec4(fixPointLatitude.x, planarFactor, fixPointLatitude.y, 1)).xyz);
	distLongitude = length(cameraPosition - (World *
			vec4(fixPointLongitude.x, planarFactor, fixPointLongitude.y, 1)).xyz);
	if(distLatitude > morph_area) {
		morphing.x = morphLatitude(localPosition.xy);
	}
	if(distLongitude > morph_area) {
		morphing.y = morphLongitude(localPosition.xy);
	}

	return morphing;
}

/* ------------- main --------------- */
void main(void) {

   vec3 localPosition = (Local * vec4(in_position, 1.0)).xyz;

   if(lod > 0.0) {
	  localPosition.xz += morph(localPosition.xz, lod_morph_area[lod-1]);
   }

   float height = texture(heightMap, localPosition.xz).r;
   localPosition.y = height;

   vec4 worldPosition =  (World * vec4(localPosition, 1.0));
   
   tc_shadowCoords = toShadowMapSpace * vec4(worldPosition.x, 0.0, worldPosition.z, 1.0);
   
   vec4 positionRelativeToCam = View * worldPosition;

   gl_Position = worldPosition;

   tc_textureCoords = localPosition.xz;
   
   float distance = length(positionRelativeToCam.xyz);
   tc_visibility = exp(-pow((distance * fogDensity), fogGradient));
   tc_visibility = clamp(tc_visibility, 0.0, 1.0);
   
   distance = distance - (shadowDistance - shadowTransitionDistance);
   distance = distance / shadowTransitionDistance;
   tc_shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
   
}
