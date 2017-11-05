//GEOMETRY SHADER - Terrain
#version 430

#define LIGHT_MAX 10 // max light source count

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

/*===== in ======*/
in vec2 gs_textureCoords[];
in vec3 gs_toLightVector[][LIGHT_MAX];
in vec3 gs_toCameraVector[];
in float gs_visibility[];
in vec4 gs_shadowCoords[];
in float gs_clipDistance[];

/*===== out =====*/
out vec3 fs_normal;
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

/*-------- functions -----------*/
vec4 createVertex(int index, mat4 projectionViewMatrix) {

	fs_textureCoords = gs_textureCoords[index];
	for(int i = 0; i < LIGHT_MAX; i++) {
		fs_toLightVector[i] = gs_toLightVector[index][i];
	}
	fs_toCameraVector = gs_toCameraVector[index];
	fs_visibility = gs_visibility[index];
	fs_shadowCoords = gs_shadowCoords[index];

//	gl_ClipDistance[0] = gs_clipDistance[index];

	fs_normal = (projectionViewMatrix * vec4(0, 1, 0, 1)).xyz;

	vec4 position = gl_in[index].gl_Position;

	gl_Position = projectionViewMatrix * position;

	EmitVertex();

	return position;
}

/*------------- main ---------------*/
void main() {

	mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

	vec3[3] position;

	// pass values
	for(int i=0; i < gl_in.length(); i++) {
		position[i] = createVertex(i, projectionViewMatrix).xyz;
	}

	// calculate tangent vector
	vec3 deltaPos1 = position[1] - position[0];
	vec3 deltaPos2 = position[2] - position[0];

	vec2 deltaUVec1 = gs_textureCoords[1] - gs_textureCoords[0];
	vec2 deltaUVec2 = gs_textureCoords[2] - gs_textureCoords[0];
	float scale = 1.0 / (deltaUVec1.x * deltaUVec2.y - deltaUVec1.y * deltaUVec2.x);
	deltaPos1 = deltaPos1 * deltaUVec2.y;
	deltaPos2 = deltaPos2 * deltaUVec1.y;
	fs_tangent = scale * (deltaPos1 - deltaPos2);

	EndPrimitive();

}
