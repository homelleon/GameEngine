// VERTEX SHADER - GUI Texture
#version 140

/* ===== in ====== */
in vec2 in_position;

/* ===== out ===== */
out vec2 textureCoords;

/* == uniforms == */
uniform mat4 Transformation;

/* ------------- main --------------- */
void main(void) {

	gl_Position = Transformation * vec4(in_position, 0.0, 1.0);
	textureCoords = vec2((in_position.x + 1.0) / 2.0, 1 - (in_position.y + 1.0) / 2.0);
	
}
