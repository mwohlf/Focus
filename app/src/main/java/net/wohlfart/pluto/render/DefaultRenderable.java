package net.wohlfart.pluto.render;

import net.wohlfart.pluto.shader.ShaderProgram;

/**
 * the default IRenderable implementation consists of:
 * - exactly one ShaderProgram
 * - zero or more uniforms
 * - zero or more attribute
 * - zero or more meshes
 */
public class DefaultRenderable implements IRenderable {

    ShaderProgram shaderProgram;

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    @Override
    public void render() {
        shaderProgram.use();
    }

}
