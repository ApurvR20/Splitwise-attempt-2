import java.util.ArrayList;
import java.util.List;

public class EqualSplit implements Split {

    double amount;
    List<User> userList;
    int userCount;

    public List<LedgerEntry> split(Expense expense){

        this.amount = expense.amount;;
        this.userList = expense.userList;
        userCount = userList.size();
        double share = amount/userCount;
        List<LedgerEntry> ledgerEntryList = new ArrayList<>();
        for(User u : userList){
            ledgerEntryList.add(new LedgerEntry(u.id,share));
        }

        if(share*userCount < amount){
            ledgerEntryList.getFirst().amount += (amount - (share*userCount));
        }

        return ledgerEntryList;
    }
}
