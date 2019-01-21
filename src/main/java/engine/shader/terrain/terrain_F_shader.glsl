// FRAGMENT SHADER - Terrain
/* ===== in ====== */
in vec2 fs_textureCoords;
in vec3 fs_toLightVector[LIGHT_MAX];
in vec3 fs_toCameraVector;
in float fs_visibility;
in vec4 fs_shadowCoords;

/* ===== out ===== */
out vec4 out_Color;
out vec4 out_BrightColor;

/* == uniforms == */
// material
uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;
uniform sampler2D normalMap;

uniform int lod;

// light
uniform vec3 lightColor[LIGHT_MAX];
uniform vec3 attenuation[LIGHT_MAX];
uniform float shineDamper;
uniform float reflectivity;

// ambient variables
uniform vec3 skyColor;

// shadow variables
//uniform float shadowMapSize;
//uniform int shadowPCFCount;

const float lightSize = 0.2;

/* ------------- main --------------- */
void main(void) {

//   float objectNearestLight = decodeFloat(texture(shadowMap, fs_shadowCoords.xy));
//   float total = 1.0 - clamp(exp(-4.0 * (fs_shadowCoords.z - objectNearestLight)), 0.0, 1.0);
//   float total = 0.0;
//   if (objectNearestLight > fs_shadowCoords.z) {
//	   total = 1.0;
//   }
//   float lightFactor = total;
	float shadowFactor = 1.0 - fs_shadowCoords.w * fetchHardShadows(shadowMap, fs_shadowCoords, length(fs_toLightVector[0]));
	
	vec4 blendMapColor = texture(blendMap, fs_textureCoords);
	
	float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
	vec2 tiledCoords = fs_textureCoords * 500.0;
	vec4 backgroundTextureColor = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
	vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
	vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;
	
	vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;
	
	vec3 unitNormal =  normalize(2.0 * texture(normalMap, fs_textureCoords).rgb + vec3(1.0));
	vec3 unitVectorToCamera = normalize(fs_toCameraVector);
	
	vec4 totalDiffuse = vec4(0.0, 0.0, 0.0, 1.0);
	vec4 totalSpecular = vec4(0.0, 0.0, 0.0, 1.0);
	
	for (int i = 0; i < LIGHT_MAX; i++) {
		float distance = length(fs_toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(fs_toLightVector[i]);
		float nDot1 = dot(unitNormal, unitLightVector);
		float brightness = max(nDot1, 0.0);
		totalDiffuse.rgb = totalDiffuse.rgb + (brightness * lightColor[i]) / attFactor;
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalSpecular.rgb = totalSpecular.rgb + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	totalDiffuse.rgb = max(totalDiffuse.rgb * shadowFactor, 0.4);
	
	out_Color = phongModelColor(totalColor, totalDiffuse, totalSpecular);
	out_Color = mix(vec4(skyColor, 1.0), out_Color, fs_visibility);
	
	out_BrightColor = vec4(0.0);

}
