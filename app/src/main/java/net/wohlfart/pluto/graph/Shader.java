package net.wohlfart.pluto.graph;

import net.wohlfart.pluto.shader.ShaderProgram;

/**
 * Created by michael on 11.12.16.
 */

public class Shader implements SceneGraphNode {

    final ShaderProgram shaderProgram;

    public Shader(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    @Override
    public void render(SceneGraphContext context) {
        context.setShader(shaderProgram);
    }

}
