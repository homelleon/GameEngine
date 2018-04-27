// FRAGMENT SHADER - Contrast PostProcessing
/* ===== in ====== */
in vec2 textureCoords;

/* ===== out ===== */
out vec4 out_Colour;

/* == uniforms == */
uniform sampler2D colorMap;

uniform float contrast;

/* ------------- main --------------- */
void main(void) {

	out_Colour = texture(colorMap, textureCoords);
	out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;

}
