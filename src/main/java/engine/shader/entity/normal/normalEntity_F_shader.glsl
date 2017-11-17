//FRAGMENT SHADER - NM Entity
#version 400 core

#define LIGHT_MAX 10

/*===== in ======*/
//geometry
in vec2 pass_textureCoordinates;
in vec3 toLightVector[LIGHT_MAX];
in vec3 toCameraVector;
in vec4 shadowCoords;

//factors
in float visibility;

/*===== out =====*/
out vec4 out_Color;
out vec4 out_BrightColor;

/*=== uniforms ==*/
//textures
uniform sampler2D diffuseMap;
uniform sampler2D normalMap;
uniform sampler2D shadowMap;
uniform sampler2D specularMap;
uniform sampler2D alphaMap;

//light and colour
uniform vec3 lightColor[LIGHT_MAX];
uniform vec3 attenuation[LIGHT_MAX];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;
uniform int lightCount;
uniform float usesSpecularMap;
uniform float usesAlphaMap;

//shadows
uniform float shadowMapSize;
uniform int shadowPCFCount;

//control
uniform bool isChosen;

/*------------- main ---------------*/
void main(void) {

	//transparent in diffuse texture
	vec4 textureColour = texture(diffuseMap, pass_textureCoordinates);
	if(textureColour.a < 0.5) {
		discard;
	}

	//transparent in alpha texture
	if(usesAlphaMap > 0.5) {
		vec4 alphaColour = texture(alphaMap, pass_textureCoordinates);
		if(alphaColour.r < 1) {
			discard;
		}
	}

	float totalTexels = (shadowPCFCount * 2.0 + 1.0) * (shadowPCFCount * 2.0 + 1.0);

    float texelSize = 1.0 / shadowMapSize;
    float total = 0.0;
   
    for(int x = -shadowPCFCount; x <= shadowPCFCount; x++) {
   		for(int y = -shadowPCFCount; y <= shadowPCFCount; y++) {
			float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
			if(shadowCoords.z > objectNearestLight + 0.002) {
				total += 1.0;
			}
   		}
   }
   
   total /= totalTexels;

   float lightFactor = 1.0 - (total * shadowCoords.w);

   vec4 normalMapValue = 2.0 * texture(normalMap, pass_textureCoordinates) - 1.0;

   vec3 unitNormal = normalize(normalMapValue.rgb);
   vec3 unitVectorToCamera = normalize(toCameraVector);
	
   vec3 totalDiffuse = vec3(0.0);
   vec3 totalSpecular = vec3(0.0);
   vec4 mapInfo = vec4(0.0);
   
	if(usesSpecularMap > 0.5) {
		mapInfo = normalize(texture(specularMap, pass_textureCoordinates));
		if(mapInfo.g > 0.5) {
			mapInfo.r *= shineDamper;
		}
	}
   
   for(int i = 0; i < LIGHT_MAX; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);	
		float nDotl = dot(unitNormal, unitLightVector);
		float brightness = max(nDotl, 0.0);
		brightness += mapInfo.r;
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
	}
	totalDiffuse = max(totalDiffuse * lightFactor, 0.4);

	out_BrightColor = vec4(0.0);
	
	if(usesSpecularMap > 0.5) {
		if(mapInfo.g > 0.5) {
			out_BrightColor = textureColour + vec4(totalSpecular, 1.0);
		}
	}

	out_Color = vec4(totalDiffuse, 1.0) * textureColour + vec4(totalSpecular, 1.0);
	out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
	
	if(isChosen) {
		out_Color.r *= 3;
	}

}
