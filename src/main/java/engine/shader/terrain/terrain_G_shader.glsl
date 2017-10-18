#version 430

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

in vec2 gs_textureCoords[];
in vec2 gs_globalTextureCoords[];
in vec3 gs_surfaceNormal[];
in vec3 gs_toLightVector[];
in vec3 gs_toCameraVector[];
in float gs_visibility[];
in vec4 gs_shadowCoords[];

out vec2 fs_textureCoords;
out vec2 fs_globalTextureCoords;
out vec3 fs_surfaceNormal;
out vec3 fs_toLightVector[10];
out vec3 fs_toCameraVector;
out float fs_visibility;
out vec4 fs_shadowCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void createVertex(int index, mat4 projectionViewMatrix) {

	fs_textureCoords = gs_textureCoords[index];
	fs_globalTextureCoords = gs_globalTextureCoords[index];
	fs_surfaceNormal = gs_surfaceNormal[index];
	//fs_toLightVector[index] = gs_toLightVector[index];
	fs_toCameraVector = gs_toCameraVector[index];
	fs_visibility = gs_visibility[index];
	fs_shadowCoords = gs_shadowCoords[index];

	vec4 position = gl_in[index].gl_Position;

	gl_Position = projectionViewMatrix * position;

	EmitVertex();
}

void main() {

	mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

	for(int i=0; i < gl_in.length(); i++) {
		createVertex(i, projectionViewMatrix);
	}

	EndPrimitive();

}
