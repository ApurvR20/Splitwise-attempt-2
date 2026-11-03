import java.util.HashMap;
import java.util.Map;

public class Ledger {

    Map<String,Map<String, Double>> userExpenses;

    Ledger(){
        userExpenses = new HashMap<>();
    }

    void updateEntry(String id1, String id2, double amt){
        userExpenses.computeIfAbsent(id1, k -> new HashMap<>()).merge(id2,amt,Double::sum);
        userExpenses.computeIfAbsent(id2, k -> new HashMap<>()).merge(id1,-amt,Double::sum);
    }

    void removeEntry(String id){
        Map<String, Double> a = userExpenses.get("A");
        if (a != null) a.remove("B");

        Map<String, Double> b = userExpenses.get("B");
        if (b != null) b.remove("A");
    }

    Map<String, Double> getUserExpensesByID(String id){
        return userExpenses.get(id);
    }
}
