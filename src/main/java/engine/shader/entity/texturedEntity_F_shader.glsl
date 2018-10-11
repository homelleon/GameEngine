// FRAGMENT SHADER - Entity
/* ===== in ====== */

// geometry
in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[LIGHT_MAX];
in vec3 toCameraVector;
in vec4 shadowCoords;
in vec3 reflectedVector;
in vec3 refractedVector;
/* factors */
in float fogVisibility;

/* ===== out ===== */
out vec4 out_Color;
out vec4 out_BrightColor;

/* == uniforms == */
// material
uniform sampler2D diffuseMap;
uniform sampler2D specularMap;
uniform sampler2D alphaMap;
uniform samplerCube enviroMap;
uniform sampler2D shadowMap;

// boolean flags
uniform float usesAlphaMap;
uniform float usesSpecularMap;
uniform bool isChosen;

// shadow variables
uniform float shadowMapSize;
uniform int shadowPCFCount;

// light and color
uniform vec3 lightColor[LIGHT_MAX];
uniform vec3 attenuation[LIGHT_MAX];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

// reflection and refraction
uniform float reflectiveFactor;
uniform float refractiveFactor;

/* ------------- main --------------- */
void main(void) {

	//transparent in diffuse texture
	vec4 textureColor = texture(diffuseMap, pass_textureCoordinates);
	if (textureColor.a < 0.5)
		discard;

	// transparent in alpha texture
	if (usesAlphaMap > 0.5) {
		vec4 alphaColor = texture(alphaMap, pass_textureCoordinates);
		if (alphaColor.r < 1)
			discard;
	}

	float totalTexels = (shadowPCFCount * 2.0 + 1.0) * (shadowPCFCount * 2.0 + 1.0);

	float texelSize = 1.0 / shadowMapSize;
    float total = 0.0;

    for (int x = -shadowPCFCount; x <= shadowPCFCount; x++) {
   		 for (int y = -shadowPCFCount; y <= shadowPCFCount; y++) {
   				 float objectNearestLight = decodeFloat(texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize));
   			 	 if (shadowCoords.z > objectNearestLight + SHADOW_BIAS)
   			 		 total += 1.0;
   		 }
    }

    total /= totalTexels;

    float lightFactor = 1.0 - (total * shadowCoords.w);

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec4 totalDiffuse = vec4(0.0, 0.0, 0.0, 1.0);
    vec4 totalSpecular = vec4(0.0, 0.0, 0.0, 1.0);

	for (int i = 0; i < LIGHT_MAX; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot1 = dot(unitNormal, unitLightVector);
		float brightness = max(nDot1, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor,0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse.rgb = totalDiffuse.rgb + (brightness * lightColor[i]) / attFactor;
		totalSpecular.rgb = totalSpecular.rgb + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	totalDiffuse.rgb = max(totalDiffuse.rgb * lightFactor, 0.4);

	out_BrightColor = vec4(0.0);
	if (usesSpecularMap > 0.5) {
		vec4 mapInfo = texture(specularMap, pass_textureCoordinates);
		totalSpecular *= mapInfo.r;
		if (mapInfo.g > 0.5) {
			out_BrightColor = textureColor + totalSpecular;
			totalDiffuse = vec4(1.0);
		}
	}

	vec4 reflectedColour = texture(enviroMap, reflectedVector);
	vec4 refractedColour = texture(enviroMap, refractedVector);

	vec4 totalColor = textureColor;

	if (isChosen)
		totalColor.r *= 3;

	totalColor = mix(totalColor, refractedColour, refractiveFactor);
	totalColor = mix(totalColor, reflectedColour, reflectiveFactor);

	totalColor = phongModelColor(totalColor, totalDiffuse, totalSpecular);

	totalColor = mix(vec4(skyColor, 1.0), totalColor, fogVisibility);

	out_Color = totalColor;

	
}
