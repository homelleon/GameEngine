//FRAGMENT SHADER - GUI Texture
#version 140

/*===== in ======*/
in vec2 textureCoords;

/*===== out =====*/
out vec4 out_Color;

/*== uniforms ==*/
uniform sampler2D guiTexture;

/*------------- main ---------------*/
void main(void) {

	out_Color = texture(guiTexture, textureCoords);

}
