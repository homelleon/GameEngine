//FRAGMENT SHADER - Contrast PostProcessing
#version 140

/*===== in ======*/
in vec2 textureCoords;

/*===== out =====*/
out vec4 out_Colour;

/*== uniforms ==*/
uniform sampler2D colourTexture;

uniform float contrast;

/*------------- main ---------------*/
void main(void) {

	out_Colour = texture(colourTexture, textureCoords);
	out_Colour.rgb = (out_Colour.rgb - 0.5) * (1.0 + contrast) + 0.5;

}
