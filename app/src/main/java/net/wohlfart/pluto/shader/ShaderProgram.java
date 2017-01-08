package net.wohlfart.pluto.shader;

import android.opengl.GLES20;

import java.util.Arrays;

public class ShaderProgram {

    private static final int INVALID_HANDLE = -1;

    static final String POSITION_ATTRIBUTE = "Position";
    static final String TEXCOORD_ATTRIBUTE = "TexCoord";
    static final String NORMAL_ATTRIBUTE = "Normal";
    static final String COLOR_ATTRIBUTE = "Color";

    static final String MODEL_VIEW_PROJECTION_MATRIX = "MVPMatrix";
    static final String MODEL_MATRIX = "ModelMatrix";
    static final String VIEW_MATRIX = "ViewMatrix";
    static final String PROJECTION_MATRIX = "ProjectionMatrix";

    private final String name;

    private final int shaderHandle;


    // handles for the uniforms of this shader
    private final int[] uniformHandles;

    // handles for the attributes of this shader
    private final int[] attributeHandles;


    /* package private, use loader */
    ShaderProgram(int shaderHandle, String name) {
        this.shaderHandle = shaderHandle;
        this.name = name;
        this.uniformHandles = new int[ShaderUniform.values().length];
        this.attributeHandles = new int[ShaderAttribute.values().length];
        Arrays.fill(uniformHandles, INVALID_HANDLE);
        Arrays.fill(attributeHandles, INVALID_HANDLE);
    }

    public void use() {
        GLES20.glUseProgram(shaderHandle);
    }

    public void attach(ShaderUniform shaderUniform) {
        uniformHandles[shaderUniform.ordinal()] = GLES20.glGetUniformLocation(shaderHandle, shaderUniform.getUniformName());
    }

    public void setMatrix(ShaderUniform modelMatrix, float[] matrix) {
        GLES20.glUniformMatrix4fv(uniformHandles[modelMatrix.ordinal()], 1, false, matrix, 0);
    }


    public int lookup(ShaderAttribute shaderAttribute) {
        int handle = attributeHandles[shaderAttribute.ordinal()];
        if (handle == INVALID_HANDLE) {
            handle = attributeHandles[shaderAttribute.ordinal()] = GLES20.glGetAttribLocation(shaderHandle, shaderAttribute.getAttributeName());
        }
        return handle;
    }

}
