import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BeginnerComputerOpponent implements ComputerOpponent {
    private final Level level = Level.BEGINNER;

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void makeMove() {
        List<Cell> emptyCellList = new ArrayList<>();

        for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                Cell cell = TicTacToe.getBoardCells()[i][j];
                if (cell.getCellState() == CellState.EMPTY) {
                    emptyCellList.add(cell);
                }
            }
        }

        Random random = new Random();
        int randomCellNumber = random.nextInt(emptyCellList.size());
        Cell chosenCell = emptyCellList.get(randomCellNumber);
        chosenCell.occupyCell();

        boolean isGameOver = TicTacToe.checkIfGameOver(chosenCell);
        if (!isGameOver) {
            TicTacToe.setIsPlayer1Turn(!TicTacToe.getIsPlayer1Turn());
        }
    }
}
