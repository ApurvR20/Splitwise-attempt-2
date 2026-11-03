import java.util.*;

// Money is double for now. Amounts print as whole numbers when possible, else 2 decimals.

void main() {
    Map<String, User> users = new LinkedHashMap<>();
    users.put("u1", new User("u1", "Ali", "alizaidi@duck.com"));
    users.put("u2", new User("u2", "Apu", "apuraj@duck.com"));
    users.put("u3", new User("u3", "Kush", "kushgupta@duck.com"));
    users.put("u4", new User("u4", "Toff", "toffahmad@duck.com"));

    Ledger ledger = new Ledger();
    Scanner sc = new Scanner(System.in);

    while (sc.hasNextLine()) {
        String line = sc.nextLine().trim();
        if (line.isEmpty()) continue;
        String[] t = line.split("\\s+");
        try {
            switch (t[0]) {
                case "EXPENSE" -> addExpense(t, users, ledger);
                case "SHOW" -> show(t, users, ledger);
                default -> System.out.println("Unknown command: " + t[0]);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// EXPENSE <payer> <amount> <n> <u1..un> <TYPE> [values...]
void addExpense(String[] t, Map<String, User> users, Ledger ledger) {
    if (t.length < 5) throw new IllegalArgumentException("Invalid EXPENSE command");

    User payer = users.get(t[1]);
    if (payer == null) throw new IllegalArgumentException("Unknown user: " + t[1]);

    double amount = Double.parseDouble(t[2]);
    if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

    int n = Integer.parseInt(t[3]);
    if (n <= 0 || t.length < 5 + n) throw new IllegalArgumentException("Invalid participant count");

    List<User> participants = new ArrayList<>();
    Set<String> seen = new HashSet<>();
    for (int i = 0; i < n; i++) {
        String id = t[4 + i];
        User u = users.get(id);
        if (u == null) throw new IllegalArgumentException("Unknown user: " + id);
        if (!seen.add(id)) throw new IllegalArgumentException("Duplicate participant: " + id);
        participants.add(u);
    }

    String type = t[4 + n];
    Expense expense;
    if (type.equals("EQUAL")) {
        expense = new Expense(payer.id, amount, participants, Expense.SplitStrategy.EQUAL);
    } else if (type.equals("EXACT")) {
        List<Double> shares = new ArrayList<>();
        for (int i = 5 + n; i < t.length; i++) shares.add(Double.parseDouble(t[i]));
        // your enum calls this UNEQUAL; change it if you renamed it to EXACT
        expense = new Expense(payer.id, amount, participants, shares, Expense.SplitStrategy.UNEQUAL);
    } else {
        throw new IllegalArgumentException("Unsupported split type: " + type);
    }

    // new Splitter per expense, since it reads the payer and strategy in its constructor
    Splitter splitter = new Splitter(expense);
    List<LedgerEntry> entries = splitter.split(expense);   // throws if the shares are invalid
    splitter.updateAllLedger(entries, ledger);
}

// SHOW  or  SHOW <user-id>
void show(String[] t, Map<String, User> users, Ledger ledger) {
    List<String> out = new ArrayList<>();

    if (t.length == 1) {
        // every pair appears once: only the negative side is printed
        for (String u : new TreeSet<>(ledger.userExpenses.keySet())) {
            for (var e : new TreeMap<>(ledger.userExpenses.get(u)).entrySet()) {
                if (e.getValue() < -0.005) {
                    out.add(u + " owes " + e.getKey() + ": " + fmt(e.getValue()));
                }
            }
        }
    } else {
        String u = t[1];
        if (!users.containsKey(u)) throw new IllegalArgumentException("Unknown user: " + u);
        Map<String, Double> row = ledger.getUserExpensesByID(u);
        if (row != null) {
            for (var e : new TreeMap<>(row).entrySet()) {
                double v = e.getValue();
                if (v < -0.005) out.add(u + " owes " + e.getKey() + ": " + fmt(v));
                else if (v > 0.005) out.add(e.getKey() + " owes " + u + ": " + fmt(v));
            }
        }
    }

    if (out.isEmpty()) System.out.println("No balances");
    else out.forEach(System.out::println);
}

String fmt(double v) {
    v = Math.abs(v);
    return v == Math.rint(v) ? String.valueOf((long) v) : String.format("%.2f", v);
}