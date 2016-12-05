package net.wohlfart.pluto;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OpenGLES20Activity extends Activity {

    private CoreRenderView coreRenderView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        coreRenderView = new CoreRenderView(this);
        setContentView(coreRenderView);
    }
}