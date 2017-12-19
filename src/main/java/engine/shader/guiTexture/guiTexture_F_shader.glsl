// FRAGMENT SHADER - GUI Texture
#version 140

/* ===== in ====== */
in vec2 textureCoords;

/* ===== out ===== */
out vec4 out_Color;

/* == uniforms == */
// material
uniform sampler2D guiMap;

// color
uniform vec3 mixColor;

// boolean flag
uniform bool isMixColored;

/* ------------- main --------------- */
void main(void) {

	out_Color = texture(guiMap, textureCoords);
	if(isMixColored) {
		out_Color.rgb = mix(out_Color.rgb, mixColor, 0.5);
	}

}
