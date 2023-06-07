package gameLogic;

public class Letter {
    private char letter;

    private boolean isSelected = false;
    private boolean isAvailable = true;
    public Letter(char letter) {
        this.letter = letter;
    }

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public char getLetter() {
        return letter;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
