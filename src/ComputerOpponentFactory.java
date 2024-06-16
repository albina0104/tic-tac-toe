public class ComputerOpponentFactory {
    public static ComputerOpponent createComputerOpponent(Level level) {
        return switch (level) {
            case BEGINNER -> new BeginnerComputerOpponent();
            case INTERMEDIATE -> new IntermediateComputerOpponent();
            case ADVANCED -> new AdvancedComputerOpponent();
        };
    }
}
