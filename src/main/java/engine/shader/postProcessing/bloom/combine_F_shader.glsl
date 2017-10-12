//FRAGMENT SHADER - Combine PostProcessing
#version 150

/*===== in ======*/
in vec2 textureCoords;

/*===== out =====*/
out vec4 out_Colour;

/*== uniforms ==*/
uniform sampler2D colorTexture;
uniform sampler2D highlightTexture2;
uniform sampler2D highlightTexture4;
uniform sampler2D highlightTexture8;

/*------------- main ---------------*/
void main(void) {

	vec4 sceneColour = texture(colorTexture, textureCoords);
	vec4 highlightColour2 = texture(highlightTexture2, textureCoords);
	vec4 highlightColour4 = texture(highlightTexture4, textureCoords);
	vec4 highlightColour8 = texture(highlightTexture8, textureCoords);
	out_Colour = sceneColour + highlightColour2*0.4 + highlightColour4 + highlightColour8;

}
