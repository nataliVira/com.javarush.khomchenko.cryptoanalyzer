package com.javarush.khomchenko.cryptoanalyzer.enums;

import java.nio.charset.Charset;

public enum CharsetType {
    UTF_8(Charset.forName("UTF-8")), WINDOWS_1251(Charset.forName("windows-1251"));

    private final Charset charset;

    private CharsetType(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return this.charset;
    }

}
