// FRAGMENT SHADER - Shadow
/* ===== in ====== */
in vec2 textureCoords;
in vec3 worldPosition;

/* ===== out ===== */
out vec4 out_Colour;

/* == uniforms == */
uniform sampler2D modelTexture;
uniform float depthRange;

/* ------------- main --------------- */
void main(void) {

	float linearDepth = length(worldPosition) * (1.0 / depthRange);

	float alpha = texture(modelTexture, textureCoords).a;
	if (alpha < 0.5)
		discard;
	
	out_Colour = encodeFloat(linearDepth);

}
