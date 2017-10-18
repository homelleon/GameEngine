#version 430

layout (quads, fractional_odd_spacing, cw) in;

in vec2 te_textureCoords[];
in vec2 te_mapCoords[];
in vec2 te_globalTextureCoords[];
in vec3 te_surfaceNormal[];
in vec3 te_toLightVector[];
in vec3 te_toCameraVector[];
in float te_visibility[];
in vec4 te_shadowCoords[];

out vec2 gs_textureCoords;
out vec2 gs_mapCoords;
out vec2 gs_globalTextureCoords;
out vec3 gs_surfaceNormal;
out vec3 gs_toLightVector;
out vec3 gs_toCameraVector;
out float gs_visibility;
out vec4 gs_shadowCoords;

uniform mat4 worldMatrix;
uniform mat4 localMatrix;
uniform mat4 transformationMatrix;

uniform sampler2D heightMap;
uniform float scaleY;

void main() {

	float u = gl_TessCoord.x;
	float v = gl_TessCoord.y;

	vec4 position =
		((1-u) * (1-v) * gl_in[12].gl_Position +
		u * (1-v) * gl_in[0].gl_Position +
		u * v * gl_in[3].gl_Position +
		(1-u) * v * gl_in[15].gl_Position);

	gs_textureCoords =
		((1-u) * (1-v) * te_textureCoords[12] +
		u * (1-v) * te_textureCoords[0] +
		u * v * te_textureCoords[3] +
		(1-u) * v * te_textureCoords[15]);

	vec2 mapCoords =
		((1-u) * (1-v) * te_mapCoords[12] +
		u * (1-v) * te_mapCoords[0] +
		u * v * te_mapCoords[3] +
		(1-u) * v * te_mapCoords[15]);

	float height = texture(heightMap, mapCoords).r;

	height *= scaleY;
	height -= 200;

	gs_mapCoords = mapCoords;

	position.y = height;

	gs_globalTextureCoords =
			((1-u) * (1-v) * te_globalTextureCoords[12] +
			u * (1-v) * te_globalTextureCoords[0] +
			u * v * te_globalTextureCoords[3] +
			(1-u) * v * te_globalTextureCoords[15]);

	gs_surfaceNormal =
			((1-u) * (1-v) * te_surfaceNormal[12] +
			u * (1-v) * te_surfaceNormal[0] +
			u * v * te_surfaceNormal[3] +
			(1-u) * v * te_surfaceNormal[15]);

	gs_toLightVector =
			((1-u) * (1-v) * te_toLightVector[12] +
			u * (1-v) * te_toLightVector[0] +
			u * v * te_toLightVector[3] +
			(1-u) * v * te_toLightVector[15]);

	gs_toCameraVector =
			((1-u) * (1-v) * te_toCameraVector[12] +
			u * (1-v) * te_toCameraVector[0] +
			u * v * te_toCameraVector[3] +
			(1-u) * v * te_toCameraVector[15]);

	gs_visibility =
			((1-u) * (1-v) * te_visibility[12] +
			u * (1-v) * te_visibility[0] +
			u * v * te_visibility[3] +
			(1-u) * v * te_visibility[15]);

	gs_shadowCoords =
				((1-u) * (1-v) * te_shadowCoords[12] +
				u * (1-v) * te_shadowCoords[0] +
				u * v * te_shadowCoords[3] +
				(1-u) * v * te_shadowCoords[15]);

	gl_Position = position;

}
