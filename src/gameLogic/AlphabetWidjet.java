package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AlphabetWidjet extends JPanel {
    private Alphabet alphabet;

    private LetterButton[] letters;

    private List<Letter> allLetters;

    /**
     * Отображение части алфавита.
     * <p>
     *
     */
    public AlphabetWidjet(Alphabet alphabet, boolean side) {
        this.alphabet = alphabet;
        allLetters = alphabet.getLettersLeftOrRight(side);
        letters = new LetterButton[allLetters.size()];
        for (int i = 0; i < allLetters.size(); i++){
            letters[i] = new LetterButton(allLetters.get(i));
            if(!allLetters.get(i).isAvailable()){
                letters[i].changeBlocking();
            }
        }
        setLayout(new GridLayout(3,6));
        for (LetterButton btn: letters){
            add(btn);
        }
    }

    /**
     * Отображение блокировки кнопок ввода букв алфавита.
     * <p>
     *
     */
    public void blockAnavailable(){
        for (int i = 0; i<allLetters.size(); i++) {
            if(!allLetters.get(i).isAvailable()){
                letters[i].changeBlocking();
            }
        }
    }
}
