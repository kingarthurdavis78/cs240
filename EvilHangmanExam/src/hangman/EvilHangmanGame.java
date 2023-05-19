package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{

    String word_so_far;

    Map<String, SortedSet<String>> word_map = new HashMap<String, SortedSet<String>>();

    SortedSet<Character> guesses_made = new TreeSet<Character>();

    SortedSet<String> word_set = new TreeSet<String>();

    public EvilHangmanGame() {}
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {

        word_set.clear();
        word_map.clear();

        word_so_far = "_".repeat(wordLength);

        Scanner read_file = new Scanner(dictionary);
        while (read_file.hasNext()) {
            String word = read_file.next();
            if (word.length() == wordLength) {
                word_set.add(word);
            }

        }
        if (word_set.size() == 0) {
            throw new EmptyDictionaryException();
        }
    }

    private String make_key(String dict_word, char guess) {
        StringBuilder key = new StringBuilder();
        for (int i=0; i<word_so_far.length(); i++) {
            if (dict_word.charAt(i) == word_so_far.charAt(i)) {
                key.append(word_so_far.charAt(i));
            } else if (dict_word.charAt(i) == guess) {
                key.append(guess);
            }
            else {
                key.append('_');
            }
        }
        return key.toString();
    }

    private void partition_word_set(char guess) {
        word_map.clear();
        for (String word:word_set) {
            String key = make_key(word, guess);
            if (!word_map.containsKey(key)) {
                word_map.put(key, new TreeSet<>());
            }
            word_map.get(key).add(word);
        }
    }

    private String right_most(String word, char guess) {
        for (int i=0; i<word_so_far.length(); i++) {
            if (word_so_far.charAt(i) == guess && word.charAt(i) == guess) {
                continue;
            }
            if (word_so_far.charAt(i) != guess && word.charAt(i) == guess) {
                return word_so_far;
            }
            if (word_so_far.charAt(i) == guess) {
                return word;
            }
        }
        return word_so_far;
    }

    @Override
    public Set<String> makeGuess(char guess1) throws GuessAlreadyMadeException {
        char guess = Character.toLowerCase(guess1);

        // Check if already Guessed
        if (guesses_made.contains(guess)) {
            throw new GuessAlreadyMadeException();
        }
        guesses_made.add(guess);

        // Partition Word Set
        partition_word_set(guess);

        for (String key:word_map.keySet()) {
            if (!word_map.containsKey(word_so_far)) {
                word_so_far = key;
                continue;
            }
            if (word_map.get(key).size() > word_map.get(word_so_far).size()) {
                word_so_far = key;
                continue;
            }
            if (word_map.get(key).size() == word_map.get(word_so_far).size()) {
                word_so_far = right_most(key, guess);
            }

        }

        word_set = word_map.get(word_so_far);
        return word_set;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guesses_made;
    }
}
