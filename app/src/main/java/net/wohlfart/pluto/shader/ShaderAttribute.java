package net.wohlfart.pluto.shader;


public enum ShaderAttribute {
    POSITION_ATTRIBUTE(ShaderProgram.POSITION_ATTRIBUTE),
    TEXCOORD_ATTRIBUTE(ShaderProgram.TEXCOORD_ATTRIBUTE),
    NORMAL_ATTRIBUTE(ShaderProgram.NORMAL_ATTRIBUTE),
    COLOR_ATTRIBUTE(ShaderProgram.COLOR_ATTRIBUTE);

    private final String attributeName;

    ShaderAttribute(String attributeName) {
        this.attributeName = attributeName;
    }

    String getAttributeName() {
        return attributeName;
    }

}
