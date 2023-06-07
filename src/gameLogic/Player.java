package gameLogic;

import gameLogic.Lexicon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    public String condition;

    public String notAddedWord = " ";
    private Lexicon lexicon;
    private Field field;
    private Game game;
    private boolean lastTurnSkiped = false;
    public enum LetterInputRes{
        Success,
        NewWord,
        WrongInput,
        UnexpectedFall
    }
    public Player(Lexicon lexicon, Game game) {
        this.lexicon = lexicon;
        this.game = game;
        condition = "Just created";
    }

    /**
     * Ввод буквы в последовательность его клеток.
     * <p>
     *
     * @param letter вводимая буква.
     * @param sequence последовательность клеток слова.
     * @return результат ввода.
     */
    public LetterInputRes enterLetter(char letter, Cell[] sequence){
        if (this.game.isGameFinished() == false) {
            Cell[] seqCopy = cellsCopy(sequence);
            int inCell = 0;
            //Если клетка не занята
            //Случай когда клетка не в начале последовательности
            for (int i = 0; i < seqCopy.length; i++) {
                if (seqCopy[i].isFree()) {
                    inCell = i;
                    i = seqCopy.length;
                }
            }
            if (field.isCellFree(sequence[inCell]) && !manySpaces(seqCopy)) {
                seqCopy[inCell].setLetter(letter);
                Field.WordFound search = field.findWord(seqCopy);
                if (search == Field.WordFound.Successs) {
                    sequence[inCell].setLetter(letter);
                    this.lastTurnSkiped = false;
                    this.game.shouldGameConitnue();
                    return LetterInputRes.Success;
                } else if(search == Field.WordFound.NewWord){
                    //Сдучай когда игрок не нашел новое слово
                    notAddedWord = field.cellSeqToString(seqCopy);
                    return LetterInputRes.NewWord;
                }
            } else {
                //Сдучай когда клетка занята или в слове много пробелов
                return LetterInputRes.WrongInput;
            }
        }
        return LetterInputRes.UnexpectedFall;
    }

    public boolean getSkips(){
        return this.lastTurnSkiped;
    }

    /**
     * Получение клеток по введённым координатом.
     * <p>
     *
     * @param selected последовательность координат клеток слова.
     * @return массив клеток.
     */
    public Cell[] selectCells(Point[] selected){
        Cell [] cells = new Cell[selected.length];
        for(int i =0; i< selected.length; i++) {
            cells[i] = field.chooseCell(selected[i]);
            if(!cells[i].hasNotFreeNeighbour()){
                throw new IllegalArgumentException("here cell "+i+" which not nearby to some other letter");
            }
            if(i>0 && !cells[i-1].isNeighbour(cells[i])){
                throw new IllegalArgumentException("letter number " + i + " is not a neighbor for previous letter(");
            }
        }
        return cells;
    }

    public void setField(Field field){
        if (field != null){
            this.field = field;
            this.field.setLexiconToDictionary(this.lexicon);
        }
    }

    /**
     * Пропуск хода.
     * <p>
     *
     */
    public void skipTurn(){
        if(this.game.isGameFinished() == false) {
            field.nextMove();
            this.lastTurnSkiped = true;
            condition = "current player id: " + field.condition;
            this.game.shouldGameConitnue();
        }
    }

    public void setFieldSize(int size){
        Field newField = this.game.setFieldSize(size);
        if (newField != null){
            condition = "new field added: " + this.field.condition;
        }
        else {
            condition = "Tried to input wrong field size: "+ size;
        }
    }

    public int getCurrentScore(){
        return this.lexicon.getScore();
    }

    //Проверка на наличие нескольких пропущенных букв в слове
    private boolean manySpaces(Cell[] seq){
        int spacers = 0;
        for (Cell current: seq) {
            if(current.takeLetter() == ' '){
                spacers++;
            }
        }
        if(spacers>1){
            return true;
        }else {
            return false;
        }
    }

    //Список всех слов по лексикону игрока
    public List<String>getWordsList(){
        return this.lexicon.showAll();
    }

    private Cell[] cellsCopy(Cell [] defolt){
        List<Cell> output = new ArrayList<>();
        for(int i = 0; i < defolt.length; ++i){
            output.add(new Cell());
            output.get(i).setLetter(defolt[i].takeLetter());
        }
        Cell res[] = output.toArray(new Cell[0]);
        return res;
    }
}
