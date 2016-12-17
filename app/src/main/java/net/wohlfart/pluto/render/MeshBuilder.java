package net.wohlfart.pluto.render;

import android.opengl.GLES20;

import com.google.common.primitives.UnsignedBytes;

import java.util.Arrays;

/**
 * Created by michael on 16.12.16.
 */

public class MeshBuilder {

    private static final int MAX_INDICES = 300;

    private int vertexCount;
    private float[] vertices;

    private int indexCount;
    private int[] indices;

    // see: http://www.naturewizard.at/Tutorials/Tutorial01/images/image009.jpg
    //  GLES20.GL_POINTS,
    // GL_LINES, GL_LINE_STRIP, GL_LINE_LOOP
    // GL_TRIANGLES, GL_TRIANGLE_FAN, GL_TRIANGLE_STRIP
    // GL_QUADS, GL_QUAD_STRIP, GL_POLYGON
    private int primitiveType;

    MeshBuilder() {
        vertexCount = 0;
        vertices = new float[0xFF * 3];
        indexCount = 0;
        indices = new int[MAX_INDICES];
        primitiveType = 0;
    }

    MeshBuilder add(float x, float y, float z) {
        vertices[vertexCount * 3 + 0] = x;
        vertices[vertexCount * 3 + 1] = y;
        vertices[vertexCount * 3 + 2] = z;
        vertexCount++;
        return this;
    }

    MeshBuilder triangles() {
        primitiveType = GLES20.GL_TRIANGLES;
        return this;
    }

    MeshBuilder triangle(int a, int b, int c) {
        indices[indexCount++] = a;
        indices[indexCount++] = b;
        indices[indexCount++] = c;
        return this;
    }

    public Mesh build() {
        if (indexCount < 0x01 << Byte.SIZE) {
            byte[] indexArray = new byte[indexCount];
            for (int i = 0; i < indexArray.length; i++) {
                indexArray[i] = (byte) indices[i];
            }
            return new Mesh.ByteIndexMesh(
                    Arrays.copyOf(vertices, vertexCount * 3),  // vertices
                    primitiveType,
                    indexArray);
        }
        else if (indexCount < 0x01 << Short.SIZE) {
            short[] indexArray = new short[indexCount];
            for (int i = 0; i < indexArray.length; i++) {
                indexArray[i] = (short) indices[i];
            }
            return new Mesh.ShortIndexMesh(
                    Arrays.copyOf(vertices, vertexCount * 3),  // vertices
                    primitiveType,
                    indexArray);
        }
        else if (indexCount < 0x01 << Integer.SIZE) {
            int[] indexArray = new int[indexCount];
            for (int i = 0; i < indexArray.length; i++) {
                indexArray[i] = indices[i];
            }
            return new Mesh.IntegerIndexMesh(
                    Arrays.copyOf(vertices, vertexCount * 3),  // vertices
                    primitiveType,
                    indexArray);
        }
        else {
            throw new IllegalStateException("<build> indexCount too large");
        }
    }



}
