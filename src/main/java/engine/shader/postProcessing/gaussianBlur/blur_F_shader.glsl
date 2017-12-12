//FRAGMENT SHADER - Blur PostProcessing
#version 150

/*===== in ======*/
in vec2 blurTextureCoords[11];

/*===== out =====*/
out vec4 out_colour;

/*== uniforms ==*/
uniform sampler2D originalMap;

/*------------- main ---------------*/
void main(void) {
	
	out_colour = vec4(0.0);
	out_colour += texture(originalMap, blurTextureCoords[0]) * 0.0093;
    out_colour += texture(originalMap, blurTextureCoords[1]) * 0.028002;
    out_colour += texture(originalMap, blurTextureCoords[2]) * 0.065984;
    out_colour += texture(originalMap, blurTextureCoords[3]) * 0.121703;
    out_colour += texture(originalMap, blurTextureCoords[4]) * 0.175713;
    out_colour += texture(originalMap, blurTextureCoords[5]) * 0.198596;
    out_colour += texture(originalMap, blurTextureCoords[6]) * 0.175713;
    out_colour += texture(originalMap, blurTextureCoords[7]) * 0.121703;
    out_colour += texture(originalMap, blurTextureCoords[8]) * 0.065984;
    out_colour += texture(originalMap, blurTextureCoords[9]) * 0.028002;
    out_colour += texture(originalMap, blurTextureCoords[10]) * 0.0093;

}
