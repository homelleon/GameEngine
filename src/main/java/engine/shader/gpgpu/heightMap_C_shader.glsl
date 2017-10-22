//COMPUTE SHADER - heightMap
#version 430 core

layout (local_size_x = 16, local_size_y = 16) in;
in vec3 position;

layout (binding = 0, rgba32f) uniform writeonly image2D heightMap;

uniform int size;

void main(void) {

	ivec2 x = ivec2(gl_GlobalInvocationID.xy);
	vec2 texCoord = gl_GlobalInvocationID.xy / float(size);

	float texelSize = 1.0 / size;

	float z0 = position *
	float z1 = texture(heightMap, texCoord + vec2(0, -texelSize)).r;
	float z2 = texture(heightMap, texCoord + vec2(texelSize, -texelSize)).r;
	float z3 = texture(heightMap, texCoord + vec2(-texelSize, 0)).r;
	float z4 = texture(heightMap, texCoord + vec2(texelSize, 0)).r;
	float z5 = texture(heightMap, texCoord + vec2(-texelSize, texelSize)).r;
	float z6 = texture(heightMap, texCoord + vec2(0, texelSize)).r;
	float z7 = texture(heightMap, texCoord + vec2(texelSize, texelSize)).r;


}
