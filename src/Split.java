import java.util.List;

public interface Split {
    public List<LedgerEntry> split(Expense expense);
}
