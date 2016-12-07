package net.wohlfart.pluto;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class CoreActivity extends Activity {

    private static final int GL_VERSION = 2;
    private CoreRenderView coreRenderView;
    private CoreRenderer coreRenderer;

    // see: http://androidblog.reindustries.com/opengl-es-2-0-2d-shaders-series-001-basic-shaders/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(Focus.TAG, "onCreate");

        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        Log.i(Focus.TAG, "configurationInfo.reqGlEsVersion: " + configurationInfo.reqGlEsVersion);

        // Set our view.
        setContentView(R.layout.activity_main);

        // Retrieve our Relative layout from our main layout we just set to our view.
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.core_layout);

        coreRenderer = new CoreRenderer(this);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        coreRenderView = new CoreRenderView(this);
        coreRenderView.setEGLContextClientVersion(GL_VERSION);
        coreRenderView.setRenderer(coreRenderer);
        // Render the view only when there is a change in the drawing data
        coreRenderView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        // Attach our surfaceview to our relative layout from our main layout.
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(coreRenderView, glParams);
    }

    @Override
    protected void onResume() {
        // The activity must call the GL surface view's onResume() on activity onResume().
        super.onResume();
        coreRenderView.onResume();
    }

    @Override
    protected void onPause() {
        // The activity must call the GL surface view's onPause() on activity onPause().
        super.onPause();
        coreRenderView.onPause();
    }

}