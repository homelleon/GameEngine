//TESSELLATION EVALUATION SHADER - Terrain
#version 430

layout (quads, equal_spacing, cw) in;

in vec2 te_textureCoords[];
in vec3 te_surfaceNormal[];
in vec3 te_toLightVector[];
in vec3 te_toCameraVector[];
in float te_visibility[];
in vec4 te_shadowCoords[];

out vec2 gs_textureCoords;
out vec3 gs_surfaceNormal;
out vec3 gs_toLightVector;
out vec3 gs_toCameraVector;
out float gs_visibility;
out vec4 gs_shadowCoords;

uniform mat4 worldMatrix;
uniform mat4 localMatrix;
uniform mat4 transformationMatrix;
uniform vec4 clipPlane;

uniform sampler2D heightMap;
uniform float scaleY;

vec4 interpolate4D(vec4 vector[gl_MaxPatchVertices], float u, float v) {
	return ((1-u) * (1-v) * vector[12] +
			u * (1-v) * vector[0] +
			u * v * vector[3] +
			(1-u) * v * vector[15]);
}

vec3 interpolate3D(vec3 vector[gl_MaxPatchVertices], float u, float v) {
	return ((1-u) * (1-v) * vector[12] +
			u * (1-v) * vector[0] +
			u * v * vector[3] +
			(1-u) * v * vector[15]);
}

vec2 interpolate2D(vec2 vector[gl_MaxPatchVertices], float u, float v) {
	return ((1-u) * (1-v) * vector[12] +
			u * (1-v) * vector[0] +
			u * v * vector[3] +
			(1-u) * v * vector[15]);
}

float interpolateFloat(float vector[gl_MaxPatchVertices], float u, float v) {
	return ((1-u) * (1-v) * vector[12] +
			u * (1-v) * vector[0] +
			u * v * vector[3] +
			(1-u) * v * vector[15]);
}

void main() {

	float u = gl_TessCoord.x;
	float v = gl_TessCoord.y;

	vec2 textureCoords = interpolate2D(te_textureCoords, u, v);
	gs_textureCoords = textureCoords;

	gs_surfaceNormal = interpolate3D(te_surfaceNormal, u, v);
	gs_toLightVector = interpolate3D(te_toLightVector, u, v);
	gs_toCameraVector = interpolate3D(te_toCameraVector, u, v);
	gs_visibility = interpolateFloat(te_visibility, u, v);
	gs_shadowCoords = interpolate4D(te_shadowCoords, u, v);

	vec4 position =
		((1-u) * (1-v) * gl_in[12].gl_Position +
		u * (1-v) * gl_in[0].gl_Position +
		u * v * gl_in[3].gl_Position +
		(1-u) * v * gl_in[15].gl_Position);

	float height = texture(heightMap, textureCoords).r;
	height *= scaleY;
	height -= 100;
	position.y = height;

	gl_ClipDistance[0] = dot(position, clipPlane);

	gl_Position = position;

}
