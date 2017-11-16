//FRAGMENT SHADER - Bright Filter PostProcessing
#version 150

/*===== in ======*/
in vec2 textureCoords;

/*===== out =====*/
out vec4 out_Colour;

/*== uniforms ==*/
uniform sampler2D colourTexture;

/*------------- main ---------------*/
void main(void) {
	vec4 colour = texture(colourTexture, textureCoords);
	float brightness = (colour.r * 0.2126) + (colour.g * 0.7152) + (colour.b * 0.0722);
	if(brightness > 0.5) {
		out_Colour = colour;
	} else {
		out_Colour = vec4(0.0);
	}
}
