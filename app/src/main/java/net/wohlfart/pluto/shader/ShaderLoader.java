package net.wohlfart.pluto.shader;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;


public class ShaderLoader {

    private static final int INVALID_PROGRAM_ID = 0; // todo: cleanup

    // subfolder in the asset dir
    private static final String BASE_PATH = "shader/";

    // file endings
    private static final String VERTEX_POSTFIX = ".vert";
    private static final String FRAGMENT_POSTFIX = ".frag";

    private final Context context;

    public ShaderLoader(Context context) {
        this.context = context;
    }

    public ShaderProgram load(String name) throws IOException {
        InputStream inputStream;
        String vertexSource, fragmentSource;

        inputStream = context.getResources().getAssets().open(BASE_PATH + name + VERTEX_POSTFIX);
        try {
            vertexSource = readFully(inputStream).toString();
        } finally {
            inputStream.close();
        }

        inputStream = context.getResources().getAssets().open(BASE_PATH + name + FRAGMENT_POSTFIX);
        try {
            fragmentSource = readFully(inputStream).toString();
        } finally {
            inputStream.close();
        }

        return new ShaderProgram(createProgram(vertexSource, fragmentSource), name);
    }

    private ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }

    private int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        GLES20.glUseProgram(0);
        return program;
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("OpenGLUtils", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
