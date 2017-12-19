// VERTEX SHADER - Shadow
#version 150

/* ===== in ====== */
in vec3 in_position;
in vec2 in_textureCoords;

/* ===== out ===== */
out vec2 textureCoords;

/* == uniforms == */
uniform mat4 ModelViewProjection;

uniform float numberOfRows;
uniform vec2 offset;

/* ------------- main --------------- */
void main(void) {

	gl_Position = ModelViewProjection * vec4(in_position, 1.0);
	textureCoords = (in_textureCoords / numberOfRows) + offset;

}
