package com.javarush.khomchenko.cryptoanalyzer.data;

import com.javarush.khomchenko.cryptoanalyzer.enums.CharsetType;
import com.javarush.khomchenko.cryptoanalyzer.enums.TypeOperation;

import java.nio.file.Path;

public class UserInfo {

    private final int key;
    private final Path sourcePath;
    private final TypeOperation typeOperation;
    private final CharsetType charsetType;

    public UserInfo(final int key,
                    final Path sourcePath,
                    final TypeOperation typeOperation,
                    final CharsetType charsetType) {
        this.key = key;
        this.sourcePath = sourcePath;
        this.typeOperation = typeOperation;
        this.charsetType = charsetType;
    }

    public int getKey() {
        return key;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public CharsetType getCharsetType() {
        return charsetType;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "key=" + key +
                ", sourcePath=" + sourcePath +
                ", typeOperation=" + typeOperation +
                ", charsetType=" + charsetType +
                '}';
    }

}
