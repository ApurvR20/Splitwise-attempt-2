import java.util.List;
import java.util.Map;

public class Splitter {

    private double amount;
    private List<User> userList;
    private Expense.SplitStrategy splitStrategy;
    private String id;


    Splitter(Expense expense){
        this.id = expense.getUserID();
        this.amount = expense.amount;
        this.userList = expense.userList;
        this.splitStrategy = expense.splitStrategy;
    }

    List<LedgerEntry> split(Expense expense){
        List<LedgerEntry> ledgerEntryList = StrategyFactory.getStrategy(splitStrategy).split(expense);
        return ledgerEntryList;
    }

    void updateAllLedger(List<LedgerEntry> ledgerEntryList, Ledger ledger){

        for(LedgerEntry e : ledgerEntryList){

            if(id.equals(e.id)) continue;
            ledger.updateEntry(id, e.id, e.amount);
        }
    }
}
