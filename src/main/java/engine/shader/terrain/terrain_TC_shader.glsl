//TESSELLATION CONTROL SHADER - Terrain
#version 430

layout (vertices = 16) out;

in vec2 tc_textureCoords[];
in vec3 tc_surfaceNormal[];
in vec3 tc_toLightVector[];
in vec3 tc_toCameraVector[];
in float tc_visibility[];
in vec4 tc_shadowCoords[];

out vec2 te_textureCoords[];
out vec3 te_surfaceNormal[];
out vec3 te_toLightVector[];
out vec3 te_toCameraVector[];
out float te_visibility[];
out vec4 te_shadowCoords[];

const int AB = 2;
const int BC = 3;
const int CD = 0;
const int DA = 1;

uniform int tessellationFactor;
uniform float tessellationSlope;
uniform float tessellationShift;
uniform vec3 cameraPosition;

float LodFactor(float dist) {

	float tessellationLevel = max(0.0, tessellationFactor/pow(dist, tessellationSlope) + tessellationShift);

	return tessellationLevel;
}

void main() {
	if(gl_InvocationID == 0) {

		vec3 abMid = vec3(gl_in[0].gl_Position + gl_in[3].gl_Position)/2.0;
		vec3 bcMid = vec3(gl_in[3].gl_Position + gl_in[15].gl_Position)/2.0;
		vec3 cdMid = vec3(gl_in[15].gl_Position + gl_in[12].gl_Position)/2.0;
		vec3 daMid = vec3(gl_in[12].gl_Position + gl_in[0].gl_Position)/2.0;

		float distanceAB = distance(abMid, cameraPosition);
		float distanceBC = distance(bcMid, cameraPosition);
		float distanceCD = distance(cdMid, cameraPosition);
		float distanceDA = distance(daMid, cameraPosition);

		gl_TessLevelOuter[AB] = mix(1, gl_MaxTessGenLevel, LodFactor(distanceAB));
		gl_TessLevelOuter[BC] = mix(1, gl_MaxTessGenLevel, LodFactor(distanceBC));
		gl_TessLevelOuter[CD] = mix(1, gl_MaxTessGenLevel, LodFactor(distanceCD));
		gl_TessLevelOuter[DA] = mix(1, gl_MaxTessGenLevel, LodFactor(distanceDA));

		gl_TessLevelInner[0] = (gl_TessLevelOuter[BC] + gl_TessLevelOuter[DA])/4;
		gl_TessLevelInner[1] = (gl_TessLevelOuter[AB] + gl_TessLevelOuter[CD])/4;

	}

	te_surfaceNormal[gl_InvocationID] = tc_surfaceNormal[gl_InvocationID];
	te_toLightVector[gl_InvocationID] = tc_toLightVector[gl_InvocationID];
	te_toCameraVector[gl_InvocationID] = tc_toCameraVector[gl_InvocationID];
	te_textureCoords[gl_InvocationID] = tc_textureCoords[gl_InvocationID];
	te_visibility[gl_InvocationID] = tc_visibility[gl_InvocationID];
	te_shadowCoords[gl_InvocationID] = tc_shadowCoords[gl_InvocationID];

	gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
}
