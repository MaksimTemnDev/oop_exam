import gameLogic.Dictionary;
import gameLogic.Game;
import gameLogic.Lexicon;
import gameLogic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    @Test
    void generateWordConfiguration() {
        Dictionary dictionary = new Dictionary();
        String res = dictionary.generateWordConfiguration(5);
        assertTrue(!res.contains(" "));
        assertTrue(res.length()==5);
    }

    @Test
    void setWordToLexicon() {
        Lexicon lexicon = new Lexicon();
        Dictionary dictionary = new Dictionary();
        dictionary.addLexicon(lexicon);
        String exp = "123";
        dictionary.setWordToLexicon(exp, 0);
        String res = lexicon.showAll().get(0);
        assertEquals(exp, res);
    }

    @Test
    void findWordExist() {
        Dictionary dictionary = new Dictionary();
        dictionary.addLexicon(new Lexicon());
        dictionary.addLexicon(new Lexicon());
        Dictionary.WordStatus res = dictionary.findWord("сова");
        assertEquals(Dictionary.WordStatus.NOTED, res);
    }

    @Test
    void findWordNotExist() {
        Dictionary dictionary = new Dictionary();
        Dictionary.WordStatus res = dictionary.findWord("абобус");
        assertEquals(Dictionary.WordStatus.UNKNOWN, res);
    }

    @Test
    void addWord() {
        Dictionary dictionary = new Dictionary();
        Dictionary.WordStatus res = dictionary.findWord("абобус");
        dictionary.addWord("слово");
        assertEquals(Dictionary.WordStatus.UNKNOWN, res);
    }
}