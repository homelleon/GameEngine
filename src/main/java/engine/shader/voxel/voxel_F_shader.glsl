// FRAGMENT SHADER - Voxel
/* ===== in ====== */
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[10];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;

/* ===== out ===== */
out vec4 out_Color;
out vec4 out_BrightColor;

/* == uniforms == */
uniform sampler2D diffuseMap;
uniform sampler2D specularMap;
uniform float usesSpecularMap;
uniform sampler2D shadowMap;
uniform vec3 lightColor[10];
uniform vec3 attenuation[10];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
uniform int lightCount;

uniform float shadowMapSize;
uniform int shadowPCFCount;

/* ------------- main --------------- */
void main(void) {

	vec4 textureColor = texture(diffuseMap, pass_textureCoords);
	if (textureColor.a < 0.5) {
		discard;
	}

	float totalTexels = (shadowPCFCount * 2.0 + 1.0) * (shadowPCFCount * 2.0 + 1.0);

	float texelSize = 1.0 / shadowMapSize;
    float total = 0.0;
   
    for (int x = -shadowPCFCount; x <= shadowPCFCount; x++) {
   		 for (int y = -shadowPCFCount; y <= shadowPCFCount; y++) {
   				 float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
   			 	 if (shadowCoords.z > objectNearestLight + 0.001) {
   			 		 total += 1.0;
   			 	 }
   		 }
    }
   
    total /= totalTexels;

    float lightFactor = 1.0 - (total * shadowCoords.w);
   
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);
   
    vec4 totalDiffuse = vec4(0.0, 0.0, 0.0, 1.0);
    vec4 totalSpecular = vec4(0.0, 0.0, 0.0, 1.0);
   
	for (int i = 0; i < lightCount; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot1 = dot(unitNormal, unitLightVector);
		float brightness = max(nDot1, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor,shineDamper);
		totalDiffuse.rgb = totalDiffuse.rgb + (brightness * lightColor[i]) / attFactor;
		totalSpecular.rgb = totalSpecular.rgb + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	totalDiffuse.rgb = max(totalDiffuse.rgb * lightFactor, 0.4);
   
	out_BrightColor = vec4(0.0);   
	if (usesSpecularMap > 0.5) {
		vec4 mapInfo = texture(specularMap, pass_textureCoords);
		totalSpecular.rgb *= mapInfo.r;
		if (mapInfo.g > 0.5) {
			out_BrightColor = textureColor + totalSpecular;
			totalDiffuse = vec4(1.0);
		}
	}

	out_Color = phongModelColor(textureColor, totalDiffuse, totalSpecular);
   
	out_Color = mix(vec4(skyColor,1.0), out_Color, visibility);
	
}
