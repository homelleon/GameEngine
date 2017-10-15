#version 430

layout(triangles) in;
layout(line_strip, max_vertices = 4) out;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {

	mat4 projectionViewMatrix = projectionMatrix * viewMatrix;
	for(int i=0; i < gl_in.length(); ++i) {

		vec4 position = gl_in[i].gl_Position;
		gl_Position = projectionViewMatrix * position;
		EmitVertex();
	}

	vec4 position = gl_in[0].gl_Position;
	gl_Position = projectionViewMatrix * position;
	EmitVertex();

	EndPrimitive();

}
