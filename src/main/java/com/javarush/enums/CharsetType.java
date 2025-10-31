package com.javarush.enums;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum CharsetType {

    UTF_8(StandardCharsets.UTF_8), WINDOWS_1251(Charset.forName("windows-1251"));

    private final Charset charset;

    CharsetType(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return this.charset;
    }

}
