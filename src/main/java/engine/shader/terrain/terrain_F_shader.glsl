//FRAGMENT SHADER - Terrain
#version 430 core

/*===== in ======*/
in vec2 fs_textureCoords;
in vec3 fs_surfaceNormal;
in vec3 fs_toLightVector[10];
in vec3 fs_toCameraVector;
in float fs_visibility;
in vec4 fs_shadowCoords;

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

uniform vec3 lightColor[10];
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
   				float objectNearestLight = texture(shadowMap, fs_shadowCoords.xy + vec2(x, y) * texelSize).r;
   				if(fs_shadowCoords.z > objectNearestLight) {
   					total += 1.0;
   				}
   		}
   }
   
   total /= totalTexels;

   float lightFactor = 1.0 - (total * fs_shadowCoords.w);
  	

   vec4 blendMapColour = texture(blendMap, fs_textureCoords);
   
   float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
   vec2 tiledCoords = fs_textureCoords;
   vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
   vec4 rTextureColour = texture(rTexture,tiledCoords) * blendMapColour.r;
   vec4 gTextureColour = texture(gTexture,tiledCoords) * blendMapColour.g;
   vec4 bTextureColour = texture(bTexture,tiledCoords) * blendMapColour.b;

   vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;

   vec3 unitNormal = normalize(fs_surfaceNormal);
   vec3 unitVectorToCamera = normalize(fs_toCameraVector);
   
   vec3 totalDiffuse = vec3(0.0);
   vec3 totalSpecular = vec3(0.0);
   
   for(int i=0;i<lightCount;i++) {
     float distance = length(fs_toLightVector[i]);
     float attFactor = attenuation[1].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
     vec3 unitLightVector = normalize(fs_toLightVector[i]);
     float nDot1 = dot(unitNormal,unitLightVector);
     float brightness = max(nDot1, 0.0);
     totalDiffuse = totalDiffuse + (brightness * lightColor[i])/attFactor;
     vec3 lightDirection = -unitLightVector;
     vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
     float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
     specularFactor = max(specularFactor, 0.0);
     float dampedFactor = pow(specularFactor,shineDamper);
     totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attFactor;
   }
   totalDiffuse = max(totalDiffuse * lightFactor, 0.4);
   
   out_Color = vec4(totalDiffuse, 1.0) * totalColour + vec4(totalSpecular,1.0);
   out_Color = mix(vec4(skyColour, 1.0), out_Color, fs_visibility);

   out_BrightColor = vec4(0.0);
   
}
