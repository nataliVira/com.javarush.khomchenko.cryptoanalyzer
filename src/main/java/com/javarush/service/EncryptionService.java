package com.javarush.service;

import com.javarush.data.Alphabet;
import com.javarush.data.UserInfo;
import com.javarush.enums.TypeOperation;
import com.javarush.exception.ProcessException;

public class EncryptionService {


    private static int getKey(int key, TypeOperation operationType) {
        key = key % Alphabet.size();
        if (operationType == TypeOperation.DECRYPTION || operationType == TypeOperation.CRYPTANALYSIS) {
            key = key * (-1);
        }
        return key;
    }

    public static Character encrypt(Character ch, UserInfo userInfo) throws ProcessException {
        int key = getKey(userInfo.getKey(), userInfo.getTypeOperation());
        if (key == 0) {
            throw new ProcessException(String.format("The key %s is not applicable. There is no shift.\n", userInfo.getKey()));
        }
        if (!Alphabet.contains(ch)) {
            return ch;
        }
        int index = Alphabet.indexOf(ch);
        int newIndex = key < 0 ? (index + key + Alphabet.size()) % Alphabet.size() : (index + key) % Alphabet.size();
        return Alphabet.get(newIndex);
    }

}
