package net.wohlfart.pluto.shader;


import android.opengl.GLES20;

import static android.R.attr.handle;

public class ShaderUniform {

    private final String name;

    public ShaderUniform(String name) {
        this.name = name;
    }

    public int bind(ShaderProgram shaderProgram) {
        int handle = GLES20.glGetUniformLocation(shaderProgram.handle(), "uMVPMatrix");

        return handle;
    }

    public void unbind(ShaderProgram shaderProgram) {

    }
}
