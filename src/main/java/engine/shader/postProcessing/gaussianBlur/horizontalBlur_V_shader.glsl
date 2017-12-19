//VERTEX SHADER - Horizontal Blur PostProcessing
#version 150

/*===== in ======*/
in vec2 in_position;

/*===== out =====*/
out vec2 blurTextureCoords[11]; 

/*== uniforms ==*/
uniform float targetWidth;

/*------------- main ---------------*/
void main(void) {

	gl_Position = vec4(in_position, 0.0, 1.0);
	vec2 centerTexCoords = in_position * 0.5 + 0.5;
	float pixelSize = 1.0 / targetWidth;
	
	for(int i = -5; i <= 5; i++) {
		blurTextureCoords[i + 5] = centerTexCoords + vec2(pixelSize * i, 0.0);
	}

}
