package com.javarush.service;

import com.javarush.data.UserInfo;
import com.javarush.enums.CharsetType;
import com.javarush.enums.TypeOperation;
import com.javarush.exception.ProcessException;

import java.nio.file.Path;

import static com.javarush.enums.TypeOperation.*;

public class UserInterfaceService {

    private UserInterfaceService() {
    }

    private static Path getPath(String filePath) throws ProcessException {
        if (filePath == null || filePath.trim().equals("")) {
            throw new ProcessException("Задан некорректный путь к файлу");
        }
        return Path.of(filePath);
    }

    private static int getKey(String strKey) throws ProcessException {
        int key;
        try {
            key = Integer.parseInt(strKey);
        } catch (Exception e) {
            throw new ProcessException("В качестве ключа введено не целое число");
        }
        return key;
    }

    private static TypeOperation getOperation(String operation) throws ProcessException {
        switch (operation) {
            case "ENCRYPTION":
                return ENCRYPTION;
            case "DECRYPTION":
                return DECRYPTION;
            case "CRYPTANALYSIS":
                return CRYPTANALYSIS;
            default:
                throw new ProcessException("Такой операции не существует");
        }
    }

    private static CharsetType getCharset(String charsSet) throws ProcessException {
        switch (charsSet) {
            case "UTF_8":
                return CharsetType.UTF_8;
            case "WINDOWS_1251":
                return CharsetType.WINDOWS_1251;
            default:
                throw new ProcessException("Такой кодировки не существует");
        }
    }

    public static UserInfo getUserInfo(String action, String charSet, String strPath, String strKey) throws ProcessException {
        TypeOperation operation = getOperation(action);
        int key = 1;
        if (operation != CRYPTANALYSIS) {
            key = getKey(strKey);
        }
        Path path = getPath(strPath);
        CharsetType charset = getCharset(charSet);
        return new UserInfo(key, path, operation, charset);
    }

}
