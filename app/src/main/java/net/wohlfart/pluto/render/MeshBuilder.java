package net.wohlfart.pluto.render;

import android.opengl.GLES20;
import android.support.annotation.NonNull;

import com.google.common.primitives.UnsignedBytes;

import net.wohlfart.pluto.shader.ShaderProgram;
import net.wohlfart.pluto.shader.ShaderUniform;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.EnumSet;

import static android.R.attr.type;
import static net.wohlfart.pluto.shader.ShaderAttribute.POSITION_ATTRIBUTE;

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

    private ShaderProgram shaderProgram;

    MeshBuilder() {
        vertexCount = 0;
        vertices = new float[0xFF * 3];
        indexCount = 0;
        indices = new int[MAX_INDICES];
        primitiveType = 0;
    }

    public MeshBuilder shaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        return this;
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

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        int type = getIndexType();

        return new Mesh(
                shaderProgram,
                indexCount,
                vertexBuffer,
                createIndexBuffer(type),
                type,
                primitiveType,
                EnumSet.of(POSITION_ATTRIBUTE)
                );
    }

    private int getIndexType() {
        if (indexCount < 0x01 << Byte.SIZE) {
            return GLES20.GL_UNSIGNED_BYTE;
        }
        else if (indexCount < 0x01 << Short.SIZE) {
            return GLES20.GL_UNSIGNED_SHORT;
        }
        else if (indexCount < 0x01 << Integer.SIZE) {
            return GLES20.GL_UNSIGNED_INT;
        }
        else {
            throw new IllegalStateException("<getIndexType> indexCount too large");
        }
    }

    private Buffer createIndexBuffer(int type) {
        switch (type) {
            case GLES20.GL_UNSIGNED_BYTE:
                return getByteIndexBuffer();
            case GLES20.GL_UNSIGNED_SHORT:
                return getShortIndexBuffer();
            case GLES20.GL_UNSIGNED_INT:
                return getIntegerIndexBuffer();
            default:
                throw new IllegalStateException("<createIndexBuffer> indexCount too large");
        }
    }

    @NonNull
    private Buffer getIntegerIndexBuffer() {
        IntBuffer indexBuffer = ByteBuffer
                .allocateDirect(indices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        for (int i = 0; i < indices.length; i++) {
            indexBuffer.put(indices[i]);
        }
        indexBuffer.position(0);
        return indexBuffer;
    }

    @NonNull
    private Buffer getShortIndexBuffer() {
        ShortBuffer indexBuffer = ByteBuffer
                .allocateDirect(indices.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
        for (int i = 0; i < indices.length; i++) {
            indexBuffer.put((short) indices[i]);
        }
        indexBuffer.position(0);
        return indexBuffer;
    }

    @NonNull
    private Buffer getByteIndexBuffer() {
        ByteBuffer indexBuffer = ByteBuffer
                .allocateDirect(indices.length * 1);
        for (int i = 0; i < indices.length; i++) {
            indexBuffer.put((byte) indices[i]);
        }
        indexBuffer.position(0);
        return indexBuffer;
    }

}
