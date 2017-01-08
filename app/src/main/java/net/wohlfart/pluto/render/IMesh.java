package net.wohlfart.pluto.render;


import net.wohlfart.pluto.shader.ShaderProgram;

public interface IMesh {

    void bind(ShaderProgram shader);

    void render();

}
