// FRAGMENT SHADER - Font
/* ===== in ====== */
in vec2 pass_textureCoordinates;

/* ===== out ===== */
out vec4 out_Color;

/* == uniforms == */
// material
uniform sampler2D fontMap;

// color
uniform vec3 color;
uniform vec3 outlineColor;

// size
uniform float width;
uniform float edge;
// border
uniform float borderWidth;
uniform float borderEdge;

// material variables
uniform vec2 offset;

/* ------------- main --------------- */
void main(void) {

	float distance = 1.0 - texture(fontMap, pass_textureCoordinates).a;
	float alpha = 1.0 - smoothstep(width, width + edge, distance);
	
	float distance2 = 1.0 - texture(fontMap, pass_textureCoordinates + offset).a;
	float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, distance2);

	float overallAlpha = alpha + (1.0 - alpha) * outlineAlpha;
	vec3 overallColor = mix(outlineColor, color, alpha  / overallAlpha);

	out_Color = vec4(overallColor, overallAlpha);

}
