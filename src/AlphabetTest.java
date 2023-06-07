import gameLogic.Alphabet;
import gameLogic.Letter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlphabetTest {

    @Test
    void changeBlockStateInButtons() {
        Alphabet alphabet = new Alphabet();
        List<Letter> left = alphabet.getLettersLeftOrRight(true);
        List<Letter> right = alphabet.getLettersLeftOrRight(false);
        List<Letter> priv = new ArrayList<>(left);
        priv.addAll(right);
        alphabet.changeBlockStateInButtons();
        List<Letter> leftChanged = alphabet.getLettersLeftOrRight(true);
        List<Letter> rightChanged = alphabet.getLettersLeftOrRight(false);
        List<Letter> actual = new ArrayList<>(leftChanged);
        actual.addAll(rightChanged);
        assertTrue(actual.containsAll(priv) == true);
    }

    @Test
    void setInputed() {
        Alphabet alphabet = new Alphabet();
        alphabet.setInputed(3);
        char exp = 'г';
        char res = alphabet.getInputed().getLetter();
        assertEquals(exp, res);
    }

    @Test
    void getLettersLeft() {
        Alphabet alphabet = new Alphabet();
        List<Letter> res = alphabet.getLettersLeftOrRight(true);
        List<Letter> exp = new ArrayList<>();
        exp.add(new Letter('а'));
        exp.add(new Letter('б'));
        exp.add(new Letter('в'));
        exp.add(new Letter('г'));
        exp.add(new Letter('д'));
        exp.add(new Letter('е'));
        exp.add(new Letter('ж'));
        exp.add(new Letter('з'));
        exp.add(new Letter('и'));
        exp.add(new Letter('й'));
        exp.add(new Letter('к'));
        exp.add(new Letter('л'));
        exp.add(new Letter('м'));
        exp.add(new Letter('н'));
        exp.add(new Letter('о'));
        exp.add(new Letter('п'));
        for (int i = 0; i < exp.size(); ++i){
            assertTrue(exp.get(i).getLetter() == res.get(i).getLetter());
        }
    }

    @Test
    void getLettersRight() {
        Alphabet alphabet = new Alphabet();
        List<Letter> res = alphabet.getLettersLeftOrRight(false);
        List<Letter> exp = new ArrayList<>();
        exp.add(new Letter('р'));
        exp.add(new Letter('с'));
        exp.add(new Letter('т'));
        exp.add(new Letter('у'));
        exp.add(new Letter('ф'));
        exp.add(new Letter('х'));
        exp.add(new Letter('ц'));
        exp.add(new Letter('ч'));
        exp.add(new Letter('ш'));
        exp.add(new Letter('щ'));
        exp.add(new Letter('ъ'));
        exp.add(new Letter('ы'));
        exp.add(new Letter('ь'));
        exp.add(new Letter('э'));
        exp.add(new Letter('ю'));
        exp.add(new Letter('я'));
        for (int i = 0; i < exp.size(); ++i){
            assertTrue(exp.get(i).getLetter() == res.get(i).getLetter());
        }
    }
}