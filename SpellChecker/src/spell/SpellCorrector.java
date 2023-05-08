package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class SpellCorrector implements ISpellCorrector{
    Trie dictionary = new Trie();

    public SpellCorrector() {}

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File text = new File(dictionaryFileName);
        Scanner scanner = new Scanner(text);
        while (scanner.hasNext()) {
            String str = scanner.next();
            dictionary.add(str);
        }
    }

    Set<String> potential_words(String input_word) {
        Set<String> words = new java.util.HashSet<>();

        for (int index=0; index < input_word.length() + 1; index++) {

            String omitted_char = input_word.substring(0, index);
            if (index < input_word.length() - 1) {
                omitted_char += input_word.substring(index + 1);
            }
            if (dictionary.find(omitted_char) != null) {
                words.add(omitted_char);
            }

            // Letter Swap

            if (index < input_word.length() - 1) {
                char first = input_word.charAt(index);
                char second = input_word.charAt(index + 1);
                String letter_swap = input_word.substring(0, index) + second + first;
                if (index < input_word.length() - 2) {
                    letter_swap += input_word.substring(index + 2);
                }
                if (dictionary.find(letter_swap) != null) {
                    words.add(letter_swap);
                }
            }

            for (int i =0; i < 26; i++) {

                // Wrong Character

                char new_char = (char)('a' + i);
                String wrong_char = input_word.substring(0, index) + new_char;
                if (index < input_word.length() - 1) {
                    wrong_char += input_word.substring(index + 1);
                }
                if (dictionary.find(wrong_char) != null) {
                    words.add(wrong_char);
                }


                // Insert Extra Char
                String extra_char = input_word.substring(0, index) + new_char + input_word.substring(index);
                if (dictionary.find(extra_char) != null) {
                    words.add(extra_char);
                }

            }
        }
        return words;
    }


    Set<String> far_potential_words(String input_word) {
        Set<String> words = new java.util.HashSet<>();

        for (int index=0; index < input_word.length() + 1; index++) {

            String omitted_char = input_word.substring(0, index);
            if (index < input_word.length() - 1) {
                omitted_char += input_word.substring(index + 1);
            }
            words.addAll(potential_words(omitted_char));


            // Letter Swap

            if (index < input_word.length() - 1) {
                char first = input_word.charAt(index);
                char second = input_word.charAt(index + 1);
                String letter_swap = input_word.substring(0, index) + second + first;
                if (index < input_word.length() - 2) {
                    letter_swap += input_word.substring(index + 2);
                }
                words.addAll(potential_words(letter_swap));
            }

            for (int i =0; i < 26; i++) {

                // Wrong Character

                char new_char = (char)('a' + i);
                String wrong_char = input_word.substring(0, index) + new_char;
                if (index < input_word.length() - 1) {
                    wrong_char += input_word.substring(index + 1);
                }
                words.addAll(potential_words(wrong_char));


                // Insert Extra Char
                String extra_char = input_word.substring(0, index) + new_char + input_word.substring(index);
                words.addAll(potential_words(extra_char));

            }
        }
        return words;
    }
    @Override
    public String suggestSimilarWord(String inputWord) {
        String input_word = inputWord.toLowerCase();
        if (dictionary.find(input_word) != null) {
            return input_word;
        }
        Set<String> words = potential_words(input_word);

        int value = 0;
        String lowest = "";
        for (String word:words) {
            if (dictionary.find(word).getValue() > value) {
                value = dictionary.find(word).getValue();
                lowest = word;
            }
            if (dictionary.find(word).getValue() == value && word.compareTo(lowest) < 0) {
                value = dictionary.find(word).getValue();
                lowest = word;
            }
        }

        if (lowest.equals("")) {
            for (String word:far_potential_words(input_word)) {
                if (dictionary.find(word).getValue() > value) {
                    value = dictionary.find(word).getValue();
                    lowest = word;
                }
                if (dictionary.find(word).getValue() == value && word.compareTo(lowest) < 0) {
                    value = dictionary.find(word).getValue();
                    lowest = word;
                }
            }
        }
        if (lowest.equals("")) {
            return null;
        }
        return lowest;
    }
}
