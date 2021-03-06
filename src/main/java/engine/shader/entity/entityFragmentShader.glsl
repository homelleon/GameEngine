//FRAGMENT SHADER - Entity
#version 400 core

/*===== in ======*/

//geometry
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[10];
in vec3 toCameraVector;
in vec4 shadowCoords;
in vec3 reflectedVector;
in vec3 refractedVector;
/*factors*/
in float fogVisibility;

/*===== out =====*/
out vec4 out_Color;
out vec4 out_BrightColor;

/*== uniforms ==*/
uniform sampler2D textureSampler;
uniform sampler2D specularMap;
uniform samplerCube enviroMap;

//shadows
uniform float usesSpecularMap;
uniform sampler2D shadowMap;
uniform float shadowMapSize;
uniform int shadowPCFCount;

//light and colour
uniform vec3 lightColour[10];
uniform vec3 attenuation[10];
uniform int lightCount;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

//reflection and refraction
uniform float reflectiveFactor;
uniform float refractiveFactor;

uniform bool isChosen;

/*------------- main ---------------*/
void main(void) {

	float totalTexels = (shadowPCFCount * 2.0 + 1.0) * (shadowPCFCount * 2.0 + 1.0);

	float texelSize = 1.0 / shadowMapSize;
    float total = 0.0;

    for(int x=-shadowPCFCount; x<=shadowPCFCount; x++) {
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

	for(int i=0;i<lightCount;i++) {
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
		totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
		totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i])/attFactor;
	}
	totalDiffuse = max(totalDiffuse * lightFactor, 0.4);

	vec4 textureColour = texture(textureSampler,pass_textureCoords);
	if(textureColour.a < 0.5) {
		discard;
	}

	out_BrightColor = vec4(0.0);
	if(usesSpecularMap > 0.5) {
		vec4 mapInfo = texture(specularMap, pass_textureCoords);
		totalSpecular *= mapInfo.r;
		if(mapInfo.g > 0.5) {
			out_BrightColor = textureColour + vec4(totalSpecular,1.0);
			totalDiffuse = vec3(1.0);
		}
	}

	vec4 reflectedColour = texture(enviroMap, reflectedVector);
	vec4 refractedColour = texture(enviroMap, refractedVector);

	out_Color = textureColour;

	out_Color = mix(out_Color, refractedColour, refractiveFactor);
	out_Color = mix(out_Color, reflectedColour, reflectiveFactor);

	out_Color = vec4(totalDiffuse,1.0) * out_Color + vec4(totalSpecular,1.0);

	out_Color = mix(vec4(skyColour,1.0), out_Color, fogVisibility);


	if(isChosen) {
		out_Color.r *= 4;
	}
	
}
