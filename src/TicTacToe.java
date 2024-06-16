import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TicTacToe {
    static final int BOARD_SIZE = 3;
    private static final Cell[][] boardCells = new Cell[BOARD_SIZE][BOARD_SIZE];
    private static final JFrame frame = new JFrame("Tic-Tac-Toe");
    private static boolean isGameVsComputer = true;
    private static boolean isComputerPlayer1 = false;
    private static ComputerOpponent computerOpponent = ComputerOpponentFactory.createComputerOpponent(Level.BEGINNER);
    private static boolean isPlayer1Turn = true;
    private static JLabel whoseTurn;

    public static void main(String[] args) {
        buildGui();
        startNewGame();
    }

    private static void buildGui() {
        JMenuBar menuBar = createJMenuBar();
        frame.setJMenuBar(menuBar);

        JPanel infoPanel = new JPanel();
        whoseTurn = new JLabel("Your turn! (X)");
        whoseTurn.setForeground(Cell.color1);
        infoPanel.setOpaque(true);
        infoPanel.setBackground(new Color(213, 231, 255));
        whoseTurn.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoPanel.add(whoseTurn);

        JPanel cellsPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardCells[i][j] = new Cell(i, j);
                cellsPanel.add(boardCells[i][j]);
            }
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(infoPanel, BorderLayout.NORTH);
        frame.getContentPane().add(cellsPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameVsComputer = new JMenuItem("New game (vs computer)");
        newGameVsComputer.addActionListener((event) -> {
            isGameVsComputer = true;
            startNewGame();
        });
        JMenuItem newGame2Players = new JMenuItem("New game (2 players)");
        newGame2Players.addActionListener((event) -> {
            isGameVsComputer = false;
            startNewGame();
        });
        gameMenu.add(newGameVsComputer);
        gameMenu.add(newGame2Players);
        menuBar.add(gameMenu);

        JMenu levelMenu = new JMenu("Level");
        ButtonGroup levelGroup = new ButtonGroup();
        JRadioButtonMenuItem levelBeginner = new JRadioButtonMenuItem("Beginner", true);
        levelBeginner.addActionListener((event) ->
                computerOpponent = ComputerOpponentFactory.createComputerOpponent(Level.BEGINNER)
        );
        JRadioButtonMenuItem levelIntermediate = new JRadioButtonMenuItem("Intermediate");
        levelIntermediate.addActionListener((event) ->
                computerOpponent = ComputerOpponentFactory.createComputerOpponent(Level.INTERMEDIATE)
        );
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
            String winMessage = TicTacToe.getIsPlayer1Turn() ? "Player 1 won!" : "Player 2 won!";
            JOptionPane.showMessageDialog(frame, winMessage, "Game over", JOptionPane.INFORMATION_MESSAGE);
            startNewGame();
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
            startNewGame();
            return true;
        }

        return false;
    }

    private static void resetBoardForNewGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardCells[i][j].setCellState(CellState.EMPTY);
                boardCells[i][j].setText("");
                boardCells[i][j].setForeground(Cell.color1);
            }
        }
        TicTacToe.setIsPlayer1Turn(true);
    }

    public static boolean getIsPlayer1Turn() {
        return isPlayer1Turn;
    }

    public static void setIsPlayer1Turn(boolean isPlayer1Turn) {
        TicTacToe.isPlayer1Turn = isPlayer1Turn;

        String whoseTurnLabelText;
        if (isGameVsComputer) {
            if (isPlayer1Turn) {
                whoseTurnLabelText = (isComputerTurn() ? "Computer's turn!" : "Your turn!") + " (X)";
                whoseTurn.setForeground(Cell.color1);
            } else {
                whoseTurnLabelText = (isComputerTurn() ? "Computer's turn!" : "Your turn!") + " (O)";
                whoseTurn.setForeground(Cell.color2);
            }
        } else {
            if (isPlayer1Turn) {
                whoseTurnLabelText = "Player 1's turn! (X)";
                whoseTurn.setForeground(Cell.color1);
            } else {
                whoseTurnLabelText = "Player 2's turn! (O)";
                whoseTurn.setForeground(Cell.color2);
            }
        }
        whoseTurn.setText(whoseTurnLabelText);
    }

    public static boolean isComputerTurn() {
        return isGameVsComputer && (isPlayer1Turn == isComputerPlayer1);
    }

    private static void startNewGame() {
        if (isGameVsComputer) {
            Random random = new Random();
            int randomInt = random.nextInt(2);
            isComputerPlayer1 = randomInt == 1;
        }

        resetBoardForNewGame();

        if (isComputerTurn()) {
            computerOpponent.makeMove();
        }

    }

    public static ComputerOpponent getComputerOpponent() {
        return computerOpponent;
    }

    public static Cell[][] getBoardCells() {
        return boardCells;
    }
}
