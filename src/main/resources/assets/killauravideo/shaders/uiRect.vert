#version 330 core

layout (location = 0) in vec3 aPos;

uniform mat4 uProj;
uniform mat4 uModel;

out vec2 fragCoord;

void main() {
    fragCoord = (uModel * vec4(aPos, 1.0)).xy;
    gl_Position = uProj * vec4(fragCoord, 0.0, 1.0);
}