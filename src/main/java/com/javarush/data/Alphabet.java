package com.javarush.data;

import java.util.List;

public class Alphabet {

    private static final List<Character> alphabet = List.of('а', 'б', 'в', 'г', 'д', 'е', 'ё',
            'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц',
            'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '!', '.', ',', '?', ' ', ':', '-', '"');

    public static List<Character> getAlphabet() {
        return alphabet;
    }

    public static int indexOf(char ch) {
        return alphabet.indexOf(ch);
    }

    public static int size() {
        return alphabet.size();
    }

    public static boolean contains(char ch) {
        return alphabet.contains(ch);
    }

    public static char get(int index) {
        return alphabet.get(index);
    }

}
