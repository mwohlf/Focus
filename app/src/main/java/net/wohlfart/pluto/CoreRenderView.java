package net.wohlfart.pluto;

import android.content.Context;
import android.opengl.GLSurfaceView;

class CoreRenderView extends GLSurfaceView {

    // Create an OpenGL ES 2.0 context
    private static final int GL_VERSION = 2;

    // Set the CoreRenderer for drawing on the GLSurfaceView
    private final CoreRenderer coreRenderer;

    public CoreRenderView(Context context){
        super(context);
        coreRenderer = new CoreRenderer(context);
        setEGLContextClientVersion(GL_VERSION);
        setRenderer(coreRenderer);
    }
}