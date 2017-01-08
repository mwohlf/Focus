package net.wohlfart.pluto.shader;


import android.opengl.GLES20;

import java.nio.Buffer;

public enum ShaderAttribute {

    POSITION_ATTRIBUTE(ShaderProgram.POSITION_ATTRIBUTE, 3) {    // a 3 float position vertex attribute

    },

    TEXCOORD_ATTRIBUTE(ShaderProgram.TEXCOORD_ATTRIBUTE, 2) {

    },

    NORMAL_ATTRIBUTE(ShaderProgram.NORMAL_ATTRIBUTE, 3) {

    },

    COLOR_ATTRIBUTE(ShaderProgram.COLOR_ATTRIBUTE, 4) {

    },

    ;

    private final String attributeName;

    private final int size;

    private final int type;

    private final boolean normalized;

    private final int stride;




    ShaderAttribute(String attributeName, int size) {
        this.attributeName = attributeName;
        this.size = size;
        this.type = GLES20.GL_FLOAT;
        this.normalized = false;
        this.stride = 0;
    }

    String getAttributeName() {
        return attributeName;
    }

    public void enable(ShaderProgram shaderProgram, Buffer buffer) {
        int handle = shaderProgram.lookup(this);
        GLES20.glEnableVertexAttribArray(handle);
        GLES20.glVertexAttribPointer(handle, size, type, normalized, stride, buffer);
    }

    public void disable(ShaderProgram shaderProgram) {
        int handle = shaderProgram.lookup(this);
        GLES20.glDisableVertexAttribArray(handle);
    }

}
