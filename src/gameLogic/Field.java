package gameLogic;

import java.awt.*;

public class Field {

    enum WordFound
    {
        Successs,
        WrongWord,
        NewWord
    }
    public String condition;
    private int size;
    private Player[] players;
    private int currentPlayer = 0;
    private Cell[][] cells;
    private Dictionary dictionary;
    public String dictionaryStatus = "Just created";

    public Field(int size, Player[] players) {
        this.size = size;
        this.players = players;
        this.dictionary = new Dictionary();
        buildConfiguration();
        condition = "Just created with size: " + size;
    }

    /**
     * Добавление лексикона в словарь.
     * <p>
     *
     */
    public void setLexiconToDictionary(Lexicon lexicon) {
        this.dictionary.addLexicon(lexicon);
        dictionaryStatus = this.dictionary.lexiconStatus;
    }

    /**
     * Получение символов поля.
     * <p>
     *
     * @return массив символов на поле.
     */
    public char[][] getCellsContent(){
        char[][] content = new char[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                content[i][j] = this.cells[i][j].takeLetter();
            }
        }
        return content;
    }

    private void buildConfiguration(){
        this.cells = new Cell[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                this.cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    //Крайняя верхняя строка
                    if (j == 0) {
                        this.cells[i][j].setNeighbour(this.cells[i][j + 1], 1);
                        this.cells[i][j].setNeighbour(this.cells[i + 1][j], 2);
                    } else if (j == size - 1) {
                        this.cells[i][j].setNeighbour(this.cells[i][j - 1], 3);
                        this.cells[i][j].setNeighbour(this.cells[i + 1][j], 2);
                    } else {
                        this.cells[i][j].setNeighbour(this.cells[i][j + 1], 1);
                        this.cells[i][j].setNeighbour(this.cells[i + 1][j], 2);
                        this.cells[i][j].setNeighbour(this.cells[i][j - 1], 3);
                    }
                }else if(i == size-1){
                    //Нижняя строка
                    if (j == 0) {
                        this.cells[i][j].setNeighbour(this.cells[i][j + 1], 1);
                        this.cells[i][j].setNeighbour(this.cells[i - 1][j], 0);
                    } else if (j == size - 1) {
                        this.cells[i][j].setNeighbour(this.cells[i][j - 1], 3);
                        this.cells[i][j].setNeighbour(this.cells[i - 1][j], 0);
                    } else {
                        this.cells[i][j].setNeighbour(this.cells[i][j + 1], 1);
                        this.cells[i][j].setNeighbour(this.cells[i - 1][j], 0);
                        this.cells[i][j].setNeighbour(this.cells[i][j - 1], 3);
                    }
                }else if(j == 0){
                    //Крайний левый столбец
                    if(i>0 && i <size-1){
                        this.cells[i][j].setNeighbour(this.cells[i-1][j], 0);
                        this.cells[i][j].setNeighbour(this.cells[i][j+1], 1);
                        this.cells[i][j].setNeighbour(this.cells[i+1][j], 2);
                    }
                }else if(j == size-1){
                    //Крайний правый столбец
                    if(i>0 && i <size-1){
                        this.cells[i][j].setNeighbour(this.cells[i-1][j], 0);
                        this.cells[i][j].setNeighbour(this.cells[i][j-1], 3);
                        this.cells[i][j].setNeighbour(this.cells[i+1][j], 2);
                    }
                }else {
                    //остальное
                    this.cells[i][j].setNeighbour(this.cells[i - 1][j], 0);
                    this.cells[i][j].setNeighbour(this.cells[i][j + 1], 1);
                    this.cells[i][j].setNeighbour(this.cells[i + 1][j], 2);
                    this.cells[i][j].setNeighbour(this.cells[i][j - 1], 3);
                }
            }
        }
        String startWord = dictionary.generateWordConfiguration(size);
        if(startWord.length() == size) {
            for (int i = 0; i < size; i++) {
                cells[size / 2][i].setLetter(startWord.charAt(i));
            }
        }
        this.condition = "Created Cells "+size +"on "+ size;
    }

    /**
     * Проверка является ли данная клетка свободной.
     * <p>
     *
     */
    public boolean isCellFree(Cell cell){
        return cell.isFree();
    }

    /**
     * Поиск слова по его последовательности клеток.
     * <p>
     *
     * @param subsequence последовательность клеток слова.
     * @return результат поиска.
     */
    public WordFound findWord(Cell[] subsequence){
        String possibleWord = cellSeqToString(subsequence);
        if (possibleWord != null) {
            Dictionary.WordStatus wordFind = dictionary.findWord(possibleWord);
            if (wordFind == Dictionary.WordStatus.NOTED) {
                dictionary.setWordToLexicon(possibleWord, currentPlayer);
                nextMove();
                return WordFound.Successs;
            }else if(wordFind == Dictionary.WordStatus.UNKNOWN){
                return WordFound.NewWord;
            }
        }
        return WordFound.WrongWord;
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Перевод последовательности клеток в строку.
     * <p>
     *
     * @param subsequence последовательность клеток.
     * @return строка из содержимого клеток.
     */
    public String cellSeqToString(Cell[] subsequence){
        char [] subTransform = new char[subsequence.length];
        for (int i = 0; i < subTransform.length; i++){
            subTransform[i] = subsequence[i].takeLetter();
        }
        String res = new String(subTransform);
        return res;
    }
    public void addWord(String word){this.dictionary.addWord(word);}
    public Cell chooseCell(Point cell){
        return cells[cell.y][cell.x];
    }

    /**
     * Узнать номер игрока.
     * <p>
     *
     * @return номер играка.
     */
    public int getPlayersId(Player player){
        if(players[currentPlayer] == player){
            return currentPlayer;
        }else {
            if(currentPlayer == 0){
                return 1;
            }else {
                return 0;
            }
        }
    }

    /**
     * Проверка есть ли ещё доступные клетки.
     * <p>
     *
     * @return являются ли все возможные клетки доступными.
     */
    public boolean isAllAvailableCellsSeted(){
        boolean res = false;
        for(Cell[] i: cells){
            for(Cell j: i){
                if(!j.isFree()||j.isBlocked()){
                    res = true;
                }else {
                    res = false;
                }
            }
        }
        return res;
    }
    /**
     * Переключение на следующего игрока.
     * <p>
     *
     */
    public void nextMove(){
        if(currentPlayer == players.length -1){
            currentPlayer = 0;
        }else {
            currentPlayer++;
        }
        condition = "Size: " + size + " current player: " + currentPlayer;
    }
}
