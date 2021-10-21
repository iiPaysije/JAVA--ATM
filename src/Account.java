import java.util.ArrayList;

public class Account {

    //    Name of the account
    private String name;


    //    Unique id of acount
    private String uuid;

    //    Owner of the account (User)
    private User holder;

    //    List of transactions
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank) {

        //set name and holder of the account
        this.name = name;
        this.holder = holder;

        //get Accout UUID
        this.uuid = theBank.getNewAccountUUID();

        //init transactions
        this.transactions = new ArrayList<Transaction>();

//        //add account to holder and the bank list
//        holder.addAccount(this);
//        theBank.addAccount(this);
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {

        //Get account balance
        double balance = this.getBalance();

        //format the summary line +-
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);

        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }

    }

    public double getBalance() {
        double balance = 0;

        for (Transaction t : this.transactions) {

            balance += t.getAmount();
        }
        return balance;

    }

    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int i = this.transactions.size() - 1; i >= 0; i--) {
            System.out.printf(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {

        //create a new transaction object and add to list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
