package net.wohlfart.pluto.graph;

import android.opengl.GLES20;

import net.wohlfart.pluto.shader.ShaderAttribute;
import net.wohlfart.pluto.shader.ShaderProgram;
import net.wohlfart.pluto.shader.ShaderUniform;

/**
 * Created by michael on 11.12.16.
 */

public class Cam implements SceneGraphNode {


    @Override
    public void render(SceneGraphContext context) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        ShaderProgram shaderProgram = context.getShader();



        // clear Screen and Depth Buffer, we have set the clear color as black.


        // Get handle to shape's transformation matrix
       // int mtrxhandle = GLES20.glGetUniformLocation(shader.handle(), "uMVPMatrix");

        // Apply the projection and view transformation
       // GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

    }
}
