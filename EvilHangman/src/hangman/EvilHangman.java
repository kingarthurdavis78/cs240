package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class EvilHangman {
    public static void main(String[] args) {

        File dictionary = new File(args[0]);
        int word_length = Integer.parseInt(args[1]);
        int num_guesses = Integer.parseInt(args[2]);
        EvilHangmanGame game = new EvilHangmanGame();
        try {
            game.startGame(dictionary, word_length);
        }
        catch (IOException | EmptyDictionaryException ex) {
            ex.printStackTrace();
        }
        Scanner in = new Scanner(System.in);

        for (int i=num_guesses;i>0;i--) {
            System.out.printf("You have %d guesses left%n", i);
            System.out.printf("Used Letters: %s%n", game.guessed_letters);
            System.out.printf("Word: %s%n", game.word);
            System.out.print("Enter guess: ");
            String s = in.nextLine();
            if (s.length() != 1 || !s.matches("[a-zA-Z]")) {
                System.out.println("Invalid Input");
                i++;
                continue;
            }
            char guess = s.charAt(0);
            try {
                game.makeGuess(guess);
            }
            catch (GuessAlreadyMadeException e) {
                System.out.printf("You already guessed %s%n", guess);
                System.out.println();
                i++;
                continue;
            }
            if (!game.word.contains(s)) {
                System.out.printf("Sorry there are no %s's%n", s);
            }
            else {
                int result = 0;
                for (int j = 0; j < game.word.length(); j++) {
                    char letter = game.word.charAt(j);
                    if (String.valueOf(letter).equals(s)) {
                        result++;
                    }
                }
                System.out.printf("Yes, there is %d %s%n", result, s);
                i++;
            }
            System.out.println();
            if (!game.word.contains("_")) {
                System.out.println("You win!");
                System.out.printf("The word was %s%n", game.word);
                break;
            }

        }
        if (game.word.contains("_")) {
            System.out.println("You lose!");
            System.out.printf("The word was %s%n", game.word_set.first());
        }

    }

}
