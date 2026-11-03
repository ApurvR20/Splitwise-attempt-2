import java.util.List;

public class Expense {

    public enum SplitStrategy{
        EQUAL,
        UNEQUAL
    }

    String id;
    double amount;
    List<User> userList;
    List<Double> shares;
    SplitStrategy splitStrategy;
    Expense(String id, double amount, List<User> userList, SplitStrategy splitStrategy){
        this.id = id;
        this.amount = amount;
        this.userList = userList;
        this.splitStrategy = splitStrategy;
    }

    String getUserID(){
        return id;
    }

    Expense(String id,double amount, List<User> userList, List<Double> shares, SplitStrategy splitStrategy){
        this.id = id;
        this.amount = amount;
        this.userList = userList;
        this.shares = shares;
        this.splitStrategy = splitStrategy;
    }
}
