#version 150 core

in vec4 inPosition;
in vec4 inColor;

out vec4 passColor;

void main(void) {
	gl_Position = inPosition;
	passColor = inColor;
}