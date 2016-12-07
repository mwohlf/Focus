package net.wohlfart.pluto.shader;

import android.opengl.GLES20;

/**
 * Created by michael on 06.12.16.
 */

public class ShaderProgram {

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
