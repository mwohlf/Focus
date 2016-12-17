package net.wohlfart.pluto.render;


public abstract class Mesh implements IMesh {

    private final float[] vertices;
    private final int primitive;

    private Mesh(float[] vertices, int primitive) {
        this.vertices = vertices;
        this.primitive = primitive;
    }


    static class ByteIndexMesh extends Mesh {

        private final byte[] indices;

        ByteIndexMesh(float[] floats, int primitiveType, byte[] indexArray) {
            super(floats, primitiveType);
            this.indices = indexArray;
        }

    }

    static class ShortIndexMesh extends Mesh {

        private final short[] indices;

        ShortIndexMesh(float[] floats, int primitiveType, short[] indexArray) {
            super(floats, primitiveType);
            this.indices = indexArray;
        }

    }


    static class IntegerIndexMesh extends Mesh {

        private final int[] indices;

        IntegerIndexMesh(float[] floats, int primitiveType, int[] indexArray) {
            super(floats, primitiveType);
            this.indices = indexArray;
        }

    }

}
