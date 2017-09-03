//FRAGMENT SHADER - Skybox
#version 400

/*===== in ======*/
in vec3 textureCoords;

/*===== out =====*/
out vec4 out_Color;

/*== uniforms ==*/
uniform samplerCube cubeMap;
uniform samplerCube cubeMap2;
uniform float blendFactor;
uniform vec3 fogColour;

/*== constants ==*/
const float lowerLimit = 0.0;
const float upperLimit = 30.0;

/*------------- main ---------------*/
void main(void) {
    vec4 texture1 = texture(cubeMap, textureCoords);
    vec4 texture2 = texture(cubeMap2, textureCoords);
    vec4 finalColour = mix(texture1, texture2, blendFactor);
    
    float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);
    out_Color = mix(vec4(fogColour, 1.0), finalColour, factor);
    
}
