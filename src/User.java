import java.util.List;
import java.util.Map;

public class User {

    String id, name, email;
    Ledger ledger;
    User(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
        this.ledger = new Ledger();
    }

    Expense generateExpense(String id,double amount, List<User> userList){
        return new Expense(id,amount,userList, Expense.SplitStrategy.EQUAL);
    }

    Expense generateExpense(String id,double amount, List<User> userList, List<Double> shares){
        return new Expense(id,amount,userList,shares, Expense.SplitStrategy.UNEQUAL);
    }

    Map<String, Double> getLedger(String id, Ledger ledger){
        return ledger.getUserExpensesByID(id);
    }
}
