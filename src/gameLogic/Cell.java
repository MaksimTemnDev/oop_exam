package gameLogic;

public class Cell {
    private char letter;
    private Cell neighbourLeft;
    private Cell neighbourUp;
    private Cell neighbourRight;
    private Cell neighbourDown;

    private boolean blocked;

    /**
     * Получение буквы из клетки.
     * <p>
     *
     * @return буква из клетки.
     */
    public char takeLetter(){
        char res = this.letter;
        return res;
    }

    public Cell() {
        blocked = false;
        letter = ' ';
    }

    /**
     * Проверка является ли данная клетка соседом по отношению к выбранной.
     * <p>
     *
     * @param searched выбранная клетка.
     * @return является ли соседом.
     */
    public boolean isNeighbour(Cell searched){
        if(this.neighbourLeft == searched || this.neighbourRight == searched || this.neighbourUp == searched || this.neighbourDown == searched){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Установка соседа.
     * <p>
     *
     * @param neighbour клетка сосед.
     * @param direct направление на котором находится сосед.
     */
    public void setNeighbour(Cell neighbour, int direct) {
        if(neighbour != null) {
            switch (direct) {
                case 0:
                    this.neighbourUp = neighbour;
                    break;
                case 1:
                    this.neighbourRight = neighbour;
                    break;
                case 2:
                    this.neighbourDown = neighbour;
                    break;
                case 3:
                    this.neighbourLeft = neighbour;
                    break;
                default:
                    throw new IllegalArgumentException("incorrect neighbor direction (should be integer between 0 and 3)");
            }
        }
    }

    public Cell getNeighbourLeft() {
        return neighbourLeft;
    }

    public Cell getNeighbourUp() {
        return neighbourUp;
    }

    public Cell getNeighbourRight() {
        return neighbourRight;
    }

    public Cell getNeighbourDown() {
        return neighbourDown;
    }

    public boolean isFree(){
        boolean res = this.letter == ' ';
        return res;
    }

    public void setLetter(char input){
            this.letter = input;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked(){
        return this.blocked;
    }

    /**
     * Имеется ли у клетки не свободный соседй.
     * <p>
     *
     * @return есть ли не свободный сосед.
     */
    public boolean hasNotFreeNeighbour(){
        if(neighbourRight != null){
            if (!neighbourRight.isFree()){
                return true;
            }
        }
        if(neighbourLeft != null){
            if (!neighbourLeft.isFree()){
                return true;
            }
        }
        if (neighbourUp != null) {
            if(!neighbourUp.isFree()){
                return true;
            }
        }
        if(neighbourDown != null){
            if(!neighbourDown.isFree()){
                return true;
            }
        }
        return false;
    }
}
