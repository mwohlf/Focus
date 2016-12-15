package net.wohlfart.pluto.render;

import android.opengl.GLES20;

/**
 * Created by michael on 14.12.16.
 */

public class Root implements IRenderable {

    @Override
    public void render() {
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.0f, 0.0f, 1.0f);
        // clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

}
