//COMPUTE SHADER - normalMap
#version 430 core

layout (local_size_x = 16, local_size_y = 16) in;

layout (binding = 0, rgba32f) uniform writeonly image2D normalMap;

uniform sampler2D heightMap;
uniform int N;
uniform float strength;

void main(void) {

	ivec2 xy = ivec2(gl_GlobalInvocationID.xy);
	vec2 texCoord = gl_GlobalInvocationID.xy / float(N);

	float texelSize = 1.0 / N;

	float z0 = texture(heightMap, texCoord + vec2(-texelSize, -texelSize)).r;
	float z1 = texture(heightMap, texCoord + vec2(0, -texelSize)).r;
	float z2 = texture(heightMap, texCoord + vec2(texelSize, -texelSize)).r;
	float z3 = texture(heightMap, texCoord + vec2(-texelSize, 0)).r;
	float z4 = texture(heightMap, texCoord + vec2(texelSize, 0)).r;
	float z5 = texture(heightMap, texCoord + vec2(-texelSize, texelSize)).r;
	float z6 = texture(heightMap, texCoord + vec2(0, texelSize)).r;
	float z7 = texture(heightMap, texCoord + vec2(texelSize, texelSize)).r;

	vec3 normal = vec3(0,0,0);

	// Sobel Filter
	normal.z = 1.0 / strength;
	normal.x = z0 + 2 * z3 + z5 - z2 - 2 * z4 - z7;
	normal.y = z0 + 2 * z1 + z2 - z5 - 2 * z6 - z7;

	imageStore(normalMap, xy, vec4(normalize(normal) + 1/2.0, 1));


}
