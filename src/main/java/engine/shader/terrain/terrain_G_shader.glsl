//GEOMETRY SHADER - Terrain
#version 430

#define LIGHT_MAX 10 // max light source count

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

/*===== in ======*/
in vec2 gs_textureCoords[];
in float gs_visibility[];
in vec4 gs_shadowCoords[];

/*===== out =====*/
out vec3 fs_tangent;
out vec2 fs_textureCoords;
out vec3 fs_toLightVector[LIGHT_MAX];
out vec3 fs_toCameraVector;
out float fs_visibility;
out vec4 fs_shadowCoords;


/*== uniforms ===*/
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 localMatrix;
uniform mat4 worldMatrix;
uniform vec4 clipPlane;

uniform vec3 lightPosition[LIGHT_MAX];

/*-------- functions -----------*/
void createVertex(int index, mat4 projectionViewMatrix) {

	fs_textureCoords = gs_textureCoords[index];
	fs_visibility = gs_visibility[index];
	fs_shadowCoords = gs_shadowCoords[index];

	gl_ClipDistance[0] = dot(gl_in[index].gl_Position, clipPlane);

	vec4 position = gl_in[index].gl_Position;
	for(int i = 0; i < LIGHT_MAX; i++) {
		fs_toLightVector[i] = lightPosition[i] - (projectionMatrix * position).xyz;
	}
	fs_toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - (projectionMatrix * position).xyz;

	gl_Position = projectionViewMatrix * position;

	EmitVertex();
}

/*------------- main ---------------*/
void main() {

	mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

	// pass values
	for(int i=0; i < gl_in.length(); i++) {
		createVertex(i, projectionViewMatrix);
	}

	EndPrimitive();

}