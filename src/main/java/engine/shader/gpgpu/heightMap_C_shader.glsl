//COMPUTE SHADER - heightMap
#version 430 core

#define VERTEX_COUNT 512

layout(std430, binding = 0) buffer position;

layout (binding = 0, rgba32f) uniform writeonly image2D heightMap;

uniform int size;

void main(void) {

	ivec2 x = ivec2(gl_GlobalInvocationID.xy);

	vec4 pos = vec4(position, 1.0);
	vec2 texCoord = gl_GlobalInvocationID.xy / float(size);

	float texelSize = 1.0 / size;

	vec4 Color.r = position.z;

	imageStore(heightMap, x, Color);

}
