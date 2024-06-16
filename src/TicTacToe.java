import javax.swing.*;
import java.awt.*;

public class TicTacToe {
    private static final int BOARD_SIZE = 3;
    private static final Cell[][] boardCells = new Cell[BOARD_SIZE][BOARD_SIZE];
    private static final JFrame frame = new JFrame("Tic-Tac-Toe");

    public static void main(String[] args) {
        buildGui();
    }

    private static void buildGui() {
        JMenuBar menuBar = createJMenuBar();

        frame.setJMenuBar(menuBar);

        JPanel panel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardCells[i][j] = new Cell(i, j);
                panel.add(boardCells[i][j]);
            }
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameVsComputer = new JMenuItem("New game (vs computer)");
        JMenuItem newGame2Players = new JMenuItem("New game (2 players)");
        gameMenu.add(newGameVsComputer);
        gameMenu.add(newGame2Players);
        menuBar.add(gameMenu);

        JMenu levelMenu = new JMenu("Level");
        ButtonGroup levelGroup = new ButtonGroup();
        JRadioButtonMenuItem levelBeginner = new JRadioButtonMenuItem("Beginner", true);
        JRadioButtonMenuItem levelIntermediate = new JRadioButtonMenuItem("Intermediate");
        levelMenu.add(levelBeginner);
        levelGroup.add(levelBeginner);
        levelMenu.add(levelIntermediate);
        levelGroup.add(levelIntermediate);
        menuBar.add(levelMenu);
        return menuBar;
    }

    static boolean checkIfGameOver(Cell thisTurn) {
        CellState thisTurnState = thisTurn.getCellState();
        boolean isWin = false;

        // check vertical win
        int countSameCellState = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (thisTurnState == boardCells[i][thisTurn.getColumnNumber()].getCellState()) {
                countSameCellState++;
            }
            if (countSameCellState == BOARD_SIZE) {
                isWin = true;
            }
        }

        // check horizontal win
        countSameCellState = 0;
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (thisTurnState == boardCells[thisTurn.getRowNumber()][j].getCellState()) {
                countSameCellState++;
            }
            if (countSameCellState == BOARD_SIZE) {
                isWin = true;
            }
        }

        // check diagonal win top-left to bottom-right
        countSameCellState = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (thisTurnState == boardCells[i][i].getCellState()) {
                countSameCellState++;
            }
            if (countSameCellState == BOARD_SIZE) {
                isWin = true;
            }
        }

        // check diagonal win top-right to bottom-left
        countSameCellState = 0;
        for (int i = 0, j = BOARD_SIZE - 1; i < BOARD_SIZE || j >= 0; i++, j--) {
            if (thisTurnState == boardCells[i][j].getCellState()) {
                countSameCellState++;
            }
            if (countSameCellState == BOARD_SIZE) {
                isWin = true;
            }
        }

        if (isWin) {
            String winMessage = Cell.getIsPlayer1Turn() ? "Player 1 won!" : "Player 2 won!";
            JOptionPane.showMessageDialog(frame, winMessage, "Game over", JOptionPane.INFORMATION_MESSAGE);
            resetBoardForNewGame();
            return true;
        }

        // check game over
        boolean isAtLeastOneCellEmpty = false;
        outerLoop:
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (boardCells[i][j].getCellState() == CellState.EMPTY) {
                    isAtLeastOneCellEmpty = true;
                    break outerLoop;
                }
            }
        }
        if (!isAtLeastOneCellEmpty) {
            JOptionPane.showMessageDialog(frame, "Draw!", "Game over", JOptionPane.INFORMATION_MESSAGE);
            resetBoardForNewGame();
            return true;
        }

        return false;
    }

    static void resetBoardForNewGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardCells[i][j].setCellState(CellState.EMPTY);
                boardCells[i][j].setText("");
                boardCells[i][j].setForeground(Cell.color1);
            }
        }
        Cell.setIsPlayer1Turn(true);
    }
}
