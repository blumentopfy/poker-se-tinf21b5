package com.palcas.poker.user_interaction.display;

import java.util.Scanner;

public class PauseDisplay {
    public static void continueWithEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("(press enter to proceed)");
        scanner.nextLine();
    }
}
