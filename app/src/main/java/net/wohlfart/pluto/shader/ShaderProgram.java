package net.wohlfart.pluto.shader;

import android.opengl.GLES20;

public class ShaderProgram {

    public static final String POSITION_ATTRIBUTE = "Position";
    public static final String TEXCOORD_ATTRIBUTE = "";
    public static final String NORMAL_ATTRIBUTE = "";
    public static final String COLOR_ATTRIBUTE = "";

    public static final String MODEL_VIEW_PROJECTION_MATRIX = "MVPMatrix";
    public static final String MODEL_MATRIX = "ModelMatrix";
    public static final String VIEW_MATRIX = "ViewMatrix";
    public static final String PROJECTION_MATRIX = "ProjectionMatrix";

    private final String name;
    private final int handle;

    public ShaderProgram(int handle, String name) {
        this.handle = handle;
        this.name = name;
    }

    public int handle() {
        return handle;
    }

    public void use() {
        GLES20.glUseProgram(handle);
    }

}
