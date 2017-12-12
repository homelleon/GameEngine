//VERTEX SHADER - Particle
#version 140

/*===== in ======*/
in vec2 in_position;

in mat4 in_ModelView;
in vec4 in_texOffsets;
in float in_blendFactor;

/*===== out =====*/
out vec2 textureCoords1;
out vec2 textureCoords2;
out float blend;

/*== uniforms ==*/
uniform mat4 Projection;
uniform float numberOfRows;

/*------------- main ---------------*/
void main(void) {

	vec2 textureCoords = in_position + vec2(0.5, 0.5);
	textureCoords.y = 1.0 - textureCoords.y;
	textureCoords /= numberOfRows;
	textureCoords1 = textureCoords + in_texOffsets.xy;
	textureCoords2 = textureCoords + in_texOffsets.zw;
	blend = in_blendFactor;
	
	gl_Position = Projection * in_ModelView * vec4(in_position, 0.0, 1.0);

}
