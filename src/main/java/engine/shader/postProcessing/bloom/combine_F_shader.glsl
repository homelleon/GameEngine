// FRAGMENT SHADER - Combine PostProcessing
#version 150

/* ===== in ====== */
in vec2 textureCoords;

/* ===== out ===== */
out vec4 out_Color;

/* == uniforms == */
uniform sampler2D colorMap;
uniform sampler2D highlightMap2;
uniform sampler2D highlightMap4;
uniform sampler2D highlightMap8;

/* ------------- main --------------- */
void main(void) {

	vec4 sceneColor = texture(colorMap, textureCoords);
	vec4 highlightColor2 = texture(highlightMap2, textureCoords);
	vec4 highlightColor4 = texture(highlightMap4, textureCoords);
	vec4 highlightColor8 = texture(highlightMap8, textureCoords);
	out_Color = sceneColor + highlightColor2 * 0.4 + highlightColor4 + highlightColor8;

}
