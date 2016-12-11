package net.wohlfart.pluto.graph;

import net.wohlfart.pluto.shader.ShaderProgram;

public interface SceneGraphContext {

    // return the current shader
    ShaderProgram getShader();

    void setShader(ShaderProgram shaderProgram);

}
