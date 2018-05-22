// COMMON SHADER PART
#version 430

#define LOD_MAX 8
#define LIGHT_MAX 10
#define SHADOW_BIAS 0.002
#define PCF_NUM_SAMPLES 16
#define BLOCKER_SEARCH_NUM_SAMPLES 16
#define NEAR_PLANE 9.5
#define LIGHT_WORLD_SIZE 0.5
#define LIGHT_FRUSTUM_WIDTH 3.75

#define LIGHT_SIZE_UV (LIGHT_WORLD_SIZE / LIGHT_FRUSTUM_WIDTH)

vec4 encodeFloat(float f) {
  vec4 enc = vec4(1.0, 255.0, 65025.0, 16581375.0) * f;
  enc = fract(enc);
  enc -= enc.yzww * vec4(1.0 / 255.0, 1.0 / 255.0, 1.0 / 255.0, 0.0);
  return enc;
}

float decodeFloat(vec4 rgba) {
  return dot(rgba, vec4(1.0, 1 / 255.0, 1 / 65025.0, 1 / 16581375.0));
}

vec2[] poissonDisk = {
		vec2( -0.94201624, -0.39906216 ),
		vec2( 0.94558609, -0.76890725 ),
		vec2( -0.094184101, -0.92938870 ),
		vec2( 0.34495938, 0.29387760 ),
		vec2( -0.91588581, 0.45771432 ),
		vec2( -0.81544232, -0.87912464 ),
		vec2( -0.38277543, 0.27676845 ),
		vec2( 0.97484398, 0.75648379 ),
		vec2( 0.44323325, -0.97511554 ),
		vec2( 0.53742981, -0.47373420 ),
		vec2( -0.26496911, -0.41893023 ),
		vec2( 0.79197514, 0.19090188 ),
		vec2( -0.24188840, 0.99706507 ),
		vec2( -0.81409955, 0.91437590 ),
		vec2( 0.19984126, 0.78641367 ),
		vec2( 0.14383161, -0.14100790 )
};

float penumbraSize(float zReceiver, float zBlocker) {
	return (zReceiver - zBlocker) / zBlocker;
}

void findBlocker(float avgBlockerDepth, float numBlockers, sampler2D shadowMap, vec2 coords, float zReceiver) {
	float searchWidth = LIGHT_SIZE_UV * (zReceiver - NEAR_PLANE) / zReceiver;

	float blockerSum = 0;
	numBlockers = 0;

	for (int i = 0; i < BLOCKER_SEARCH_NUM_SAMPLES; i++) {
		float shadowMapDepth = decodeFloat(texture(shadowMap, coords + poissonDisk[i] * searchWidth));
		if (shadowMapDepth < zReceiver) {
			blockerSum += shadowMapDepth;
			numBlockers++;
		}
	}

	avgBlockerDepth = blockerSum / numBlockers;
}

float filterPCF(sampler2D shadowMap, vec2 coords, float zReceiver, float filterRadiusCoords) {
	float sum = 0.0;
	for (int i = 0; i < PCF_NUM_SAMPLES; i++) {
		vec2 offset = poissonDisk[i] * filterRadiusCoords;
		sum += decodeFloat(texture(shadowMap, coords + offset, zReceiver));
	}
	return sum / PCF_NUM_SAMPLES;
}

float fetchPCSS(sampler2D shadowMap, vec4 shadowLookup) {
	vec2 coords = shadowLookup.xy / shadowLookup.w;
	float zReceiver = shadowLookup.z;

	float avgBlockerDepth = 0;
	float numBlockers = 0;
	findBlocker(avgBlockerDepth, numBlockers, shadowMap, coords, zReceiver);

	if (numBlockers < 1)
		return 1.0;

	float penumbraRatio = penumbraSize(zReceiver, avgBlockerDepth);
	float filterRadiusCoords = penumbraRatio * LIGHT_SIZE_UV * NEAR_PLANE / shadowLookup.z;

	return filterPCF(shadowMap, coords, zReceiver, filterRadiusCoords);
}
