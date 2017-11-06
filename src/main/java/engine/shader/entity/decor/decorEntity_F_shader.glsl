//FRAGMENT SHADER - Entity
#version 400 core
#define LIGHT_MAX 10

/*===== in ======*/

//geometry
in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[LIGHT_MAX];
in vec3 toCameraVector;
in vec4 shadowCoords;

/*factors*/
in float fogVisibility;

/*===== out =====*/
out vec4 out_Color;
out vec4 out_BrightColor;

/*== uniforms ==*/
uniform sampler2D diffuseMap;
uniform sampler2D specularMap;

//shadows
uniform float usesSpecularMap;
uniform sampler2D shadowMap;
uniform float shadowMapSize;
uniform int shadowPCFCount;

//light and colour
uniform vec3 lightColor[LIGHT_MAX];
uniform vec3 attenuation[LIGHT_MAX];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

uniform bool isChosen;

/*------------- main ---------------*/
void main(void) {

	//transparent in diffuse texture
	vec4 textureColour = texture(diffuseMap, pass_textureCoordinates);
	if(textureColour.a < 0.5) {
		discard;
	}

	float totalTexels = (shadowPCFCount * 2.0 + 1.0) * (shadowPCFCount * 2.0 + 1.0);

	float texelSize = 1.0 / shadowMapSize;
    float total = 0.0;

    for(int x = -shadowPCFCount; x <= shadowPCFCount; x++) {
   		 for(int y=-shadowPCFCount; y<=shadowPCFCount; y++) {
   				 float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
   			 	 if(shadowCoords.z > objectNearestLight + 0.001) {
   			 		 total += 1.0;
   			 	 }
   		 }
    }

    total /= totalTexels;

    float lightFactor = 1.0 - (total * shadowCoords.w);

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

	for(int i = 0; i < LIGHT_MAX; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[1].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
		vec3 unitLightVector = normalize(toLightVector[i]);
		float nDot1 = dot(unitNormal,unitLightVector);
		float brightness = max(nDot1, 0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor,0.0);
		float dampedFactor = pow(specularFactor,shineDamper);
		totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i])/attFactor;
	}
	totalDiffuse = max(totalDiffuse * lightFactor, 0.4);

	out_BrightColor = vec4(0.0);
	if(usesSpecularMap > 0.5) {
		vec4 mapInfo = texture(specularMap, pass_textureCoordinates);
		totalSpecular *= mapInfo.r;
		if(mapInfo.g > 0.5) {
			out_BrightColor = textureColour + vec4(totalSpecular,1.0);
			totalDiffuse = vec3(1.0);
		}
	}

	out_Color = textureColour;

	out_Color = vec4(totalDiffuse,1.0) * out_Color + vec4(totalSpecular,1.0);

	out_Color = mix(vec4(skyColor,1.0), out_Color, fogVisibility);


	if(isChosen) {
		out_Color.r *= 3;
	}
	
}