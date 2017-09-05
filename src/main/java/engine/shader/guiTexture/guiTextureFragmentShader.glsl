//FRAGMENT SHADER - GUI Texture
#version 140

/*===== in ======*/
in vec2 textureCoords;

/*===== out =====*/
out vec4 out_Color;

/*== uniforms ==*/
uniform sampler2D guiTexture;
uniform bool isMixColored;
uniform vec3 mixColor;

/*------------- main ---------------*/
void main(void) {

	out_Color = texture(guiTexture, textureCoords);
	if(isMixColored) {
		out_Color.rgb = mix(out_Color.rgb, mixColor, 0.5);
	}

}
