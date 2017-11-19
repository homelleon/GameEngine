//FRAGMENT SHADER - Debug
#version 430 core

/*===== in ======*/
in float fs_uniform;
/*== uniforms ==*/
uniform float uniform0;
uniform float uniform1;

/*===== out =====*/
out vec4 out_colour;

/*------------- main ---------------*/
void main() {
	
	out_colour = vec4(fs_uniform, 0, 0, 1.0);
	
}