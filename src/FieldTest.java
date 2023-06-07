import gameLogic.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void setLexiconToDictionary() {
        String exp = "created lexicon0: []";
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        field.setLexiconToDictionary(lexicon);
        String res = field.dictionaryStatus;
        assertEquals(exp, res);
    }

    @Test
    void getCellsContent() {
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        char [][] res = field.getCellsContent();
        for (int i =0; i<5; i++){
            for (int j =0; j<5; j++){
                if(i!=2) {
                    assertEquals(' ', res[i][j]);
                }
            }
        }
    }

    @Test
    void isCellFree() {
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        boolean res = field.isCellFree(field.chooseCell(new Point(2,2)));
        assertTrue(res == false);
    }

    @Test
    void isCellNotFree() {
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        boolean res = field.isCellFree(field.chooseCell(new Point(2,1)));
        assertTrue(res == true);
    }

    @Test
    void findWord() {
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        Point[] points = {new Point(2,1), new Point(2,2), new Point(3,2), new Point(4, 2)};
        Cell[] res = players[1].selectCells(points);
        Player.LetterInputRes callback = players[1].enterLetter('с', res);
        Player.LetterInputRes expectedCallback = Player.LetterInputRes.Success;
        assertEquals(expectedCallback, callback);
    }

    @Test
    void chooseOccupiedCell() {
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        Cell res = field.chooseCell(new Point(1,2));
        assertTrue(res.isFree() == false);
        assertTrue(res.takeLetter()!= ' ');
    }

    @Test
    void chooseFreeCell() {
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        Cell res = field.chooseCell(new Point(1,1));
        assertTrue(res.isFree() == true);
    }

    @Test
    void nextMove() {
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field field = new Field(5, players);
        String oldCondition = field.condition;
        field.nextMove();
        String currentCondition = field.condition;
        assertTrue(oldCondition != currentCondition);
    }
}