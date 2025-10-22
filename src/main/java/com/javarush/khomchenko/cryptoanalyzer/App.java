package com.javarush.khomchenko.cryptoanalyzer;

import com.javarush.khomchenko.cryptoanalyzer.data.UserInfo;
import com.javarush.khomchenko.cryptoanalyzer.exception.ProcessException;
import com.javarush.khomchenko.cryptoanalyzer.service.FileService;
import com.javarush.khomchenko.cryptoanalyzer.service.UserInterfaceService;

import java.io.IOException;


/**
 * Caesar cipher
 *
 */
public class App {

    public static void main( String[] args ) {
        UserInterfaceService.printGreeting();
        while (true) {
            UserInfo userInfo = null;
            try {
                userInfo = UserInterfaceService.getUserInfo();
            } catch (Exception e) {
                System.out.println("Unable to retrieve transaction information. Due to:");
                e.printStackTrace();
                sleep();
                continue;
            }

            try {
                FileService.process(userInfo);
                UserInterfaceService.printResult();
            } catch (IOException | ProcessException e) {
                System.out.println("The file is not being processed. Due to: " + e.getMessage());
                sleep();
            }

            if (UserInterfaceService.isExit()) {
                break;
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
