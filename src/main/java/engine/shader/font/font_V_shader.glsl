// VERTEX SHADER - Font
#version 330

/* ===== in ====== */
in vec2 in_position;
in vec2 in_textureCoords;

/* ===== out ===== */
out vec2 pass_textureCoordinates;

/* == uniforms == */
uniform vec2 translation;

/* ------------- main --------------- */
void main(void) {
	
	vec2 transp_translation = vec2(translation.x, 1 - (translation.y + 1.0) / 2.0);
	gl_Position = vec4(in_position + transp_translation * vec2(2.0, -2.0), 0.0, 1.0);
	pass_textureCoordinates = in_textureCoords;

}
