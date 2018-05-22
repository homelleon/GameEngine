// VERTEX SHADER - Shadow
/* ===== in ====== */
in vec3 in_position;
in vec2 in_textureCoords;

/* ===== out ===== */
out vec2 textureCoords;
out vec3 worldPosition;

/* == uniforms == */
uniform mat4 ModelViewProjection;
uniform mat4 Model;

uniform float numberOfRows;
uniform vec2 offset;

/* ------------- main --------------- */
void main(void) {

	gl_Position = ModelViewProjection * vec4(in_position, 1.0);

	worldPosition = (Model * vec4(in_position, 1.0)).xyz;

	textureCoords = (in_textureCoords / numberOfRows) + offset;

}
