package net.wohlfart.pluto.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.google.common.base.Function;

import net.wohlfart.pluto.Focus;
import net.wohlfart.pluto.shader.ShaderProgram;
import net.wohlfart.pluto.shader.ShaderUniform;

import java.nio.Buffer;
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

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] modelMatrix = new float[16];

    private int width;
    private int height;

    private Mesh mesh;


    public Triangle() {
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;

        this.shaderProgram.attach(ShaderUniform.PROJECTION_MATRIX);
        this.shaderProgram.attach(ShaderUniform.VIEW_MATRIX);
        this.shaderProgram.attach(ShaderUniform.MODEL_MATRIX);
    }

    public void setDimension(int width, int height) {
        Log.i(Focus.TAG, "<setDimension> width: " + width + " heigth: " + height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render() {

        if (mesh == null) {
            setupTriangle();
        }

        shaderProgram.use();
        GLES20.glViewport(0, 0, width, height);

        // Clear our matrices
        for(int i=0;i<16;i++) {
            projectionMatrix[i] = 0.0f;
            viewMatrix[i] = 0.0f;
            modelMatrix[i] = 0.0f;
        }

        Matrix.orthoM(projectionMatrix, 0, 0f, width, 0.0f, height, 0, 50);
        shaderProgram.setMatrix(ShaderUniform.PROJECTION_MATRIX, projectionMatrix);

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        shaderProgram.setMatrix(ShaderUniform.VIEW_MATRIX, viewMatrix);

        Matrix.setIdentityM(modelMatrix, 0);
        shaderProgram.setMatrix(ShaderUniform.MODEL_MATRIX, modelMatrix);

        mesh.render();

    }

    private void setupTriangle() {
        mesh = new MeshBuilder()
                .shaderProgram(shaderProgram)
                .add(10.0f, 400f, 0.0f)
                .add(10.0f, 100f, 0.0f)
                .add(400f, 400f, 0.0f)
                .triangles()
                .triangle(0, 1, 2)
                .build();
    }

}
