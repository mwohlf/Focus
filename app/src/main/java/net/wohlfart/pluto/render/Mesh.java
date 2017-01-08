package net.wohlfart.pluto.render;


import android.opengl.GLES20;

import net.wohlfart.pluto.shader.ShaderAttribute;
import net.wohlfart.pluto.shader.ShaderProgram;

import java.nio.Buffer;
import java.util.EnumSet;
import java.util.Iterator;

public class Mesh implements IMesh {

    private final Buffer indexBuffer;
    private final Buffer vertexBuffer;

    private final int indexCount;
    private final ShaderProgram shaderProgram;

    private final int type;

    private final int mode;
    private final EnumSet<ShaderAttribute> attributes;

    public Mesh(
            ShaderProgram shaderProgram,
            int indexCount,
            Buffer vertexBuffer,
            Buffer indexBuffer,
            int type,
            int mode,
            EnumSet<ShaderAttribute> attributes) {

        this.shaderProgram = shaderProgram;
        this.indexCount = indexCount;
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        //  GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT, or GL_UNSIGNED_INT
        this.type = type;
        //  GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_LINE_STRIP_ADJACENCY, GL_LINES_ADJACENCY,
        //  GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, GL_TRIANGLES, GL_TRIANGLE_STRIP_ADJACENCY, GL_TRIANGLES_ADJACENCY and GL_PATCHES
        this.mode = mode;
        this.attributes = attributes;

    }

    @Override
    public void bind(ShaderProgram shader) {

    }

    @Override
    public void render() {
        Iterator<ShaderAttribute> iterator = attributes.iterator();
        while (iterator.hasNext()) {
            iterator.next().enable(shaderProgram, vertexBuffer);
        }

        // https://www.opengl.org/sdk/docs/man/html/glDrawElements.xhtml
        GLES20.glDrawElements(mode, indexCount, type, indexBuffer);


        iterator = attributes.iterator();
        while (iterator.hasNext()) {
            iterator.next().disable(shaderProgram);
        }
    }

}
