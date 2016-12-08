package net.wohlfart.pluto;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import net.wohlfart.pluto.render.DefaultRenderable;
import net.wohlfart.pluto.render.IRenderable;
import net.wohlfart.pluto.render.Triangle;
import net.wohlfart.pluto.shader.ShaderLoader;
import net.wohlfart.pluto.shader.ShaderProgram;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CoreRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private DefaultRenderable renderable;

    private float[] mtrxProjection = new float[16];
    private float[] mtrxView = new float[16];
    private float[] mtrxProjectionAndView = new float[16];
    private ShaderProgram shader;

    public CoreRenderer(Context context) {
        this.context = context;
    }

    /**
     * This method is called when the surface is first created. It will also be called if we lose our surface context and it is later recreated by the system.
     * called on the render thread
     */
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        Log.i(Focus.TAG, "onSurfaceCreated, thread is " + Thread.currentThread());

        // Set the background frame color
        GLES20.glClearColor(0.1f, 0.0f, 0.0f, 1.0f);

        renderable = new Triangle();

        try {
            shader = new ShaderLoader(context).load("plain");
            renderable = new Triangle();
            renderable.setShaderProgram(shader);
            Log.i(Focus.TAG, "shader found, id: " + shader);
        } catch (IOException ex) {
            Log.e(Focus.TAG, "error creating shader", ex);
        }

    }

    /**
     * This is called whenever it’s time to draw a new frame.
     */
    public void onDrawFrame(GL10 glUnused) {
        // Log.i(Focus.TAG, "onDrawFrame, thread is " + Thread.currentThread());

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        render(mtrxProjectionAndView);
    }

    private void render(float[] m) {

        // clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);



        // Get handle to shape's transformation matrix
        int mtrxhandle = GLES20.glGetUniformLocation(shader.handle(), "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

        renderable.render();

    }

    /**
     * This is called whenever the surface changes; for example, when switching from portrait to landscape. It is also called after the surface has been created.
     */
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        Log.i(Focus.TAG, "onSurfaceChanged, thread is " + Thread.currentThread());

        GLES20.glViewport(0, 0, width, height);


        // Clear our matrices
        for(int i=0;i<16;i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, width, 0.0f, height, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

    }


}