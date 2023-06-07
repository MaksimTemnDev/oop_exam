
import gameLogic.Cell;
import gameLogic.Game;
import gameLogic.Player;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    //Набор тестов для всех методов класса gameLogic.Cell
    @org.junit.jupiter.api.Test
    void isNewCellFree() {
        Cell cell = new Cell();
        boolean res = cell.isFree();
        assertEquals(true, res);
    }

    @org.junit.jupiter.api.Test
    void isCellChangedFree() {
        Cell cell = new Cell();
        cell.setLetter('а');
        cell.setLetter(' ');
        boolean res = cell.isFree();
        assertEquals(true, res);
    }

    @org.junit.jupiter.api.Test
    void isCellChangedNotFree() {
        Cell cell = new Cell();
        cell.setLetter('а');
        cell.setLetter(' ');
        cell.setLetter('k');
        boolean res = cell.isFree();
        assertEquals(false, res);
    }

    @org.junit.jupiter.api.Test
    void isCellNotFree() {
        Cell cell = new Cell();
        cell.setLetter('ь');
        boolean res = cell.isFree();
        assertEquals(false, res);
    }

    @org.junit.jupiter.api.Test
    void setLetter() {
        Cell cell = new Cell();
        char exp = 'п';
        cell.setLetter(exp);
        char res = cell.takeLetter();
        assertEquals(exp, res);
    }

    @org.junit.jupiter.api.Test
    void setNull() {
        Cell cell = new Cell();
        char exp = ' ';
        cell.setLetter(exp);
        char res = cell.takeLetter();
        assertEquals(exp, res);
    }

    @org.junit.jupiter.api.Test
    void setBlocking() {
        Cell cell = new Cell();
        cell.setBlocked(true);
        boolean exp = true;
        boolean res = cell.isBlocked();
        assertEquals(exp, res);
    }

    //Тесты на соседей
    @org.junit.jupiter.api.Test
    void leftTopNeighbors(){
        int size = 5;
        Cell[][] cells = new Cell[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    //Крайняя верхняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(i == size-1){
                    //Нижняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(j == 0){
                    //Крайний левый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j+1], 1);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else if(j == size-1){
                    //Крайний правый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j-1], 3);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else {
                    //остальное
                    cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    cells[i][j].setNeighbour(cells[i][j + 1], 1);
                    cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    cells[i][j].setNeighbour(cells[i][j - 1], 3);
                }
            }
        }
        assertEquals(cells[0][0].getNeighbourDown(), cells[1][0]);
        assertEquals(cells[0][0].getNeighbourRight(), cells[0][1]);
        assertTrue(cells[0][0].getNeighbourUp() == null);
        assertTrue(cells[0][0].getNeighbourLeft() == null);
    }

    @org.junit.jupiter.api.Test
    void rightTopNeighbors(){
        int size = 5;
        Cell[][] cells = new Cell[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    //Крайняя верхняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(i == size-1){
                    //Нижняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(j == 0){
                    //Крайний левый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j+1], 1);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else if(j == size-1){
                    //Крайний правый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j-1], 3);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else {
                    //остальное
                    cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    cells[i][j].setNeighbour(cells[i][j + 1], 1);
                    cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    cells[i][j].setNeighbour(cells[i][j - 1], 3);
                }
            }
        }
        assertEquals(cells[0][size-1].getNeighbourDown(), cells[1][size-1]);
        assertEquals(cells[0][size-1].getNeighbourLeft(), cells[0][size-2]);
        assertTrue(cells[0][size-1].getNeighbourUp() == null);
        assertTrue(cells[0][size-1].getNeighbourRight() == null);
    }

    @org.junit.jupiter.api.Test
    void leftDownNeighbors(){
        int size = 5;
        Cell[][] cells = new Cell[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    //Крайняя верхняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(i == size-1){
                    //Нижняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(j == 0){
                    //Крайний левый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j+1], 1);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else if(j == size-1){
                    //Крайний правый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j-1], 3);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else {
                    //остальное
                    cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    cells[i][j].setNeighbour(cells[i][j + 1], 1);
                    cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    cells[i][j].setNeighbour(cells[i][j - 1], 3);
                }
            }
        }
        assertEquals(cells[size-1][0].getNeighbourUp(), cells[size-2][0]);
        assertEquals(cells[size-1][0].getNeighbourRight(), cells[size-1][1]);
        assertTrue(cells[size-1][0].getNeighbourDown() == null);
        assertTrue(cells[size-1][0].getNeighbourLeft() == null);
    }

    @org.junit.jupiter.api.Test
    void rightDownNeighbors(){
        int size = 5;
        Cell[][] cells = new Cell[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    //Крайняя верхняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(i == size-1){
                    //Нижняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(j == 0){
                    //Крайний левый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j+1], 1);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else if(j == size-1){
                    //Крайний правый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j-1], 3);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else {
                    //остальное
                    cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    cells[i][j].setNeighbour(cells[i][j + 1], 1);
                    cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    cells[i][j].setNeighbour(cells[i][j - 1], 3);
                }
            }
        }
        assertEquals(cells[size-1][size-1].getNeighbourUp(), cells[size-2][size-1]);
        assertEquals(cells[size-1][size-1].getNeighbourLeft(), cells[size-1][size-2]);
        assertTrue(cells[size-1][size-1].getNeighbourDown() == null);
        assertTrue(cells[size-1][size-1].getNeighbourRight() == null);
    }

    @org.junit.jupiter.api.Test
    void getMidNeighbors(){
        int size = 5;
        Cell[][] cells = new Cell[size][size];
        for (int i =0; i<size; i++){
            for (int j =0; j<size; j++){
                cells[i][j] = new Cell();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    //Крайняя верхняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i + 1][j], 2);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(i == size-1){
                    //Нижняя строка
                    if (j == 0) {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else if (j == size - 1) {
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    } else {
                        cells[i][j].setNeighbour(cells[i][j + 1], 1);
                        cells[i][j].setNeighbour(cells[i - 1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j - 1], 3);
                    }
                }else if(j == 0){
                    //Крайний левый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j+1], 1);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else if(j == size-1){
                    //Крайний правый столбец
                    if(i>0 && i <size-1){
                        cells[i][j].setNeighbour(cells[i-1][j], 0);
                        cells[i][j].setNeighbour(cells[i][j-1], 3);
                        cells[i][j].setNeighbour(cells[i+1][j], 2);
                    }
                }else {
                    //остальное
                    cells[i][j].setNeighbour(cells[i - 1][j], 0);
                    cells[i][j].setNeighbour(cells[i][j + 1], 1);
                    cells[i][j].setNeighbour(cells[i + 1][j], 2);
                    cells[i][j].setNeighbour(cells[i][j - 1], 3);
                }
            }
        }
        assertEquals(cells[2][2].getNeighbourUp(), cells[1][2]);
        assertEquals(cells[2][2].getNeighbourRight(), cells[2][3]);
        assertEquals(cells[2][2].getNeighbourLeft(), cells[2][1]);
        assertEquals(cells[2][2].getNeighbourDown(), cells[3][2]);
    }
}