import gameLogic.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void skipTurn() {
        String exp = "current player id: Size: 5 current player: 1";
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        players[0].skipTurn();
        String res = players[0].condition;
        assertEquals(exp, res);
    }

    @Test
    void fieldSizeSetting() {
        String exp = "new field added: Just created with size: 5";
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        String res = players[1].condition;
        assertEquals(exp, res);
    }

    @Test
    void fieldWrongSizeSetting() {
        String exp = "Tried to input wrong field size: 2";
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(2);
        String res = players[1].condition;
        assertEquals(exp, res);
    }

    @Test
    void getCurrentScore() {
        int exp = 0;
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        int res = players[1].getCurrentScore();
        assertEquals(exp, res);
    }
    //Дописать тесты позже

    @Test
    void hasNoNeugborsCellInSequence() {
        try {
        Cell [] exp = {new Cell(), new Cell(), new Cell()};
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        Point[] points = {new Point(0,0), new Point(0,1), new Point(1,1)};
        Cell [] res = players[1].selectCells(points);
        }
        catch (Exception e) {
            assertEquals("here cell 0 which not nearby to some other letter", e.getMessage());
        }
    }

    @Test
    void takeSequenceWithLetters() {
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        char []exp = game.getFieldContent()[2];
        Point[] points = {new Point(0,2), new Point(1,2), new Point(2,2)};
        Cell[] res = players[1].selectCells(points);
        for(int i =0; i<res.length; i++){
            assertTrue(res[i].takeLetter() == exp[i]);
        }
    }

    @Test
    void SetLetterToFreeCell() {
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        char [][] exp = game.getFieldContent();
        exp[1][2] = 'с';
        Point[] points = {new Point(2,1), new Point(2,2), new Point(3,2), new Point(4, 2)};
        Cell[] res = players[1].selectCells(points);
        Player.LetterInputRes callback = players[1].enterLetter('с', res);
        Player.LetterInputRes expectedCallback = Player.LetterInputRes.Success;
        char [][] output = game.getFieldContent();
        assertEquals(expectedCallback, callback);
        for (int i =0; i<output.length; i++){
            for (int j =0; j<output.length; j++){
                assertEquals(exp[i][j], output[i][j]);
            }
        }
    }

    @Test
    void SetLetterToOccupiedCell() {
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        Point[] points = {new Point(0,2), new Point(1,2), new Point(2,2)};
        Cell[] res = players[1].selectCells(points);
        Player.LetterInputRes callback = players[1].enterLetter('u', res);
        Player.LetterInputRes expectedCallback = Player.LetterInputRes.WrongInput;
        assertEquals(expectedCallback, callback);
    }

    @Test
    void SetLetterWithoutWord() {
        Game game = new Game();
        Player[] players = game.startGame();
        players[1].setFieldSize(5);
        Point[] points = {new Point(1,3), new Point(1,2), new Point(0,2)};
        Cell[] res = players[1].selectCells(points);
        Player.LetterInputRes callback = players[1].enterLetter('u', res);
        Player.LetterInputRes expectedCallback = Player.LetterInputRes.NewWord;
        assertEquals(expectedCallback, callback);
    }
}