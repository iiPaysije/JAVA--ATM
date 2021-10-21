import java.util.Date;

public class Transaction {

    //    ammount of money transfered
    private double ammount;

    //    When did it occur
    private Date timestamp;

    //
    private String memo;

    //    In which account did it happen
    private Account inAccount;

    public Transaction(double ammount, Account inAccount) {

        this.ammount = ammount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double ammount, String memo, Account inAccount) {

        //call the two arg constructor first
        this(ammount, inAccount);

        //set the memo
        this.memo = memo;
    }


    public double getAmount() {
        return this.ammount;
    }

    public String getSummaryLine() {
        if(this.ammount >= 0 ) {
            return String.format("%s : $%.02f : %s  ", this.timestamp.toString(), this.ammount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s  ", this.timestamp.toString(), this.ammount, this.memo);
        }
    }
}
