//FRAGMENT SHADER - Font
#version 330

/*===== in ======*/
in vec2 pass_textureCoordinates;

/*===== out =====*/
out vec4 out_Color;

/*== uniforms ==*/
uniform vec3 color;
uniform sampler2D fontAtlas;

uniform float width;
uniform float edge;

uniform float borderWidth;
uniform float borderEdge;

uniform vec2 offset;

uniform vec3 outlineColor;

/*------------- main ---------------*/
void main(void) {

	float distance = 1.0 - texture(fontAtlas, pass_textureCoordinates).a;
	float alpha = 1.0 - smoothstep(width, width + edge, distance);
	
	float distance2 = 1.0 - texture(fontAtlas, pass_textureCoordinates + offset).a;
	float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, distance2);

	float overallAlpha = alpha + (1.0 - alpha) * outlineAlpha;
	vec3 overallColor = mix(outlineColor, color, alpha  / overallAlpha);

	out_Color = vec4(overallColor, overallAlpha);

}
