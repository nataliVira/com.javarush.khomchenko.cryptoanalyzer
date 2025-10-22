package com.javarush.khomchenko.cryptoanalyzer.service;

import com.javarush.khomchenko.cryptoanalyzer.data.UserInfo;
import com.javarush.khomchenko.cryptoanalyzer.enums.CharsetType;
import com.javarush.khomchenko.cryptoanalyzer.enums.TypeOperation;
import com.javarush.khomchenko.cryptoanalyzer.exception.ProcessException;

import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.khomchenko.cryptoanalyzer.enums.TypeOperation.*;

public class UserInterfaceService {

    private static final String OPERATION_REQUEST = "Доступны операции: \n 1. %s \n 2. %s \n 3. %s \n Выберите операцию, введя номер \n";
    private static final String FILE_PATH_REQUEST = "Укажите путь к файлу";
    private static final String KEY_REQUEST = "Укажите ключ шифрования. Любое целое число.";
    private static final String CHARSET_REQUEST = "Доступны кодировки: \n 1. %s, \n 2. %s \n Выберите кодировку файла, введя номер \n";
    private static final String EXIT_REQUEST = "Для выхода наберите  \"yes\" или \"no\" для продолжения";
    private static final String GREETINGS = "Привет";
    private static final String RESULT = "Результирующий файл расположен в каталоге исходного файла";

    private UserInterfaceService() {
    }

    public static void printGreeting() {
        System.out.println(GREETINGS);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static boolean isExit() {
        System.out.println(EXIT_REQUEST);
        String str = scanner.nextLine();
        return "yes".equalsIgnoreCase(str);
    }

    private static Path getPath() throws ProcessException {
        System.out.println(FILE_PATH_REQUEST);
        String filePath = scanner.nextLine();

        if (filePath == null || filePath.trim().equals("")) {
            throw new ProcessException("Задан некорректный путь к файлу");
        }
        return Path.of(filePath);
    }

    private static int getKey() throws ProcessException {
        System.out.println(KEY_REQUEST);
        int key;
        try {
            key = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            throw new ProcessException("В качестве ключа введено не целое число");
        }
        return key;
    }

    private static TypeOperation getOperation() throws ProcessException {
        System.out.printf(OPERATION_REQUEST, ENCRYPTION,
                DECRYPTION, CRYPTANALYSIS);
        int value;
        try {
            value = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            throw new ProcessException("Не введено число с 1 по 3");
        }
        switch (value) {
            case 1:
                return ENCRYPTION;
            case 2:
                return DECRYPTION;
            case 3:
                return CRYPTANALYSIS;
            default:
                throw new ProcessException("Такой операции не существует");
        }
    }

    private static CharsetType getCharset() throws ProcessException {
        System.out.printf(CHARSET_REQUEST, CharsetType.UTF_8, CharsetType.WINDOWS_1251);
        int value;
        try {
            value = scanner.nextInt();
        } catch (Exception e) {
            throw new ProcessException("Не введено число с 1 по 2");
        }
        switch (value) {
            case 1:
                return CharsetType.UTF_8;
            case 2:
                return CharsetType.WINDOWS_1251;
            default:
                throw new ProcessException("Такой кодировки не существует");
        }
    }

    public static UserInfo getUserInfo() throws ProcessException {
        TypeOperation operation = getOperation();
        int key = 1;
        if (operation != CRYPTANALYSIS) {
            key = getKey();
        }
        Path path = getPath();
        CharsetType charset = getCharset();
        return new UserInfo(key, path, operation, charset);
    }

    public static void printResult() {
        System.out.println(RESULT);
    }

}
