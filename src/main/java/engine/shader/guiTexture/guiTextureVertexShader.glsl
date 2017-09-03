//VERTEX SHADER - GUI Texture
#version 140

/*===== in ======*/
in vec2 position;

/*===== out =====*/
out vec2 textureCoords;

/*== uniforms ==*/
uniform mat4 transformationMatrix;

/*------------- main ---------------*/
void main(void) {

	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
	textureCoords = vec2((position.x+1.0) / 2.0, 1 - (position.y+1.0) / 2.0);
	
}
