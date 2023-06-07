package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LetterButton extends JButton {

    private boolean isClicked = false;
    private Letter letter;

    private boolean blocked = false;

    public LetterButton(Letter letter) {
        super(String.valueOf(letter.getLetter()));
        setSize(15,15);
        this.letter = letter;
        setText(String.valueOf(String.valueOf(letter.getLetter()).charAt(0)));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(letter.isAvailable()) {
                    isClicked = true;
                    letter.setSelected(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public char getLetter(){
        char res = ' ';
        if(isClicked && !blocked){
            res = getText().charAt(0);
            setUnClicked();
        }
        return res;
    }

    private void setUnClicked(){
        this.isClicked = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    //метод управления блокировкой кнопки
    public void changeBlocking(){
        if(this.blocked == false) {
            this.blocked = true;
            setEnabled(false);
        }else {
            this.blocked = false;
            this.setEnabled(true);
        }
    }
}
