// COMPUTE SHADER - heightMap
#version 430 core
#extension GL_EXT_gpu_shader4 : enable

// local groups
layout (local_size_x = 1, local_size_y = 1) in;

// material
uniform samplerBuffer positionMap;									// in
layout (binding = 0, rgba32f) uniform writeonly image2D heightMap;	// out

// size
uniform int size;
uniform float scale;

/* ------------- main --------------- */
void main(void) {

	ivec2 xy = ivec2(gl_GlobalInvocationID.x, gl_GlobalInvocationID.y);

	int offset = int(gl_WorkGroupID.y * size + gl_WorkGroupID.x);

	float height = texelFetchBuffer(positionMap, offset).y / 128;

	vec4 TextureColor = vec4(height, height, height, 1.0);

	imageStore(heightMap, xy, TextureColor);

}
