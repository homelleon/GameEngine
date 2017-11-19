//COMPUTE SHADER - heightPoints
#version 430 core
#extension GL_ARB_compute_shader : enable
#extension GL_ARB_shader_storage_buffer_object : enable

layout (local_size_x = 1) in;

uniform sampler2D heightMap;

layout (std430) buffer positionBuffer {
	float height[];
};

void main(void) {

	vec2 textureCoord = gl_WorkGroupID.xy;

	int index = int(gl_WorkGroupID.x);
	height[index] = texture(heightMap, textureCoord).r;
	height[index] = 10;

}
