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
//      clear items from last round
        word_map.clear();
        guessed_letters.clear();
        word = "_".repeat(wordLength);

//      Initialize Word Set
        Scanner scanner = new Scanner(dictionary);
        word_set.clear();
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.length() == wordLength && word.matches("[a-zA-Z]*")) {
                word_set.add(word);
            }
        }
//      Empty Dictionary Error
        if (word_set.isEmpty()) {
            throw new EmptyDictionaryException();
        }
    }

    String getKey(String word2, char guess) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (!String.valueOf(word.charAt(i)).equals("_")) {
                result.append(word.charAt(i));
                continue;
            }
            if (word2.charAt(i) == guess) {
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

    String right_word(String word1, String word2, char guess) {
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

    void build_word_map(char guess) {
        word_map.clear();
        for (String word:word_set) {
            String key = getKey(word, guess);
            if (!word_map.containsKey(key)) {
                word_map.put(key, new TreeSet<>());
            }
            word_map.get(key).add(word);
        }
    }


    @Override
    public Set<String> makeGuess(char guess1) throws GuessAlreadyMadeException {
        // Make LowerCase
        char guess = Character.toLowerCase(guess1);

        // Check if already guessed
        if (guessed_letters.contains(guess)) {
            throw new GuessAlreadyMadeException();
        }
        guessed_letters.add(guess);

        //partition the word set
        build_word_map(guess);

        for (String k:word_map.keySet()) {
            if (!word_map.containsKey(word)) {
                word = k;
            }
            if (word_map.get(k).size() > word_map.get(word).size()) {
                word = k;
            } else if (word_map.get(k).size() == word_map.get(word).size()) {
                if (word.contains(Character.toString(guess)) && k.contains(Character.toString(guess))) {
                    if (num_letters(k, guess) < num_letters(word, guess)) {
                        word = k;
                    }
                    if (num_letters(k, guess) == num_letters(word, guess)) {
                        word = right_word(word, k, guess);
                    }
                } else if (!k.contains(Character.toString(guess))) {
                    word = k;
                }

            }
        }
        word_set = word_map.get(word);
        return word_set;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessed_letters;
    }
}
