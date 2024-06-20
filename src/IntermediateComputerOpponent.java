import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class IntermediateComputerOpponent implements ComputerOpponent {
    private final Level level = Level.INTERMEDIATE;

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void makeMove() {
        Timer timer = new Timer(2000, e -> {
            CellState computerSymbol = TicTacToe.getIsComputerPlayer1() ? CellState.X : CellState.O;
            CellState playerSymbol = TicTacToe.getIsComputerPlayer1() ? CellState.O : CellState.X;

            Optional<Cell> cellToOccupy = chooseCellForMoveThirdInARow(computerSymbol);
            if (cellToOccupy.isEmpty()) {
                cellToOccupy = chooseCellForMoveThirdInARow(playerSymbol);
            }
            if (cellToOccupy.isPresent()) {
                cellToOccupy.get().occupyCell();
            } else {
                occupyRandomCell();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private Optional<Cell> chooseCellForMoveThirdInARow(CellState sameSymbolToCheck) {
        List<Cell> cellListForPossibleMove = new ArrayList<>();

        // check horizontal rows
        int countSameCellState = 0;
        List<Cell> emptyCellListTemp = new ArrayList<>();
        for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
                Cell cell = TicTacToe.getBoardCells()[i][j];
                if (cell.getCellState() == sameSymbolToCheck) {
                    countSameCellState++;
                } else if (cell.getCellState() == CellState.EMPTY) {
                    emptyCellListTemp.add(cell);
                }
            }
            if (countSameCellState == 2) {
                cellListForPossibleMove.addAll(emptyCellListTemp);
            }
            countSameCellState = 0;
            emptyCellListTemp = new ArrayList<>();
        }

        // check vertical rows
        for (int j = 0; j < TicTacToe.BOARD_SIZE; j++) {
            for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
                Cell cell = TicTacToe.getBoardCells()[i][j];
                if (cell.getCellState() == sameSymbolToCheck) {
                    countSameCellState++;
                } else if (cell.getCellState() == CellState.EMPTY) {
                    emptyCellListTemp.add(cell);
                }
            }
            if (countSameCellState == 2) {
                cellListForPossibleMove.addAll(emptyCellListTemp);
            }
            countSameCellState = 0;
            emptyCellListTemp = new ArrayList<>();
        }

        // check diagonal top-left to bottom-right
        for (int i = 0; i < TicTacToe.BOARD_SIZE; i++) {
            Cell cell = TicTacToe.getBoardCells()[i][i];
            if (cell.getCellState() == sameSymbolToCheck) {
                countSameCellState++;
            } else if (cell.getCellState() == CellState.EMPTY) {
                emptyCellListTemp.add(cell);
            }
        }
        if (countSameCellState == 2) {
            cellListForPossibleMove.addAll(emptyCellListTemp);
        }
        countSameCellState = 0;
        emptyCellListTemp = new ArrayList<>();

        // check diagonal win top-right to bottom-left
        for (int i = 0, j = TicTacToe.BOARD_SIZE - 1; i < TicTacToe.BOARD_SIZE || j >= 0; i++, j--) {
            Cell cell = TicTacToe.getBoardCells()[i][j];
            if (cell.getCellState() == sameSymbolToCheck) {
                countSameCellState++;
            } else if (cell.getCellState() == CellState.EMPTY) {
                emptyCellListTemp.add(cell);
            }
        }
        if (countSameCellState == 2) {
            cellListForPossibleMove.addAll(emptyCellListTemp);
        }

        if (cellListForPossibleMove.isEmpty()) {
            return Optional.empty();
        }

        Random random = new Random();
        int randomCellNumber = random.nextInt(cellListForPossibleMove.size());
        return Optional.of(cellListForPossibleMove.get(randomCellNumber));
    }
}
