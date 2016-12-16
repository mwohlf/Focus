
attribute vec4 Position;

uniform mat4 ProjectionMatrix;
uniform mat4 ViewMatrix;
uniform mat4 ModelMatrix;



void main(){

     gl_Position = ProjectionMatrix * ViewMatrix * ModelMatrix * Position;

}
