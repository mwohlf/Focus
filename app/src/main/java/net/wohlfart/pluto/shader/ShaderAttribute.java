package net.wohlfart.pluto.shader;


import android.opengl.GLES20;

public class ShaderAttribute {

    private final String name;

    public ShaderAttribute(String name) {
        this.name = name;
    }

    public int bind(ShaderProgram shaderProgram) {
        // get handle to vertex shader's vPosition member
        int handle = GLES20.glGetAttribLocation(shaderProgram.handle(), "vPosition");
        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray(handle);
        return handle;
    }

    public void unbind(int handle) {
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(handle);
    }
}
