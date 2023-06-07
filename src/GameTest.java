import gameLogic.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startGame() {
        Game game = new Game();
        Player[] players = game.startGame();
        assertEquals(players[0].condition, "Just created");
        assertEquals(players[1].condition, "Just created");
    }

    @Test
    void setStandartFieldSize() {
        Game game = new Game();
        game.startGame();
        Field field = game.setFieldSize(5);
        assertEquals(field.condition, "Just created with size: " + 5);
    }

    @Test
    void setTooLargeFieldSize() {
        Game game = new Game();
        game.startGame();
        Field field = game.setFieldSize(7);
        Lexicon lexicon = new Lexicon();
        Player [] players = {new Player(lexicon, new Game()), new Player(lexicon, new Game())};
        Field expected = new Field(7, players);
        assertEquals(expected.condition, field.condition);
    }

    @Test
    void setTooSmallFieldSize() {
        Game game = new Game();
        Field field = game.setFieldSize(2);
        assertEquals(null, field);
    }

    @Test
    void getFieldStartWordContent() {
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        char [] updated = game.getFieldContent()[2];
        for (int i = 0; i < updated.length; ++i) {
            assertTrue(updated[i] != ' ');
        }
    }

    @Test
    void getFullFillContent() {
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

    //Дописать
    @Test
    void findWinnerWhenAllWordsFounded(){
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
    }

    @Test
    void findWinnerWhenPlayer1Dominates(){
        String exp = "i am player winner";
        String checkResult = "game ended. player 1 is winner";
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        players[0].skipTurn();
        Point[] points = {new Point(2,1), new Point(2,2), new Point(3,2), new Point(4, 2)};
        Cell[] cells = players[0].selectCells(points);
        players[1].enterLetter('с', cells);
        players[0].skipTurn();
        Point[] points2 = {new Point(2,1), new Point(1,1), new Point(1,2), new Point(0, 2)};
        Cell[] cells2 = players[1].selectCells(points2);
        players[1].enterLetter('а', cells2);
        game.findWinner();
        String res = game.getWinner().condition;
        assertEquals(exp, res);
        assertEquals(checkResult, game.ending);
    }

    @Test
    void findWinnerWhenPlayer0Dominates(){
        String exp = "i am player winner";
        String checkResult = "game ended. player 0 is winner";
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        Point[] points = {new Point(2,1), new Point(2,2), new Point(3,2), new Point(4, 2)};
        Cell[] cells = players[0].selectCells(points);
        players[0].enterLetter('с', cells);
        players[1].skipTurn();
        Point[] points2 = {new Point(2,1), new Point(1,1), new Point(1,2), new Point(0, 2)};
        Cell[] cells2 = players[1].selectCells(points2);
        players[0].enterLetter('а', cells2);
        game.findWinner();
        String res = game.getWinner().condition;
        assertEquals(exp, res);
        assertEquals(checkResult, game.ending);
    }

    @Test
    void findWhenBothPlayersSkippedWordsFounded(){
        String checkResult = "game ended. ";
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        players[0].skipTurn();
        players[1].skipTurn();
        game.findWinner();
        Player res =  game.getWinner();
        assertEquals(null, res);
        assertEquals(checkResult, game.ending);
    }

    @Test
    void isGameFinished(){
        boolean exp = true;
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        players[0].skipTurn();
        players[1].skipTurn();
        game.findWinner();
        boolean res = game.isGameFinished();
        assertEquals(exp, res);
    }

    @Test
    void isGameNotFinished(){
        boolean exp = false;
        Game game = new Game();
        Player[] players = game.startGame();
        players[0].setFieldSize(5);
        players[0].skipTurn();
        boolean res = game.isGameFinished();
        assertEquals(exp, res);
    }
}