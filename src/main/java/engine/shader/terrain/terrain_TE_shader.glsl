//TESSELLATION EVALUATION SHADER - Terrain
#version 430

layout (quads, equal_spacing, cw) in;

/*===== in ======*/
in vec2 te_textureCoords[];
in float te_visibility[];
in vec4 te_shadowCoords[];

/*===== out =====*/
out vec2 gs_textureCoords;
out float gs_visibility;
out vec4 gs_shadowCoords;

/*== uniforms ===*/
// matrix and planes
uniform mat4 worldMatrix;
uniform mat4 localMatrix;
uniform vec4 clipPlane;
// maps
uniform sampler2D heightMap;
uniform float scaleY;

/*------ interpolate functions -------*/
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

/*------------- main ---------------*/
void main() {

	// tess coordinates
	float u = gl_TessCoord.x;
	float v = gl_TessCoord.y;

	// interpolate all variables between main points
	vec2 textureCoords = interpolate2D(te_textureCoords, u, v);
	gs_textureCoords = textureCoords;

	gs_visibility = interpolateFloat(te_visibility, u, v);
	gs_shadowCoords = interpolate4D(te_shadowCoords, u, v);

	// position - interpolate and add heights
	vec4 position =
		((1-u) * (1-v) * gl_in[12].gl_Position +
		u * (1-v) * gl_in[0].gl_Position +
		u * v * gl_in[3].gl_Position +
		(1-u) * v * gl_in[15].gl_Position);

	float height = texture(heightMap, textureCoords).r;
	height *= scaleY;
	position.y = height;

	gl_Position = position;

}
