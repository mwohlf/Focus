package net.wohlfart.pluto.shader;

import android.opengl.GLES20;

public class ShaderProgram {

    public static final String POSITION_ATTRIBUTE = "Position";
    public static final String TEXCOORD_ATTRIBUTE = "TexCoord";
    public static final String NORMAL_ATTRIBUTE = "Normal";
    public static final String COLOR_ATTRIBUTE = "Color";

    public static final String MODEL_VIEW_PROJECTION_MATRIX = "MVPMatrix";
    public static final String MODEL_MATRIX = "ModelMatrix";
    public static final String VIEW_MATRIX = "ViewMatrix";
    public static final String PROJECTION_MATRIX = "ProjectionMatrix";

    private final String name;

    private final int shaderHandle;

    int[] uniformHandles = new int[ShaderUniform.values().length];
    int[] attributeHandles = new int[ShaderAttribute.values().length];


    /* package private, use loader */
    ShaderProgram(int shaderHandle, String name) {
        this.shaderHandle = shaderHandle;
        this.name = name;
    }

    public void use() {
        GLES20.glUseProgram(shaderHandle);
    }

    public void attach(ShaderUniform shaderUniform) {
        uniformHandles[shaderUniform.ordinal()] = GLES20.glGetUniformLocation(shaderHandle, shaderUniform.getUniformName());
    }

    public void attach(ShaderAttribute shaderAttribute) {
        attributeHandles[shaderAttribute.ordinal()] = GLES20.glGetAttribLocation(shaderHandle, shaderAttribute.getAttributeName());
    }

    public void setMatrix(ShaderUniform modelMatrix, float[] matrix) {
        GLES20.glUniformMatrix4fv(uniformHandles[modelMatrix.ordinal()], 1, false, matrix, 0);
    }

    @Deprecated
    public int handle() {
        return shaderHandle;
    }
}
