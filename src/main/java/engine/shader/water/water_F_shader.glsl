// FRAGMENT SHADER - Water
#version 400 core

/* ===== in ====== */
in vec4 clipSpace;
in vec2 textureCoords;
in vec3 toCameraVector;
in vec3 fromLightVector;
in float visibility;

/* ===== out ===== */
out vec4 out_Color;
out vec4 out_BrightColor;

/* == uniforms == */
uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;
uniform sampler2D normalMap;
uniform sampler2D depthMap;
uniform vec3 lightColor;
uniform vec3 skyColor;

uniform float moveFactor;

uniform float waveStrength;

/* == constants == */
const float shineDamper = 20.0;
const float reflectivity = 0.4;

/* ------------- main --------------- */
void main(void) {

    vec2 ndc = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;
    vec2 refractTexCoords = vec2(ndc.x, ndc.y);
    vec2 reflectTexCoords = vec2(ndc.x, -ndc.y); 
    
    float near = 0.1;
    float far = 1000.0;
    float depth = texture(depthMap, refractTexCoords).r;
    float floorDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    
    depth = gl_FragCoord.z;
    float waterDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
    float waterDepth = floorDistance - waterDistance;
    
    vec2 distortedTexCoords = texture(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg * 0.1;
	distortedTexCoords = textureCoords + vec2(distortedTexCoords.x, distortedTexCoords.y + moveFactor);
	vec2 totalDistortion = (texture(dudvMap, distortedTexCoords).rg * 2.0 - 1.0) * waveStrength * clamp(waterDepth / 20.0, 0.0, 1.0);
    
    refractTexCoords += totalDistortion;
    refractTexCoords = clamp(refractTexCoords, 0.001, 0.999);
    
    reflectTexCoords += totalDistortion;
    reflectTexCoords.x = clamp(reflectTexCoords.x, 0.001, 0.999);
    reflectTexCoords.y = clamp(reflectTexCoords.y, -0.999, -0.001);
    
    vec4 reflectColour = texture(reflectionTexture, reflectTexCoords);
    vec4 refractColour = texture(refractionTexture, refractTexCoords);
    vec4 murkyWaterColour = vec4(0.4, 0.8, 0.6, 0.7);
    refractColour = mix(refractColour, murkyWaterColour, clamp(waterDepth / 60.0, 0.0, 1.0));
    
    vec4 normalMapColour = texture(normalMap, distortedTexCoords);
    vec3 normal = vec3(normalMapColour.r * 2.0 - 1.0, normalMapColour.b * 3.0, normalMapColour.g * 2.0 - 1.0);
    normal = normalize(normal);
    
    vec3 viewVector = normalize(toCameraVector);
    float refractiveFactor = dot(viewVector, normal);
    refractiveFactor = pow(refractiveFactor, 0.5); 
    refractiveFactor = clamp(refractiveFactor, 0.0, 1.0);
    
    vec3 reflectedLight = reflect(normalize(fromLightVector), normal);
	float specular = max(dot(reflectedLight, viewVector), 0.0);
	specular = pow(specular, shineDamper);
	vec3 specularHighlights = lightColor * specular * reflectivity * clamp(waterDepth/5.0, 0.0, 1.0);
	
	out_Color = mix(reflectColour, refractColour, refractiveFactor);
	out_Color = mix(out_Color, vec4(0.0, 0.3, 0.5, 1.0), 0.2) + vec4(specularHighlights, 0.0);
	out_Color = mix(out_Color, vec4(skyColor, 1.0), visibility);
    out_Color.a = clamp(waterDepth/5.0, 0.0, 1.0);
    out_BrightColor = vec4(0.0);
    
}
