package com.javarush.khomchenko.cryptoanalyzer.service;

import com.javarush.khomchenko.cryptoanalyzer.data.Alphabet;
import com.javarush.khomchenko.cryptoanalyzer.data.UserInfo;
import com.javarush.khomchenko.cryptoanalyzer.enums.TypeOperation;
import com.javarush.khomchenko.cryptoanalyzer.exception.ProcessException;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class FileService {

    public static void process(UserInfo userInfo) throws IOException, ProcessException {
        if (userInfo.getTypeOperation() == TypeOperation.CRYPTANALYSIS) {

            for (int i = 1; i < Alphabet.size(); i++) {
                UserInfo newUserInfo = new UserInfo(i, userInfo.getSourcePath(), userInfo.getTypeOperation(), userInfo.getCharsetType());
                System.out.println(newUserInfo);
                processFile(newUserInfo);
            }
        } else {
            processFile(userInfo);
        }
    }

    private static void processFile(UserInfo userInfo) throws IOException, ProcessException {
        checkFile(userInfo);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Path destinationFilePath = generateDestinationFileName(userInfo.getTypeOperation(), userInfo);
        deleteFile(destinationFilePath);
        try (FileChannel fileChannel = FileChannel.open(userInfo.getSourcePath(), StandardOpenOption.READ);
             FileChannel fileChannelOut = FileChannel.open(destinationFilePath, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            StringBuilder sb = new StringBuilder();
            CharsetDecoder decoder = userInfo.getCharsetType().getCharset().newDecoder();
            while (fileChannel.read(buffer) != -1) {
                buffer.flip();
                fixBom(buffer);
                CharBuffer charBuffer = decoder.decode(buffer);
                while (charBuffer.hasRemaining()) {
                    char ch = charBuffer.get();
                    char encryptedChar = EncryptionService.encrypt(ch, userInfo);
                    sb.append(encryptedChar);
                }
                buffer.clear();
                writeFile(fileChannelOut, sb, userInfo.getCharsetType().getCharset());
                sb.setLength(0);
            }
        }
    }

    private static void checkFile(UserInfo userInfo) throws ProcessException {
        File file = userInfo.getSourcePath().toFile();

        if (!file.exists()) {
            throw new ProcessException("File does not exist");
        }

        if (!Files.isRegularFile(userInfo.getSourcePath())) {
            throw new ProcessException(userInfo.getSourcePath() + " is not file");
        }

        String fileName = userInfo.getSourcePath().getFileName().toString();
        if (!fileName.substring(fileName.lastIndexOf(".")).equals(".txt")) {
            throw new ProcessException("File is not \"txt\"");
        }

        if (!Files.isReadable(userInfo.getSourcePath())) {
            throw new ProcessException("File is readable");
        }

        if (file.length() == 0) {
            throw new ProcessException("File is empty");
        }
    }

    private static void deleteFile(Path path) throws IOException {
        Files.deleteIfExists(path);
    }

    private static void writeFile(FileChannel fileChannelOut, StringBuilder sb, Charset charset) throws IOException {
        fileChannelOut.write(ByteBuffer.wrap((sb.toString()).getBytes(charset)));
        sb.setLength(0);
    }

    private Path getDestinationFileNameByKey(List<Path> destinationPaths, int key) {
        if (destinationPaths.size() == 1) {
            return destinationPaths.get(0);
        }
        return destinationPaths.stream().filter(p -> p.endsWith("_" + key + ".txt")).findFirst().orElse(null);
    }

    private static Path generateDestinationFileName(TypeOperation typeOperation, UserInfo userInfo) {
        Path parent = userInfo.getSourcePath().getParent();
        String fileName = userInfo.getSourcePath().getFileName().toString();
        switch (typeOperation) {
            case ENCRYPTION:
                return parent.resolve(fileName.substring(0, fileName.indexOf(".")) + "_encrypted.txt");
            case DECRYPTION:
                return parent.resolve(fileName.substring(0, fileName.indexOf(".")) + "_decrypted.txt");
            case CRYPTANALYSIS: {
                return parent.resolve(fileName.substring(0, fileName.indexOf(".")) + "_cryptoanalyzed_" + userInfo.getKey() + ".txt");

            }
            default:
                throw new IllegalArgumentException("Unknown operation type: " + typeOperation);
        }
    }

    private static void fixBom(ByteBuffer buffer) {
        if (buffer.remaining() < 3) return;
        int pos = buffer.position();
        if (buffer.get(pos) == (byte) 0xEF &&
                buffer.get(pos + 1) == (byte) 0xBB &&
                buffer.get(pos + 2) == (byte) 0xBF) {
            buffer.position(pos + 3);
            System.out.println("BOM (U+FEFF) is deleted.");
        }
    }

}
