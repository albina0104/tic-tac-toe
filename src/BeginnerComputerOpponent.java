import javax.swing.Timer;

public class BeginnerComputerOpponent implements ComputerOpponent {
    private final Level level = Level.BEGINNER;

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void makeMove() {
        Timer timer = new Timer(2000, e -> {
            occupyRandomCell();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
