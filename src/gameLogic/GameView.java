package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame {

    private GameFieldView gameFieldView;
    private int mode;
    private Alphabet alphabet;
    private Game game;
    private DefaultListModel<String> pl1words = new DefaultListModel<>();
    private DefaultListModel<String> pl2words = new DefaultListModel<>();

    private int oneWordsSize = 0;
    private int twoWordsSize = 0;
    private JLabel playerScore = new JLabel();
    private JLabel player2Score = new JLabel();
    private JLabel currentPlayer = new JLabel();
    private AlphabetWidjet leftPart;
    private AlphabetWidjet rightPart;
    private JPanel center = new JPanel(new BorderLayout());
    public GameView(String title, Alphabet alphabet, Game game) throws HeadlessException {
        super(title);
        this.game = game;
        this.alphabet = alphabet;
        setSize(840, 360);
        setResizable(false);
        JPanel verticalOne = new JPanel(new BorderLayout());
        JPanel verticalTwo = new JPanel(new BorderLayout());
        pl1words = new DefaultListModel<>();
        pl2words = new DefaultListModel<>();
        JList<String> leftWordsView =new JList<>(pl1words);
        JList<String> rightWordsView =new JList<>(pl2words);
        JScrollPane wordsOne = new JScrollPane(leftWordsView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane wordsTwo = new JScrollPane(rightWordsView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        verticalOne.add(playerScore, BorderLayout.NORTH);
        verticalTwo.add(player2Score, BorderLayout.NORTH);
        center.add(currentPlayer, BorderLayout.NORTH);
        JPanel framePanel = new JPanel(new FlowLayout());
        //Используем отображение класса Alphabet
        leftPart = new AlphabetWidjet(this.alphabet, true);
        rightPart = new AlphabetWidjet(this.alphabet, false);
        verticalOne.add(leftPart, BorderLayout.SOUTH);
        verticalOne.add(wordsOne, BorderLayout.CENTER);
        framePanel.add(verticalOne);
        JButton skip = new JButton("Пропустить ход");
        setLocation(new Point(320, 230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(framePanel);
        skip.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                game.turnSkip();
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
        center.add(skip, BorderLayout.SOUTH);
        framePanel.add(center);
        verticalTwo.add(rightPart, BorderLayout.SOUTH);
        verticalTwo.add(wordsTwo, BorderLayout.CENTER);
        framePanel.add(verticalTwo);
    }

    public void setFieldParams(Field field){
        int size = convertToSize(mode);
        gameFieldView = new GameFieldView(field, size);
        if(size == 5) {
            gameFieldView.setPreferredSize(new Dimension(240,240));
        }else {
            gameFieldView.setPreferredSize(new Dimension(260,260));
        }
        center.add(gameFieldView, BorderLayout.CENTER);
    }

    public int showStartMessage(JFrame frame){
        Object[] options = {"5 на 5",
                "5 на 5 сложный", "7 на 7", "7 на 7 сложный"};
        mode = JOptionPane.showOptionDialog(frame,
                "Выберите режим игры",
                "Новая игра",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return mode;
    }

    public int convertToSize(int mode){
        if(mode > 1 ){
            return 7;
        }else {
            return 5;
        }
    }

    public boolean convertToDifficulty(int mode){
        if(mode == 1 || mode == 3){
            return true;
        }else {
            return false;
        }
    }

    public void blockLetters(){
        leftPart.blockAnavailable();
        rightPart.blockAnavailable();
    }

    public void updateDataDisplay(int pl1,int pl2, int current){
        repaint();
        playerScore.setText("Игрок 1    счёт:" + pl1);
        player2Score.setText("Игрок 2    счёт:" + pl2);
        currentPlayer.setText("Ходит игрок " + String.valueOf(current+1));
    }

    public ArrayList<Cell> getSelectedCells(){
        return  gameFieldView.getSelectedCells();
    }

    public void resetDefault(){
        gameFieldView.repaint();
        this.gameFieldView.cellsRepaintDefolt();
        repaint();
    }

    public int showNewWordDialog(){
        Object[] options = {"Да",
                "Нет"};
        int  doType = JOptionPane.showOptionDialog(this,
                "Хотите добавить новое слово в словарь?",
                "Новое слово",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return doType;
    }

    public void clearSelect(){
        this.gameFieldView.clearSelectList();
        resetDefault();
    }

    public void showEndMessege(int winnerId){
        if(winnerId == -1){
            JOptionPane.showMessageDialog(this, "Ничья!");
        }else {
            JOptionPane.showMessageDialog(this, "Победил игрок " + (++winnerId));
        }
        dispose();
    }

    public void inputNewWordToList(int player, List<String> words1, List<String> words2){
        if(player != 0 && words1.size() > oneWordsSize){
            pl1words.addElement(words1.get(words1.size() - 1));
            oneWordsSize++;
        }else if(words2.size() >twoWordsSize){
            pl2words.addElement(words2.get(words2.size() - 1));
            twoWordsSize++;
        }
    }

    public void blockCells(List<Cell> cells){
        for(Cell cell: cells){
            gameFieldView.blockCells(cell);
        }
    }
}
