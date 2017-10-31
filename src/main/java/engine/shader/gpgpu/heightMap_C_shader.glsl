//COMPUTE SHADER - heightMap
#version 430 core
#define TERRAIN_SIZE 128
#extension GL_EXT_gpu_shader4 : enable

layout (local_size_x = 1, local_size_y = 1) in;

layout (binding = 0, rgba32f) uniform writeonly image2D heightMap;

uniform samplerBuffer positionMap;
uniform int size;
uniform float scale;

void main(void) {

	ivec2 xy = ivec2(gl_GlobalInvocationID.x, gl_GlobalInvocationID.y);

	int offset = int(gl_WorkGroupID.y * 512 + gl_WorkGroupID.x);

	float height = texelFetchBuffer(positionMap, offset).y / 256;

	vec4 TextureColor = vec4(height, height, height, 1.0);

	imageStore(heightMap, xy, TextureColor);

}
