package net.wohlfart.pluto.shader;


public enum ShaderUniform {
    MODEL_VIEW_PROJECTION_MATRIX(ShaderProgram.MODEL_VIEW_PROJECTION_MATRIX),
    MODEL_MATRIX(ShaderProgram.MODEL_MATRIX),
    VIEW_MATRIX(ShaderProgram.VIEW_MATRIX),
    PROJECTION_MATRIX(ShaderProgram.PROJECTION_MATRIX);

    private final String uniformName;

    ShaderUniform(String uniformName) {
        this.uniformName = uniformName;
    }

    String getUniformName() {
        return uniformName;
    }

}
