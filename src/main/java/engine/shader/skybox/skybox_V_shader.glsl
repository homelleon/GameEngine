//VERTEX SHADER - Skybox
#version 400

/*===== in ======*/
in vec3 position;

/*===== out =====*/
out vec3 textureCoords;

/*== uniforms ==*/
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

/*------------- main ---------------*/
void main(void) {
	
	gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0); 
	textureCoords = position;
	
}
