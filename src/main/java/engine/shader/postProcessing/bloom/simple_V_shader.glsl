//VERTEX SHADER - Simple PostProcessing
#version 150

/*===== in ======*/
in vec2 position;

/*===== out =====*/
out vec2 textureCoords;

/*------------- main ---------------*/
void main(void) {

	gl_Position = vec4(position, 0.0, 1.0);
	textureCoords = position * 0.5 + 0.5;
	
}
