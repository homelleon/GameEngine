// FRAGMENT SHADER - Shadow
/* ===== in ====== */
in vec2 textureCoords;

/* ===== out ===== */
out vec4 out_Colour;

/* == uniforms == */
uniform sampler2D modelTexture;

/* ------------- main --------------- */
void main(void) {

	float alpha = texture(modelTexture, textureCoords).a;
	if (alpha < 0.5) {
		discard;
	}
	
	out_Colour = encodeFloat(gl_FragCoord.z);

}
