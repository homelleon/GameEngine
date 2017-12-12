// FRAGMENT SHADER - Bright Filter PostProcessing
#version 150

/* ===== in ====== */
in vec2 textureCoords;

/* ===== out ===== */
out vec4 out_Color;

/* == uniforms == */
uniform sampler2D colorMap;

/* ------------- main --------------- */
void main(void) {
	vec4 color = texture(colorMap, textureCoords);
	float brightness = (color.r * 0.2126) + (color.g * 0.7152) + (color.b * 0.0722);
	if(brightness > 0.5) {
		out_Color = color;
	} else {
		out_Color = vec4(0.0);
	}
}
