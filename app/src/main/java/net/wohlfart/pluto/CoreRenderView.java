package net.wohlfart.pluto;

import android.content.Context;
import android.opengl.GLSurfaceView;


/**
 * provides a dedicated render thread for OpenGL
 * supports continuous or on-demand rendering
 * takes care of the screen setup for you using EGL, the interface between OpenGL and the underlying window system
 */
public class CoreRenderView extends GLSurfaceView {

    public CoreRenderView(Context context){
        super(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}