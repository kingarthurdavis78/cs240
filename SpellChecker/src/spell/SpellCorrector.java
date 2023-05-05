package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{

    public SpellCorrector() {}

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File text = new File(dictionaryFileName);
        Scanner scanner = new Scanner(text);
        while (scanner.hasNext()) {
            String str = scanner.next();
            for (int i = 0; i < str.length(); i++) {

            }
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
