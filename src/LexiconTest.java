import gameLogic.Lexicon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexiconTest {

    @Test
    void showManyWords() {
        Lexicon lexicon = new Lexicon();
        List<String> exp = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            exp.add("мудрец");
            lexicon.addWord(exp.get(i));
        }
        assertEquals(exp, lexicon.showAll());
    }

    @Test
    void addWord() {
        Lexicon lexicon = new Lexicon();
        String exp = "Слово";
        lexicon.addWord(exp);
        String res = lexicon.showAll().get(0);
        assertEquals(exp, res);
    }

    @Test
    void addSingleChar() {
        Lexicon lexicon = new Lexicon();
        String exp = "л";
        lexicon.addWord(exp);
        String res = lexicon.showAll().get(0);
        assertEquals(exp, res);
    }

    @Test
    void addSpace() {
        Lexicon lexicon = new Lexicon();
        String exp = " ";
        lexicon.addWord(exp);
        String res = lexicon.showAll().get(0);
        assertEquals(exp, res);
    }
}