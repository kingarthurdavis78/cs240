package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    SortedSet<String> word_set = new TreeSet<>();
    SortedSet<Character> guessed_letters = new TreeSet<>();

    Map<String, SortedSet<String>> word_map = new HashMap<>();
    String word;

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        word_set.clear();
        Scanner scanner = new Scanner(dictionary);
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.length() == wordLength) {
                word_set.add(word);
            }
        }
        word = "-".repeat(wordLength);
        if (word_set.isEmpty()) {
            throw new EmptyDictionaryException();
        }
    }

    String getKey(String word, char guess) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                result.append(guess);
            }
            else {
                result.append("_");
            }
        }
        return result.toString();
    }

    int num_letters(String word, char guess) {
        int result = 0;
        for (int i=0; i<word.length(); i++) {
            char letter = word.charAt(i);
            if (guess == letter) {
                result++;
            }
        }
        return result;
    }

    String right_most(String word1, String word2, char guess) {
        for (int i=0; i<word1.length();i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            if (c1 == guess && c2 == guess) {
                continue;
            }
            if (c1 != guess && c2 == guess) {
                return word1;
            } else if (c1 == guess) {
                return word2;
            }
        }
        return word1;
    }

    @Override
    public Set<String> makeGuess(char guess1) throws GuessAlreadyMadeException {
        Character guess = Character.toLowerCase(guess1);
        if (guessed_letters.contains(guess)) {
            throw new GuessAlreadyMadeException();
        }
        guessed_letters.add(guess);

        word_map.clear();
        for (String word:word_set) {
            String key = getKey(word, guess);
            if (!word_map.containsKey(key)) {
                word_map.put(key, new TreeSet<>());
            }
            word_map.get(key).add(word);
        }

        String most = null;
        for (String k:word_map.keySet()) {
            if (most == null) {
                most = k;
                continue;
            }
            if (word_map.get(k).size() > word_map.get(most).size()) {
                most = k;
            } else if (word_map.get(k).size() == word_map.get(most).size()) {
                if (most.contains(guess.toString()) && k.contains(guess.toString())) {
                    if (num_letters(k, guess) < num_letters(most, guess)) {
                        most = k;
                    }
                    if (num_letters(k, guess) == num_letters(most, guess)) {
                        most = right_most(most, k, guess);
                    }
                } else if (!k.contains(guess.toString())) {
                    most = k;
                }

            }
        }
        word = most;
        word_set = word_map.get(most);
        return word_set;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessed_letters;
    }
}
