package com.palcas.poker.display;

public class DisplayElements {

    public static void printSeperator() {
        System.out.println(SEPERATOR);
    }

    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    public static void clearWithSeperator() {
        clearConsole();
        printSeperator();
    }

    public static String SEPERATOR = "-----------------------------";
}
