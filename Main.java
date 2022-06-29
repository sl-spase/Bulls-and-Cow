package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int turn = 1;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        try {


        System.out.println("Input the length of the secret code:");
        int secCodeLen = Integer.parseInt(sc.nextLine());
        System.out.println("Input the number of possible symbols in the code:");
        int possibleSymbols = Integer.parseInt(sc.nextLine());
        if (secCodeLen > possibleSymbols || secCodeLen == 0 || secCodeLen > 36 || possibleSymbols > 36) {
            System.out.println("Error");
            return;
        }

        if (possibleSymbols <= 10) {
            System.out.printf("The secret is prepared: %s (0-9).\n", "*".repeat(secCodeLen));
        } else {
            System.out.printf("The secret is prepared: %s (0-9, a-%c).\n", "*".repeat(secCodeLen), (char)(86 + possibleSymbols));
        }


        String secretWord = generateSecretCode(secCodeLen, possibleSymbols);
        System.out.println("Okay, let's start a game!");
        boolean res = true;
        while (res) {
            System.out.printf("Turn %d:\n", turn);
            String answer = sc.nextLine();
            res = countCowBull(secretWord, answer);
        }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    static String generateSecretCode(int numLen, int possibleSymbols) {
        StringBuilder res = new StringBuilder();
        if (numLen > 10) {
            System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
        } else {
            int max = 9;
            int min = 0;
            int range = max - min + 1;
            Random r = new Random();
            while (res.length() < numLen) {
                int numOrChar = (int)(Math.random() * 2);
                if (numOrChar == 0) {
                    int rand = (int)(Math.random() * range) + min;
                    if (res.indexOf(String.valueOf(rand)) == -1) {
                        res.append(rand);
                    }
                } else {
                    if (possibleSymbols > 10) {
                        char c = (char)(r.nextInt(26) + 'a');
                        if (res.indexOf(String.valueOf(c)) == -1) {
                            res.append(c);
                        }
                    }
                }
            }
        }
        return res.toString();
    }

    static boolean countCowBull(String secretWord, String answer) {
        int cows = 0;
        int bulls = 0;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == answer.charAt(i)) {
                bulls++;
                continue;
            }

            if (secretWord.indexOf(answer.charAt(i)) != -1) {
                cows++;
            }
        }

        if (bulls == secretWord.length()) {
            System.out.printf("Grade: %d bulls.\n", bulls);
            System.out.println("Congratulations! You guessed the secret code.");
            return false;
        } else if (cows != 0 && bulls != 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
        } else if (cows != 0) {
            System.out.printf("Grade: %d cow(s).\n", cows);
        } else if (bulls != 0) {
            System.out.printf("Grade: %d bull(s).\n", bulls);
        } else {
            System.out.printf("Grade: None.\n");
        }
        turn++;

        return true;
    }
}
