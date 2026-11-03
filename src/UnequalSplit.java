import java.util.ArrayList;
import java.util.List;

public class UnequalSplit implements Split {
    double amount;
    List<User> userList;
    int userCount;
    List<Double> shares;
    String id;

    public List<LedgerEntry> split(Expense expense) {

        this.id = expense.getUserID();
        this.amount = expense.amount;
        this.userList = expense.userList;
        this.shares = expense.shares;
        userCount = userList.size();
        double checkAmt = 0;
        for (Double share : shares) {
            checkAmt += share;
        }


        if (checkAmt != amount || shares.size()!= userCount) {
            throw new IllegalArgumentException("Invalid shares");
        }

        List<LedgerEntry> ledgerEntryList = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            ledgerEntryList.add(new LedgerEntry(userList.get(i).id, shares.get(i)));
        }

        return ledgerEntryList;
    }
}
