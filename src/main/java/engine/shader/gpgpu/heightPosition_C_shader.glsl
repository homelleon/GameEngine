//COMPUTE SHADER - heightPoints
#version 430 core

layout (local_size_x = 1, local_size_y = 1) in;

uniform sampler2D heightMap;

layout (std430, binding=0) writeonly buffer positionBuffer {
	float height[];
};

void main(void) {

	vec2 textureCoord = gl_GlobalInvocationID.xy;

	int index = int(gl_GlobalInvocationID.y * gl_NumWorkGroups.x + gl_GlobalInvocationID.x);

	height[index] = texture(heightMap, textureCoord).r;

}
