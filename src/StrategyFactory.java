public class StrategyFactory {

    static Split getStrategy(Expense.SplitStrategy strategy){
        switch (strategy){
            case EQUAL -> {
                return new EqualSplit();
            }
            case UNEQUAL -> {
                return new UnequalSplit();
            }
        }
        return null;
    }
}
