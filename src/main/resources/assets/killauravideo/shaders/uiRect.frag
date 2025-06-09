#version 330 core

in vec2 fragCoord;

out vec4 FragColor;

uniform vec2 uTopLeft;
uniform vec2 uBottomRight;

uniform float uRounding = 10.0;
uniform float uBordering = 2.0;

uniform vec4 uBaseColor;
uniform vec4 uBorderColor;

void main() {
    vec2 corners[4] = vec2[](
        uTopLeft,
        vec2(uTopLeft.x, uBottomRight.y),
        vec2(uBottomRight.x, uTopLeft.y),
        uBottomRight
    );

    vec2 innerCorners[4] = vec2[](
        uTopLeft + vec2(uRounding),
        vec2(uTopLeft.x, uBottomRight.y) + vec2(uRounding, -uRounding),
        vec2(uBottomRight.x, uTopLeft.y) + vec2(-uRounding, uRounding),
        uBottomRight - vec2(uRounding)
    );

    bool isBorder = false;

    for(int i = 0; i < 4; ++i) {
        if(abs(fragCoord.x - corners[i].x) <= uRounding && abs(fragCoord.y - corners[i].y) <= uRounding) {
            float dist = distance(innerCorners[i], fragCoord);
            if(dist > uRounding) {
                discard;
            }
            else if(dist > uRounding - uBordering) {
                isBorder = true;
                break;
            }
        } else if(abs(fragCoord.x - corners[i].x) <= uBordering || abs(fragCoord.y - corners[i].y) <= uBordering) {
            isBorder = true;
        }
    }

    if(isBorder)
        FragColor = uBorderColor;
    else
        FragColor = uBaseColor;
}