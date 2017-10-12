//VERTEX SHADER - Boundings
#version 400 core

/*===== in ======*/
in vec3 position;

/*== uniforms ==*/
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

/*------------- main ---------------*/
void main(void) {

   vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
   vec4 positionRelativeToCam = viewMatrix * worldPosition;
   gl_Position = projectionMatrix * positionRelativeToCam; //no special transformations
   
}
