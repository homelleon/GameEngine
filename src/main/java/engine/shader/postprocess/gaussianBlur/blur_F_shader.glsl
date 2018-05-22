//FRAGMENT SHADER - Blur PostProcessing
/*===== in ======*/
in vec2 blurTextureCoords[11];

/*===== out =====*/
out vec4 out_colour;

/*== uniforms ==*/
uniform sampler2D originalMap;
uniform float[11] sigmas;

/*------------- main ---------------*/
void main(void) {
	

	out_colour = vec4(0.0);
	for (int i = 0; i <= 10; i++) {
		out_colour += texture(originalMap, blurTextureCoords[i]) * sigmas[i];
	}

}
