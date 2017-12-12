// VERTEX SHADER - Debug
#version 430 core

/* ===== in ====== */
in vec2 in_position;

/* == uniforms == */
uniform float uniform0;
uniform float uniform1;

layout (std430) buffer attribute1 {
	float data;
};

/* ===== out ===== */
out float fs_uniform;

/* ------------- main --------------- */
void main() {
	
	fs_uniform = data;
	gl_Position = vec4(in_position, 0.0, 1.0);
}

