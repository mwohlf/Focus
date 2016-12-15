package net.wohlfart.pluto.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import net.wohlfart.pluto.Focus;
import net.wohlfart.pluto.shader.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * the default IRenderable implementation consists of:
 * - exactly one ShaderProgram
 * - zero or more uniforms
 * - zero or more attribute
 * - zero or more meshes
 */
public class Triangle implements IRenderable {

    private ShaderProgram shaderProgram;

    private float[] vertices;
    private short[] indices;

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    private float[] mtrxProjection = new float[16];
    private float[] mtrxView = new float[16];
    private float[] mtrxProjectionAndView = new float[16];

    int width;
    int height;


    public Triangle() {
        setupTriangle();
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public void setDimension(int width, int height) {
        Log.i(Focus.TAG, "<setDimension> width: " + width + " heigth: " + height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render() {
        shaderProgram.use();
        GLES20.glViewport(0, 0, width, height);

        // Clear our matrices
        for(int i=0;i<16;i++) {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, width, 0.0f, height, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f,
                                       0f, 0f, 0f,
                                       0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

        // Get handle to shape's transformation matrix
        int mtrxhandle = GLES20.glGetUniformLocation(shaderProgram.handle(), "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, mtrxProjectionAndView, 0);


        // get handle to vertex shader's vPosition member
        int mPositionHandle = GLES20.glGetAttribLocation(shaderProgram.handle(), "vPosition");

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
