// VERTEX SHADER - Contrast PostProcessing
#version 140

/* ===== in ====== */
in vec2 in_position;

/* ===== out ===== */
out vec2 textureCoords;

/* ------------- main --------------- */
void main(void) {

	gl_Position = vec4(in_position, 0.0, 1.0);
	textureCoords = in_position * 0.5 + 0.5;
	
}
