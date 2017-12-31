// COMPUTE SHADER - heightPoints
#version 430 core
#extension GL_ARB_compute_shader : enable
#extension GL_ARB_shader_storage_buffer_object : enable

// local groups
layout (local_size_x = 1) in;

uniform sampler2D heightMap;								// in

// buffer
layout (std430) buffer positionBuffer {		// out
	float height[];
};

/* ------------- main --------------- */
void main(void) {

	uint index = gl_WorkGroupID.x * gl_NumWorkGroups.x + gl_WorkGroupID.y;
	vec2 textureCoord = vec2(float(gl_GlobalInvocationID.x) / gl_NumWorkGroups.x, float(gl_GlobalInvocationID.y) / gl_NumWorkGroups.y);
	height[index] = texture(heightMap, textureCoord).r;

}
