package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public String ending = "not finished yet";
    private Player[] players;
    private Player winner;
    private Field field;
    private int fieldSize;
    private boolean gameFinished;

    /**
     * Создание игроков.
     * <p>
     *
     * @param lexicons лексиконы для игроков.
     */
    private void createPlayers(Lexicon[] lexicons){
        this.players = new Player[]{new Player(lexicons[0], this), new Player(lexicons[1], this)};
    }

    /**
     * Создание шаблона игры.
     * <p>
     *
     * @return Массив игроков.
     */
    public Player[] startGame(){
        Lexicon[] lexicons = {new Lexicon(), new Lexicon()};
        createPlayers(lexicons);
        return players;
    }

    public Field setFieldSize(int size){
        if(size >=5){
            this.fieldSize = size;
            buildField();
            this.players[0].setField(this.field);
            this.players[1].setField(this.field);
            return this.field;
        }
        return null;
    }

    /**
     * Поиск игрока победителя.
     * <p>
     *
     */
    public void findWinner(){
        Player playerFirst = players[0];
        Player playerSecond = players[1];
        ending = "game ended. ";
        if(playerFirst.getCurrentScore() > playerSecond.getCurrentScore()){
            winner = playerFirst;
            ending += "player 0 is winner";
        }else if(playerSecond.getCurrentScore() > playerFirst.getCurrentScore()){
            winner = playerSecond;
            ending += "player 1 is winner";
        }
        gameFinished = true;
    }

    public boolean isGameFinished(){
        boolean request = this.gameFinished;
        return request;
    }

    public Player getWinner() {
        if(winner != null) {
            winner.condition = "i am player winner";
        }
        return winner;
    }

    public int winnerId(){
        try {
            if (winner != null) {
                return this.field.getPlayersId(winner);
            } else {
                throw new IllegalArgumentException("Победитель ещё не определен");
            }
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
        return -1;
    }

    public char[][] getFieldContent(){
        return this.field.getCellsContent();
    }
    private void buildField(){
        this.field = new Field(this.fieldSize, players);
    }

    public int getCurrentPlayerId(){
        return this.field.getCurrentPlayer();
    }

    /**
     * Проверка состояния игрового процесса(должна ли игра продолжаться).
     * <p>
     *
     */
    public void shouldGameConitnue(){
        boolean isAllSkiped = players[0].getSkips() && players[1].getSkips();
        boolean isFieldFull = true;
        char[][] fieldContent = getFieldContent();
        for (int i =0; i<fieldContent.length; i++){
            for (int j =0; j<fieldContent.length; j++){
                if(fieldContent[i][j] == ' '){
                    isFieldFull = false;
                    j = fieldContent.length;
                    i = fieldContent.length;
                }
            }
        }
        if(isFieldFull || isAllSkiped || field.isAllAvailableCellsSeted()){
            findWinner();
        }
    }

    public Field getField(){return this.field;}

    /**
     * Метод для запуска игрового цикла
     * <p>
     *
     */
    public void playGame(){
        Alphabet alphabet = new Alphabet();
        //Создадим представление игрового процесса
        GameView frame = new GameView("Игра", alphabet, this);
        //выберем режим игры (размер поля + сложность)
        int mode = frame.showStartMessage(frame);
        boolean highDifficulty = frame.convertToDifficulty(mode);
        startGame();
        //Установим игровое поле
        players[0].setFieldSize(frame.convertToSize(mode));
        Field field = this.getField();
        frame.setFieldParams(field);
        //Если сложность игры повышенная
        if(highDifficulty){
            alphabet.changeBlockStateInButtons();
            frame.blockLetters();
            frame.blockCells(blockedCells());
            frame.repaint();
        }
        while (isGameFinished() == false){
            //обновить счёт и номер текущего игрока
            frame.updateDataDisplay(players[0].getCurrentScore(), players[1].getCurrentScore(), getCurrentPlayerId());
            //получить список выбранных клеток
            ArrayList<Cell> cells = frame.getSelectedCells();
            //получить выбранную клетку
            char inputedLetter = getletter(alphabet);
            if(cells != null) {
                if (cells.size() > 2 && inputedLetter != ' ') {
                    Player.LetterInputRes res = players[getCurrentPlayerId()].enterLetter(inputedLetter, cells.toArray(new Cell[0]));
                    //Если обноружено новое слово
                    if(res == Player.LetterInputRes.NewWord){
                        if(frame.showNewWordDialog() == 0){
                            if(players[0].notAddedWord != " "){
                                field.addWord(players[0].notAddedWord);
                                players[0].enterLetter(inputedLetter, cells.toArray(new Cell[0]));
                                players[0].notAddedWord = " ";
                            }else {
                                field.addWord(players[1].notAddedWord);
                                players[1].enterLetter(inputedLetter, cells.toArray(new Cell[0]));
                                players[1].notAddedWord = " ";
                            }
                            frame.resetDefault();
                        }
                    }
                    //Очистить поле и записать полученное слово
                    frame.clearSelect();
                    frame.inputNewWordToList(getCurrentPlayerId(), players[0].getWordsList(), players[1].getWordsList());
                }
            }
        }
        //обновить счёт и номер текущего игрока
        frame.updateDataDisplay(players[0].getCurrentScore(), players[1].getCurrentScore(), getCurrentPlayerId());
        //Сообщить об окончании игры
        frame.showEndMessege(winnerId());
    }

    /**
     * Пропуск хода.
     * <p>
     */
    protected void turnSkip(){
        players[getCurrentPlayerId()].skipTurn();
    }

    /**
     * Поиск введеной буквы.
     * <p>
     *
     * @return введеная буква.
     */
    private char getletter(Alphabet alphabet){
        Letter inputed = alphabet.getInputed();
        char res = inputed.getLetter();
        inputed.setSelected(false);
        if(res != ' '){
            return res;
        }else {
            return ' ';
        }
    }

    /**
     * Блокировка клеток поля.
     * <p>
     *
     * @return список заблокированных клеток.
     */
    private List<Cell> blockedCells(){
        Random random = new Random();
        List<Cell> cells = new ArrayList<>();
        Cell selected = this.field.chooseCell(new Point(random.nextInt(0, fieldSize), random.nextInt(0, fieldSize)));
        cells.add(selected);
        selected.setBlocked(true);
        Cell secondCell = this.field.chooseCell(new Point(random.nextInt(0, fieldSize), random.nextInt(0, fieldSize)));
        while (secondCell == selected){
            secondCell = this.field.chooseCell(new Point(random.nextInt(0, fieldSize), random.nextInt(0, fieldSize)));
        }
        cells.add(secondCell);
        secondCell.setBlocked(true);
        return cells;
    }
}
