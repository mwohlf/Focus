package net.wohlfart.pluto;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import net.wohlfart.pluto.render.Root;
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

    private Triangle renderable;
    private Root root;

    private ShaderProgram shader;

    public CoreRenderer(Context context) {
        this.context = context;
    }

    /**
     * This method is called when the surface is first created. It will also be called if we lose
     * our surface context and it is later recreated by the system.
     *
     * called on the render thread
     */
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        Log.i(Focus.TAG, "onSurfaceCreated, thread is " + Thread.currentThread());

        root = new Root();
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
        root.render();
        renderable.render();
    }

    /**
     * This is called whenever the surface changes; for example, when switching from portrait to landscape. It is also called after the surface has been created.
     */
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        Log.i(Focus.TAG, "onSurfaceChanged, thread is " + Thread.currentThread());
        renderable.setDimension(width, height);
    }


}