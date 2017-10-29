//COMPUTE SHADER - heightMap
#version 430 core

#define VERTEX_COUNT 128

layout (local_size_x = VERTEX_COUNT, local_size_y = VERTEX_COUNT, local_size_z = VERTEX_COUNT) in;
layout (std140, binding = 0) uniform vec3 position[VERTEX_COUNT];

layout (binding = 0, rgba32f) uniform writeonly image2D heightMap;

uniform int size;

void main(void) {

	ivec2 x = ivec2(gl_GlobalInvocationID.xy);

	vec4 pos = imageLoad()
	vec2 texCoord = gl_GlobalInvocationID.xy / float(size);

	float texelSize = 1.0 / size;

	vec4 Color.a = position

	imageStore(heightMap, x, Color);

}
