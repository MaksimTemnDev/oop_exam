package gameLogic;

import gameLogic.Cell;
import gameLogic.Field;
import gameLogic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

// Виджет для отрисовки поля и всего того, что на нем. Так же управляет робот с помощью клавиатуры
public class GameFieldView extends JPanel {

    private final Field _field;

    ArrayList<Cell> selectedCells = new ArrayList<>();

    private int size;
    private LinkedList<CellWidget> cells = new LinkedList<>();

    private ArrayList selescted = new ArrayList<CellWidget>();
    public GameFieldView(Field field, int size) {
        _field = field;

        this.size = size;

        Dimension fieldDimension = new Dimension(340, 340);

        setLayout(new GridLayout(size, size, 0, 0));

        for(int i = 0; i<size; i++){
            for (int j = 0; j<size; j++){
                CellWidget current = new CellWidget(field.chooseCell(new Point(j, i)));
                cells.add(current);
                add(current);
            }
        }

    }

    public void clearSelectList(){
        selectedCells.clear();
    }
    public ArrayList<Cell> getSelectedCells(){
        Cell privious = new Cell();
        if(selectedCells.size()>0) {
            privious = selectedCells.get(selectedCells.size() - 1);
        }
        //Для всех клеток поля
        for(int i =0; i<cells.size(); i++){
            //если клетка выбрана
            if(cells.get(i).isSelected()){
                CellWidget potencial = cells.get(i);
                Cell potencialCell = potencial.getCell();
                if(potencialCell.hasNotFreeNeighbour()){
                    //Если уже есть клетки
                    if(selectedCells.size()>0){
                        boolean addAvailable = false;
                        //не содержится в известнй последовательности и является соседом последней из известных клеток
                        if(!selectedCells.contains(potencialCell) && privious.isNeighbour(potencialCell)){
                            addAvailable = true;
                        }
                        if(addAvailable){
                            selectedCells.add(potencialCell);
                        }else if (!selectedCells.contains(potencialCell)){
                            potencial.unselect();
                        }

                    }else {
                        selectedCells.add(potencialCell);
                    }
                }else {
                    //отмена выбора клетки
                    potencial.unselect();
                }
            }
        }
        cellsStateCheck();
        if(isAllNeighbors()) {
            return selectedCells;
        }else {
            cellsRepaintDefolt();
            return null;
        }
    }

    public void cellsRepaintDefolt(){
        for(int i = 0; i < cells.size(); i ++){
            CellWidget current = cells.get(i);
            if(current.isSelected()){
                current.unselect();
            }
        }
        selectedCells.clear();
    }

    private boolean isAllNeighbors(){
        if(selectedCells.size()>1) {
            for (int i = 1; i < selectedCells.size(); i++) {
                if (!selectedCells.get(i).isNeighbour(selectedCells.get(i-1))){
                    return false;
                }
            }
        }
        return true;
    }

    public void blockCells(Cell cell){
        for(CellWidget current: cells){
            if(current.getCell() == cell){
                current.block();
            }
        }
    }

    //в случае отмены какой либо из выбранных клеток
    private void cellsStateCheck(){
        for (int i = 0; i< cells.size(); i++){
            CellWidget current = cells.get(i);
            for (int g = 0; g< selectedCells.size(); g++) {
                if (current.getCell() ==selectedCells.get(g) && !current.isSelected()){
                    while (g<selectedCells.size()) {
                        selectedCells.remove(g);
                        g++;
                    }
                }
            }
        }
    }
}
