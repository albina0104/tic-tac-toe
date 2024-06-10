import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JLabel {
    private final int rowNumber, columnNumber;
    private CellState cellState = CellState.EMPTY;
    private static final Font font = new Font("SansSerif", Font.BOLD, 64);
    private static boolean isPlayer1Turn = true;
    static final Color color1 = new Color(92, 37, 255);
    static final Color color2 = new Color(6, 193, 5);

    public Cell(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        setOpaque(true);
        setBackground(new Color(188, 221, 255));
        setForeground(color1);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setPreferredSize(new Dimension(100, 100));
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setFont(font);
        addMouseListener(new PlayerTurnListener());
    }

    private class PlayerTurnListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            switch (cellState) {
                case X:
                case O:
                    return;
                case EMPTY:
                    if (isPlayer1Turn) {
                        cellState = CellState.X;
                        setText("X");
                    } else {
                        cellState = CellState.O;
                        setForeground(color2);
                        setText("O");
                    }
                    boolean isGameOver = TicTacToe.checkIfGameOver((Cell) e.getSource());
                    if (!isGameOver) {
                        isPlayer1Turn = !isPlayer1Turn;
                    }
            }
        }
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public CellState getCellState() {
        return cellState;
    }

    public static boolean getIsPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public static void setIsPlayer1Turn(boolean isPlayer1Turn) {
        Cell.isPlayer1Turn = isPlayer1Turn;
    }
}
