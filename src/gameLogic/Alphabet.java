package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alphabet {

    private Letter[] letters = new Letter[]{
            new Letter('а'),
            new Letter('б'),
            new Letter('в'),
            new Letter('г'),
            new Letter('д'),
            new Letter('е'),
            new Letter('ж'),
            new Letter('з'),
            new Letter('и'),
            new Letter('й'),
            new Letter('к'),
            new Letter('л'),
            new Letter('м'),
            new Letter('н'),
            new Letter('о'),
            new Letter('п'),
            new Letter('р'),
            new Letter('с'),
            new Letter('т'),
            new Letter('у'),
            new Letter('ф'),
            new Letter('х'),
            new Letter('ц'),
            new Letter('ч'),
            new Letter('ш'),
            new Letter('щ'),
            new Letter('ъ'),
            new Letter('ы'),
            new Letter('ь'),
            new Letter('э'),
            new Letter('ю'),
            new Letter('я'),
    };
    private List<Letter> lettersLeft = new ArrayList<>();
    private List<Letter> lettersRight = new ArrayList<>();
    private int firstLetter = -1;
    private int secondLetter = -1;

    public Alphabet() {
        for(int i =0 ; i < letters.length/2; ++i) {
            lettersLeft.add(letters[i]);
        }
        for(int i =letters.length/2 ; i < letters.length; ++i) {
            lettersRight.add(letters[i]);
        }
    }

    /**
     * Блокировка рандомных кнопок ввода букв.
     * <p>
     */
    public void changeBlockStateInButtons(){
        if(firstLetter == -1 && secondLetter == -1) {
            Random random = new Random();
            firstLetter = random.nextInt(0, letters.length);
            secondLetter = random.nextInt(0, letters.length);
            while (firstLetter == secondLetter) {
                secondLetter = random.nextInt(0, letters.length);
            }
        }
        letters[firstLetter].setAvailable(false);
        letters[secondLetter].setAvailable(false);
    }

    /**
     * Получение части алфавита.
     * <p>
     *
     * @param side Часть алфавита(true - первая половина, false - вторая).
     * @return Часть алфавита.
     */
    public List<Letter> getLettersLeftOrRight(boolean side) {
        if(side) {
            return lettersLeft;
        }else {
            return lettersRight;
        }
    }

    /**
     * Получение буквы алфавита которую ввел пользователь.
     * <p>
     *
     * @return Введеная буква.
     */
    public Letter getInputed() {
        for(Letter cur: letters){
            if(cur.isSelected()){
                return cur;
            }
        }
        return new Letter(' ');
    }

    //Для запуска без view
    /**
     * Ввод буквы алфавита.
     * <p>
     *
     */
    public void setInputed(int index){
        letters[index].setSelected(true);
    }
}
