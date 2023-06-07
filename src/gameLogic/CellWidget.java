package gameLogic;

import gameLogic.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

// Виджет для отрисовки ячейки и всего, что на ней располагается
public class CellWidget extends JButton {

    public static final int CELL_SIZE = 55;
    private static final Color SELECTED_COLOR = new Color(230, 140, 230);

    private static final Color BACKGROUND_COLOR = new Color(187, 87, 189);

    private static final Color BLOCKED_COLOR = new Color(120, 50, 90);
    private static final Color RECT_COLOR = new Color(54, 51, 54);
    private static final Color FONT_COLOR = new Color(90, 194, 112);

    private final Cell _cell;

    private char letter = ' ';

    private boolean isSelected = false;

    public CellWidget(Cell cell) {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(BACKGROUND_COLOR);

        _cell = cell;

        setToolTipText("");

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!_cell.isBlocked()) {
                    if (isSelected) {
                        isSelected = false;
                        setBackground(BACKGROUND_COLOR);
                    } else {
                        setBackground(SELECTED_COLOR);
                        isSelected = true;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void unselect(){
        this.isSelected = false;
        setBackground(BACKGROUND_COLOR);
    }

    public Cell getCell(){
        return this._cell;
    }

    public void block(){
        setBackground(BLOCKED_COLOR);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gr2d = (Graphics2D) g;
        gr2d.setStroke( new BasicStroke(2) );
        gr2d.setPaint( RECT_COLOR );

        gr2d.drawRect(0, 0, CELL_SIZE, CELL_SIZE);

        if(g != null) {
            letter = _cell.takeLetter();

            gr2d.setColor( FONT_COLOR );
            gr2d.setFont( new Font( "Arial", Font.BOLD, 27 ));

            FontMetrics fm = g.getFontMetrics();

            int msgWidth = fm.stringWidth(String.valueOf(1));
            int msgHeight = fm.getHeight();

            gr2d.drawString(String.valueOf(letter), (CELL_SIZE - msgWidth*2)/2, CELL_SIZE/2 + msgHeight/4- 2 );
        }
    }
}
