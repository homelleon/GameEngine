//FRAGMENT SHADER - Terrain
#version 400 core

/*===== in ======*/
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[10];
in vec3 toCameraVector;
in float visibility;
in vec4 shadowCoords;

/*===== out =====*/
out vec4 out_Color;
out vec4 out_BrightColor;

/*== uniforms ==*/
uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;
uniform sampler2D shadowMap;
uniform int lightCount;

uniform vec3 lightColour[10];
uniform vec3 attenuation[10];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

uniform float shadowMapSize;
uniform int shadowPCFCount;

/*------------- main ---------------*/
void main(void) {

   float totalTexels = (shadowPCFCount * 2.0 + 1.0) * (shadowPCFCount * 2.0 + 1.0);

   float texelSize = 1.0 / shadowMapSize;
   float total = 0.0;
   
   for(int x=-shadowPCFCount; x<=shadowPCFCount; x++) {
   		for(int y=-shadowPCFCount; y<=shadowPCFCount; y++) {
   				float objectNearestLight = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
   				if(shadowCoords.z > objectNearestLight) {
   					total += 1.0;
   				}
   		}
   }
   
   total /= totalTexels;

   float lightFactor = 1.0 - (total * shadowCoords.w);
  	

   vec4 blendMapColour = texture(blendMap, pass_textureCoords);
   
   float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
   vec2 tiledCoords = pass_textureCoords * 1000.0;
   vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
   vec4 rTextureColour = texture(rTexture,tiledCoords) * blendMapColour.r;
   vec4 gTextureColour = texture(gTexture,tiledCoords) * blendMapColour.g;
   vec4 bTextureColour = texture(bTexture,tiledCoords) * blendMapColour.b;

   vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;
   
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
     totalDiffuse = totalDiffuse + (brightness * lightColour[i])/attFactor;
     vec3 lightDirection = -unitLightVector;
     vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
     float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
     specularFactor = max(specularFactor, 0.0);
     float dampedFactor = pow(specularFactor,shineDamper);
     totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i]) / attFactor;
   }
   totalDiffuse = max(totalDiffuse * lightFactor, 0.4);
   
   out_Color = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpecular,1.0);
   out_Color = mix(vec4(skyColour, 1.0), out_Color, visibility);
   out_BrightColor = vec4(0.0);
   
}
