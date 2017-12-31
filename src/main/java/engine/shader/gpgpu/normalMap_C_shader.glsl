// COMPUTE SHADER - normalMap
#version 430 core

// local groups
layout (local_size_x = 16, local_size_y = 16) in;

// material
uniform sampler2D heightMap; 									   // in
layout (binding = 0, rgba32f) uniform writeonly image2D normalMap; // out

// size
uniform int size;
uniform float strength;

/* ------------- main --------------- */
void main(void) {

	ivec2 xy = ivec2(gl_GlobalInvocationID.xy);
	vec2 textureCoord = gl_GlobalInvocationID.xy / float(size);

	float texelSize = 1.0 / size;

	float z0 = texture(heightMap, textureCoord + vec2(-texelSize, -texelSize)).r;
	float z1 = texture(heightMap, textureCoord + vec2(0, -texelSize)).r;
	float z2 = texture(heightMap, textureCoord + vec2(texelSize, -texelSize)).r;
	float z3 = texture(heightMap, textureCoord + vec2(-texelSize, 0)).r;
	float z4 = texture(heightMap, textureCoord + vec2(texelSize, 0)).r;
	float z5 = texture(heightMap, textureCoord + vec2(-texelSize, texelSize)).r;
	float z6 = texture(heightMap, textureCoord + vec2(0, texelSize)).r;
	float z7 = texture(heightMap, textureCoord + vec2(texelSize, texelSize)).r;

	vec3 normal = vec3(0,0,0);

	// Sobel Filter
	normal.z = 1.0 / strength;
	normal.x = z0 + 2 * z3 + z5 - z2 - 2 * z4 - z7;
	normal.y = z0 + 2 * z1 + z2 - z5 - 2 * z6 - z7;

	imageStore(normalMap, xy, vec4(normalize(normal) + 1/2.0, 1));


}
