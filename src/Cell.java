import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JLabel {
    private final int rowNumber, columnNumber;
    private CellState cellState = CellState.EMPTY;
    private static final Font font = new Font("SansSerif", Font.BOLD, 64);
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
            if (TicTacToe.isComputerTurn()) return;

            switch (cellState) {
                case X:
                case O:
                    return;
                case EMPTY:
                    occupyCell();
                    boolean isGameOver = TicTacToe.checkIfGameOver((Cell) e.getSource());
                    if (!isGameOver) {
                        TicTacToe.setIsPlayer1Turn(!TicTacToe.getIsPlayer1Turn());
                        if (TicTacToe.isComputerTurn()) {
                            TicTacToe.getComputerOpponent().makeMove();
                        }
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

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public void occupyCell() {
        if (TicTacToe.getIsPlayer1Turn()) {
            cellState = CellState.X;
            setText("X");
        } else {
            cellState = CellState.O;
            setForeground(color2);
            setText("O");
        }
    }
}
