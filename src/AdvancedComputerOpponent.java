public class AdvancedComputerOpponent implements ComputerOpponent {
    private final Level level = Level.ADVANCED;

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void makeMove() {
        // To be implemented if necessary.
        // After implementation, the level needs to be added to the menu in TicTacToe.createJMenuBar
    }
}
