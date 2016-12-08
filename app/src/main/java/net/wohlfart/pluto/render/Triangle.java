package net.wohlfart.pluto.render;

import android.opengl.GLES20;

import net.wohlfart.pluto.shader.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by michael on 08.12.16.
 */

public class Triangle extends DefaultRenderable {

    private float[] vertices;
    private short[] indices;

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    public Triangle() {
        setupTriangle();
    }

    @Override
    public void render() {
        super.render();

        ShaderProgram shader = getShaderProgram();

        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES20.glGetAttribLocation(shader.handle(), "vPosition");

        // Enable generic vertex attribute array
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        // Draw the triangle
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }

    private void setupTriangle() {
        // We have to create the vertices of our triangle.
        vertices = new float[]
                {10.0f, 400f, 0.0f,
                        10.0f, 100f, 0.0f,
                        400f, 400f, 0.0f,
                };

        indices = new short[] {0, 1, 2}; // The order of vertexrendering.

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }

}
