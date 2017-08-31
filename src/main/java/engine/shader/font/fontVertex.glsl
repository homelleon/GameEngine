#version 330

in vec2 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform vec2 translation;

void main(void) {
	
	vec2 transp_translation = vec2(translation.x, 1 - (translation.y+1.0)/2.0);
	gl_Position = vec4(position + transp_translation * vec2(2.0, -2.0), 0.0, 1.0);
	pass_textureCoords = textureCoords; 

}