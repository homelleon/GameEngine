//VERTEX SHADER - Skybox
#version 400

/*===== in ======*/
in vec3 in_position;

/*===== out =====*/
out vec3 textureCoords;

/*== uniforms ==*/
uniform mat4 Projection;
uniform mat4 View;

/*------------- main ---------------*/
void main(void) {
	
	gl_Position = Projection * View * vec4(in_position, 1.0); 
	textureCoords = in_position;
	
}
