// VERTEX SHADER - Boundings
#version 400 core

/* ===== in ====== */
in vec3 in_position;

/* == uniforms == */
uniform mat4 Transformation;
uniform mat4 Projection;
uniform mat4 View;

/* ------------- main --------------- */
void main(void) {

   vec4 worldPosition = Transformation * vec4(in_position, 1.0);
   vec4 positionRelativeToCam = View * worldPosition;
   gl_Position = Projection * positionRelativeToCam; // no special transformations
   
}
