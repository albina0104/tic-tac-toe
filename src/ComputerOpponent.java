import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface ComputerOpponent {
    Level getLevel();
    void makeMove();

    default void occupyRandomCell() {
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
    }
}
