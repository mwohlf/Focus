package net.wohlfart.pluto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import net.wohlfart.pluto.shader.ShaderLoader;

import java.io.IOException;

public class OpenGLES20Activity extends Activity {

    private CoreRenderView coreRenderView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Focus.TAG, "onCreate");

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        coreRenderView = new CoreRenderView(this);
        setContentView(coreRenderView);
    }

}