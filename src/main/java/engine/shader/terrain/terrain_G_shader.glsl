//GEOMETRY SHADER - Terrain
#version 430

#define LIGHT_MAX 10 // max light source count

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

/*===== in ======*/
in vec2 gs_textureCoords[];
in vec3 gs_surfaceNormal[];
in vec3 gs_toLightVector[][LIGHT_MAX];
in vec3 gs_toCameraVector[];
in float gs_visibility[];
in vec4 gs_shadowCoords[];
in float gs_clipDistance[];

/*===== out =====*/
out vec2 fs_textureCoords;
out vec3 fs_surfaceNormal;
out vec3 fs_toLightVector[LIGHT_MAX];
out vec3 fs_toCameraVector;
out float fs_visibility;
out vec4 fs_shadowCoords;

/*== uniforms ===*/
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 localMatrix;
uniform mat4 worldMatrix;

/*-------- functions -----------*/
void createVertex(int index, mat4 projectionViewMatrix) {

	fs_textureCoords = gs_textureCoords[index];
	fs_surfaceNormal = (projectionViewMatrix * vec4(gs_surfaceNormal[index], 1.0)).xyz;
	for(int i = 0; i < LIGHT_MAX; i++) {
		fs_toLightVector[i] = (projectionViewMatrix * vec4(gs_toLightVector[index][i], 1.0)).xyz;
	}
	fs_toCameraVector = gs_toCameraVector[index];
	fs_visibility = gs_visibility[index];
	fs_shadowCoords = gs_shadowCoords[index];
	gl_ClipDistance[0] = gs_clipDistance[index];

	gl_Position = projectionViewMatrix * gl_in[index].gl_Position;

	EmitVertex();
}

/*------------- main ---------------*/
void main() {

	mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

	for(int i=0; i < gl_in.length(); i++) {
		createVertex(i, projectionViewMatrix);
	}

	EndPrimitive();

}
