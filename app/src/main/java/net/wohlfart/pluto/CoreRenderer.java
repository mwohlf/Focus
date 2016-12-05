package net.wohlfart.pluto;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import net.wohlfart.pluto.shader.ShaderLoader;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CoreRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    public CoreRenderer(Context context) {
        this.context = context;
    }

    // called on the render thread
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        Log.i(Focus.TAG, "onSurfaceCreated, thread is " + Thread.currentThread());

        // Set the background frame color
        GLES20.glClearColor(0.7f, 0.0f, 0.0f, 1.0f);


        try {
            int shader = new ShaderLoader("plain", context).load();
            Log.i(Focus.TAG, "shader found, id: " + shader);
        } catch (IOException ex) {
            Log.e(Focus.TAG, "error creating shader", ex);
        }

    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}